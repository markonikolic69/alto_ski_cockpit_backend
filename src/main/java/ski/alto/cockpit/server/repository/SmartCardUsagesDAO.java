package ski.alto.cockpit.server.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ski.alto.cockpit.server.model.SmartCardUsages;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SmartCardUsagesDAO {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
    JdbcTemplate jdbcTemplate;

	public List<SmartCardUsages> findTop100ByOrderByStartDateDesc (String ownership) {
		List<SmartCardUsages> results = null;
		logger.info("findTop100ByOrderByStartDateDesc");

		if (ownership != null) {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where ownership=? \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return  resultRow;
					},
					ownership
			);
		} else {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return  resultRow;
					}
			);
		}

		return results;
	}
	public List<SmartCardUsages> findTop100ByResortIdOrderByStartDateDesc (Integer resortId, String ownership) {
		List<SmartCardUsages> results = null;
		logger.info("findTop100ByResortIdOrderByStartDateDesc");

		if (ownership != null) {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where scu.resort_id=? and ownership=? \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return resultRow;
					},
					resortId, ownership
			);
		} else {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where scu.resort_id=? \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return  resultRow;
					},
					resortId
			);
		}

		return results;
	}
	public List<SmartCardUsages> findByStartDateBetweenOrderByStartDateDesc (LocalDateTime from, LocalDateTime to, String ownership) {
		List<SmartCardUsages> results = null;
		logger.info("findByStartDateBetweenOrderByStartDateDesc");

		if (ownership != null) {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where scu.start_date::date between date(?) and date(?) \r\n"
							+ "and ownership=? \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return  resultRow;
					},
					from, to, ownership
			);
		} else {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where scu.start_date::date between date(?) and date(?) \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return  resultRow;
					},
					from, to
			);
		}

		return results;
	}

	public List<SmartCardUsages> findByResortIdAndStartDateBetweenOrderByStartDateDesc (Integer resortId, LocalDateTime from, LocalDateTime to, String ownership) {
		List<SmartCardUsages> results = null;
		logger.info("findByResortIdAndStartDateBetweenOrderByStartDateDesc");

		if (ownership != null) {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where scu.resort_id=? \r\n"
							+ "and scu.start_date::date between date(?) and date(?) \r\n"
							+ "and ownership=? \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return  resultRow;
					},
					resortId, from, to, ownership
			);
		} else {
			results = jdbcTemplate.query(
					"select scu.id, scu.resort_id, charged_product_name, start_date, charged_price, charged_currency, charged_consumer_category_name\r\n"
							+ "from smart_card_usages scu join smart_cards sc \r\n"
							+ "on scu.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where scu.resort_id=? \r\n"
							+ "and scu.start_date::date between date(?) and date(?) \r\n"
							+ "order by date(start_date) desc\r\n"
							+ "limit 100",
					(rs, rowNum) -> {
						SmartCardUsages resultRow = new SmartCardUsages();
						resultRow.setId(rs.getLong("id"));
						resultRow.setResortId(rs.getInt("resort_id"));
						resultRow.setChargedProductName(rs.getString("charged_product_name"));
						resultRow.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
						resultRow.setChargedPrice(rs.getDouble("charged_price"));
						resultRow.setChargedCurrency(rs.getString("charged_currency"));
						resultRow.setChargedConsumerCategoryName(rs.getString("charged_consumer_category_name"));
						return  resultRow;
					},
					resortId, from, to
			);
		}

		return results;
	}

}
