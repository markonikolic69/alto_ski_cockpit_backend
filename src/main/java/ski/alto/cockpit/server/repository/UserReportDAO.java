package ski.alto.cockpit.server.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ski.alto.cockpit.server.model.UserReportDTO;

import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserReportDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<UserReportDTO> getUserReport(Integer resortId, LocalDate startDate, LocalDate endDate, String ownership) {

        List<Object> args = new ArrayList<Object>();
        List<Integer> argTypes = new ArrayList<Integer>();

        args.add(startDate);
        argTypes.add(Types.DATE);
        args.add(endDate);
        argTypes.add(Types.DATE);

        if (resortId != null) {
            args.add(resortId);
            argTypes.add(Types.INTEGER);
        }

        if (ownership != null) {
            args.add(ownership);
            argTypes.add(Types.VARCHAR);
        }

        int[] argTypesInt = argTypes.stream().mapToInt(Integer::intValue).toArray();


        return jdbcTemplate.query(
                "select r.name resortName, sum(scu.charged_price) chargedSum," +
                        " scu.charged_currency chargedCurrency, count(scu.id) totalTransactions," +
                        " case when sc.stripe_status = 'succeeded' then 'Charged'" +
                        "   when sc.stripe_status = 'requires_capture' then 'Preauthorized' end chargedStatus" +
                        " from resorts r, smart_card_usages scu, stripe_charges sc, users u" +
                        " where r.id = scu.resort_id" +
                        " and sc.resort_visit_id in" +
                        "  (select resort_visit_id from resort_usage_reports rur where rur.id = scu.resort_usage_report_id)" +
                        " and sc.user_id = u.id" +
                        " and DATE(sc.start_date) = DATE(scu.start_date)" +
                        " and sc.status='success' and usage_date >= ? and usage_date < ?" +
                        (resortId != null ? " and r.id = ?" : "") +
                        (ownership != null ? " and u.ownership = ?" : "") +
                        " group by r.name, scu.charged_currency," +
                        " sc.status, sc.stripe_status",
                args.toArray(), argTypesInt,
                (rs, rowNum) -> new UserReportDTO(
                        rs.getString("resortName"),
                        rs.getDouble("chargedSum"),
                        rs.getString("chargedCurrency"),
                        rs.getInt("totalTransactions"),
                        rs.getString("chargedStatus"))
        );

    }

}
