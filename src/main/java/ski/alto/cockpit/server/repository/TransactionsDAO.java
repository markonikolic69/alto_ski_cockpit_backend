package ski.alto.cockpit.server.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ski.alto.cockpit.server.model.ActivityDTO;
import ski.alto.cockpit.server.model.FailedTransactionDTO;
import ski.alto.cockpit.server.model.TransactionDTO;

@Component
public class TransactionsDAO {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<TransactionDTO> getTransactionByCardNumberAndResortIdForDateRange(String card_number, String start_date, String end_date, Integer resort_id) {
        List<TransactionDTO> transactions = null;
        logger.info("getDTACardByCardNumberAndResortForDateRange");
        if (start_date == null || start_date.equals("null") || end_date == null || end_date.equals("null")) {
            logger.info("RETURN FIRST 100");
            String query = "select reports.charged_product_name product_name, TO_CHAR(scu.start_date,'YYYY-MM-DD HH24:MI:SS') created_at, scu.id transaction_id,\r\n"
                    + "charges.amount price, reports.charged_currency currency, r.name resort_name\r\n"
                    + "from smart_card_usages scu join resorts r on scu.resort_id=r.id\r\n"
                    + "INNER JOIN stripe_charge_items charges on charges.payable_id=scu.id AND charges.payable_type = 'SmartCardUsage'\r\n"
                    + "INNER JOIN resort_usage_reports reports on reports.id=scu.resort_usage_report_id\r\n"
                    + "where scu.smart_card_id=(select sc.id from smart_cards sc where sc.card_number=?)\r\n"
                    + "order by scu.start_date desc\r\n"
                    + "limit 100";
            logger.info("RESORT ID IS 0");
            logger.info(query);
            transactions = jdbcTemplate.query(query,
                    (rs, rowNum) -> new TransactionDTO(
                            rs.getString("product_name"),
                            rs.getString("created_at"),
                            rs.getInt("transaction_id"),
                            rs.getInt("price"),
                            rs.getString("currency"),
                            rs.getString("resort_name")),
                    card_number
            );
        } else if (resort_id == 0) {
            String query = "select reports.charged_product_name product_name, TO_CHAR(scu.start_date,'YYYY-MM-DD HH24:MI:SS') created_at, scu.id transaction_id,\r\n"
                    + "charges.amount price, reports.charged_currency currency, r.name resort_name\r\n"
                    + "from smart_card_usages scu join resorts r on scu.resort_id=r.id\r\n"
                    + "INNER JOIN stripe_charge_items charges on charges.payable_id=scu.id AND charges.payable_type = 'SmartCardUsage'\r\n"
                    + "INNER JOIN resort_usage_reports reports on reports.id=scu.resort_usage_report_id\r\n"
                    + "where scu.smart_card_id=(select sc.id from smart_cards sc where sc.card_number=?)\r\n"
                    + "and scu.start_date::date between date(?) and date(?)\r\n"
                    + "order by scu.start_date desc";
            logger.info("RESORT ID IS 0");
            logger.info(query);
            transactions = jdbcTemplate.query(query,
                    (rs, rowNum) -> new TransactionDTO(
                            rs.getString("product_name"),
                            rs.getString("created_at"),
                            rs.getInt("transaction_id"),
                            rs.getInt("price"),
                            rs.getString("currency"),
                            rs.getString("resort_name")),
                    card_number, start_date, end_date
            );
        } else {
            logger.info("RESORT ID IS " + resort_id);
            transactions = jdbcTemplate.query(
                    "select reports.charged_product_name product_name, TO_CHAR(scu.start_date,'YYYY-MM-DD HH24:MI:SS') created_at, scu.id transaction_id,\r\n"
                            + "charges.amount price, reports.charged_currency currency, r.name resort_name\r\n"
                            + "from smart_card_usages scu join resorts r on scu.resort_id=r.id\r\n"
                            + "INNER JOIN stripe_charge_items charges on charges.payable_id=scu.id AND charges.payable_type = 'SmartCardUsage'\r\n"
                            + "INNER JOIN resort_usage_reports reports on reports.id=scu.resort_usage_report_id\r\n"
                            + "where scu.smart_card_id=(select sc.id from smart_cards sc where sc.card_number=?)\r\n"
                            + "and scu.start_date::date between date(?) and date(?) and scu.resort_id=?\r\n"
                            + "order by scu.start_date desc",
                    (rs, rowNum) -> new TransactionDTO(
                            rs.getString("product_name"),
                            rs.getString("created_at"),
                            rs.getInt("transaction_id"),
                            rs.getInt("price"),
                            rs.getString("currency"),
                            rs.getString("resort_name")),
                    card_number, start_date, end_date, resort_id
            );
        }
        return transactions;
    }

    public Integer getLastStripeChargeFailure(String ownership) {
        Integer result = 0;

        List<Integer> listOfIds = null;

        if (ownership != null) {
            listOfIds = jdbcTemplate.query(
                    "select sc.id from stripe_charges sc \n" +
                            "join users u on u.id = sc.user_id\n" +
                            "where sc.status = 'success'\n" +
                            "and stripe_status = 'requires_capture'\n" +
                            "and u.ownership = ?\n" +
                            "order by sc.start_date desc limit 1;",
                    (rs, rowNum) -> rs.getInt("id"),
                    ownership
            );
        } else {
            listOfIds = jdbcTemplate.query(
                    "select id from stripe_charges sc \n" +
                            "where sc.status = 'success'\n" +
                            "and stripe_status = 'requires_capture'\n" +
                            "order by sc.start_date desc limit 1;",
                    (rs, rowNum) -> rs.getInt("id")
            );
        }

        if (listOfIds != null && !listOfIds.isEmpty()) {
            result = listOfIds.get(0);
        }

        return result;
    }

    public List<FailedTransactionDTO> getStripeChargeFailures(Integer lastKnownFailedChargeId, String ownership) {

        List<FailedTransactionDTO> result = null;

        if (ownership != null) {
            result = jdbcTemplate.query(
                    "select scu.charged_product_name product_name, TO_CHAR(scu.start_date,'YYYY-MM-DD HH24:MI:SS') created_at, sc.id transaction_id,\n" +
                            "scu.charged_price price, scu.charged_currency currency, r.name resort_name, sc.stripe_id stripe_id\n" +
                            "from resorts r, smart_card_usages scu, resort_usage_reports rur, stripe_charges sc, users u\n" +
                            "where r.id = scu.resort_id  \n" +
                            "and scu.resort_usage_report_id = rur.id\n" +
                            "and rur.resort_visit_id = sc.resort_visit_id \n" +
                            "and sc.user_id = u.id \n" +
                            "and sc.status = 'success'\n" +
                            "and sc.stripe_status = 'requires_capture'\n" +
                            "and sc.id > ?\n" +
                            "and u.ownership = ?\n" +
                            "order by scu.start_date desc",
                    (rs, rowNum) -> new FailedTransactionDTO(
                            rs.getString("product_name"),
                            rs.getString("created_at"),
                            rs.getInt("transaction_id"),
                            rs.getInt("price"),
                            rs.getString("currency"),
                            rs.getString("resort_name"),
                            rs.getString("stripe_id")),
                    lastKnownFailedChargeId, ownership
            );
        } else {
            result = jdbcTemplate.query(
                    "select scu.charged_product_name product_name, TO_CHAR(scu.start_date,'YYYY-MM-DD HH24:MI:SS') created_at, sc.id transaction_id,\n" +
                            "scu.charged_price price, scu.charged_currency currency, r.name resort_name, sc.stripe_id stripe_id\n" +
                            "from resorts r, smart_card_usages scu, resort_usage_reports rur, stripe_charges sc\n" +
                            "where r.id = scu.resort_id  \n" +
                            "and scu.resort_usage_report_id = rur.id\n" +
                            "and rur.resort_visit_id = sc.resort_visit_id \n" +
                            "and sc.status = 'success'\n" +
                            "and sc.stripe_status = 'requires_capture'\n" +
                            "and sc.id > ?\n" +
                            "order by scu.start_date desc",
                    (rs, rowNum) -> new FailedTransactionDTO(
                            rs.getString("product_name"),
                            rs.getString("created_at"),
                            rs.getInt("transaction_id"),
                            rs.getInt("price"),
                            rs.getString("currency"),
                            rs.getString("resort_name"),
                            rs.getString("stripe_id")),
                    lastKnownFailedChargeId
            );
        }

        return result;
    }

    public List<FailedTransactionDTO> getStripeChargeFailuresFromToday(String ownership) {

        List<FailedTransactionDTO> result = null;

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrowStart = LocalDate.now().plusDays(1).atStartOfDay();

        if (ownership != null) {
            result = jdbcTemplate.query(
                    "select scu.charged_product_name product_name, TO_CHAR(scu.start_date,'YYYY-MM-DD HH24:MI:SS') created_at, sc.id transaction_id,\n" +
                            "scu.charged_price price, scu.charged_currency currency, r.name resort_name, sc.stripe_id stripe_id\n" +
                            "from resorts r, smart_card_usages scu, resort_usage_reports rur, stripe_charges sc, users u\n" +
                            "where r.id = scu.resort_id  \n" +
                            "and scu.resort_usage_report_id = rur.id\n" +
                            "and rur.resort_visit_id = sc.resort_visit_id \n" +
                            "and sc.user_id = u.id \n" +
                            "and sc.status = 'success'\n" +
                            "and sc.stripe_status = 'requires_capture'\n" +
                            "and scu.start_date::date between date(?) and date(?)\n" +
                            "and u.ownership = ?\n" +
                            "order by scu.start_date desc",
                    (rs, rowNum) -> new FailedTransactionDTO(
                            rs.getString("product_name"),
                            rs.getString("created_at"),
                            rs.getInt("transaction_id"),
                            rs.getInt("price"),
                            rs.getString("currency"),
                            rs.getString("resort_name"),
                            rs.getString("stripe_id")),
                    todayStart, tomorrowStart, ownership
            );
        } else {
            result = jdbcTemplate.query(
                    "select scu.charged_product_name product_name, TO_CHAR(scu.start_date,'YYYY-MM-DD HH24:MI:SS') created_at, sc.id transaction_id,\n" +
                            "scu.charged_price price, scu.charged_currency currency, r.name resort_name, sc.stripe_id stripe_id\n" +
                            "from resorts r, smart_card_usages scu, resort_usage_reports rur, stripe_charges sc\n" +
                            "where r.id = scu.resort_id  \n" +
                            "and scu.resort_usage_report_id = rur.id\n" +
                            "and rur.resort_visit_id = sc.resort_visit_id \n" +
                            "and sc.status = 'success'\n" +
                            "and sc.stripe_status = 'requires_capture'\n" +
                            "and scu.start_date::date between date(?) and date(?)\n" +
                            "order by scu.start_date desc",
                    (rs, rowNum) -> new FailedTransactionDTO(
                            rs.getString("product_name"),
                            rs.getString("created_at"),
                            rs.getInt("transaction_id"),
                            rs.getInt("price"),
                            rs.getString("currency"),
                            rs.getString("resort_name"),
                            rs.getString("stripe_id")),
                    todayStart, tomorrowStart
            );
        }

        return result;
    }
}
