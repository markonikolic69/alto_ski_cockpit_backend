package ski.alto.cockpit.server.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import ski.alto.cockpit.server.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.chrono.IsoChronology;
import java.time.format.*;
import java.time.temporal.ChronoField;
import java.util.*;

@Component
public class ProductDAO {

	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
    JdbcTemplate jdbcTemplate;
	public List<ProductDTO> getProductsByResortId(Integer resortId) {

    	List<ProductDTO> products =  jdbcTemplate.query(
				"select p.name as product_name, p.active, cs.name as category_name," +
				" ca.version, re.name resort_name, pl.id price_list_id" +
				" from products p JOIN product_price_list_maps pm on pm.product_id = p.id" +
				" JOIN consumer_categories cs on cs.id = pm.consumer_category_id" +
				" JOIN price_lists pl on pl.id = pm.price_list_id" +
				" JOIN catalogs ca on ca.id = p.catalog_id" +
				" JOIN resorts re on re.id = ca.resort_id" +
				" where ca.resort_id = ?" +
				" and ca.version = (select max(version) from catalogs where resort_id = ?)" +
				" and pl.type = 'PRICE' order by p.name, cs.name",
                (rs, rowNum) -> new ProductDTO(
						getJsonValue(rs.getString("product_name")),
                        rs.getBoolean("active"),
						getJsonValue(rs.getString("category_name")),
						rs.getInt("price_list_id"),
						null),
				resortId, resortId
        );
    	
    	return products;
    }

	public Map<Integer, List<ProductPriceHistoryDTO>> getProductsPriceHistory(Integer resortId) {

		Map<Integer, List<ProductPriceHistoryDTO>> priceHistoryMap = new HashMap<>();

		jdbcTemplate.query(
				"select pl.id as price_list_id, pi.unit_price as price, tr.start_date as start_date, tr.end_date as end_date" +
						" from price_lists pl" +
						" JOIN price_list_items pi on pl.id = pi.price_list_id" +
						" join time_periods tp on pi.time_period_id = tp.external_id" +
						" join time_period_ranges tpr on tp.id = tpr.time_period_id" +
						" join time_ranges tr on tr.id = tpr.time_range_id" +
						" JOIN catalogs ca on ca.id = pl.catalog_id" +
						" JOIN resorts re on re.id = ca.resort_id" +
						" where ca.resort_id = ?" +
						" and ca.version = (select max(version) from catalogs where resort_id = ?) and pl.type = 'PRICE'" +
						" order by pi.price_list_id, tr.start_date desc;",
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						do {
							Integer priceListId = rs.getInt("price_list_id");
							ProductPriceHistoryDTO priceHistory = new ProductPriceHistoryDTO(
									priceListId,
									getJsonValue(rs.getString("price"), "amount") + " " + getJsonValue(rs.getString("price"), "currency_code"),
									rs.getTimestamp("start_date").toLocalDateTime(),
									rs.getTimestamp("end_date").toLocalDateTime());
							List<ProductPriceHistoryDTO> priceHistoryList = priceHistoryMap.get(priceListId);
							if (priceHistoryList != null) {
								priceHistoryList.add(priceHistory);
							} else {
								List<ProductPriceHistoryDTO> newPriceHistoryList = new ArrayList<>();
								newPriceHistoryList.add(priceHistory);
								priceHistoryMap.put(priceListId, newPriceHistoryList);
							}
						} while (rs.next());
					}
				} ,
				resortId, resortId
		);

		return priceHistoryMap;
	}

	public List<SeasonDTO> getSeasonsByResortId(Integer resortId) {

		logger.info("getting seasons by resort id: " + resortId);

		List<SeasonDTO> seasons = new ArrayList<>();

		/*DateTimeFormatter seasonDatesFormat = (new DateTimeFormatterBuilder())
				.appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
				.appendLiteral(' ')
				.appendValue(ChronoField.MONTH_OF_YEAR, 3)
				.appendLiteral(' ')
				.appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter();*/

		DateTimeFormatter seasonDatesFormat = DateTimeFormatter.ofPattern("d MMM yyyy").withLocale(Locale.ENGLISH);

		jdbcTemplate.query(
				"select resort_season_id, start_date, end_date, display_name" +
						" from resort_season_time_ranges sr JOIN resort_seasons rs on rs.id = sr.resort_season_id where resort_id = ?" +
						" order by resort_season_id, start_date;",
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						do {
							Integer seasonId = rs.getInt("resort_season_id");
							if (seasons.isEmpty() || seasons.get(seasons.size() - 1).getSeasonId() != seasonId) {
								String seasonName = getJsonValue(rs.getString("display_name"), "en");
								SeasonDTO season = new SeasonDTO(
										seasonId,
										seasonName,
										new StringBuilder()
												.append(seasonName)
												.append(" dates: ")
												.append(rs.getTimestamp("start_date").toLocalDateTime().format(seasonDatesFormat))
												.append(" - ")
												.append(rs.getTimestamp("end_date").toLocalDateTime().format(seasonDatesFormat)).toString(),
										null,
										null
								);
								seasons.add(season);
								logger.info("found season: " + season.getSeasonName());
							} else {
								SeasonDTO season = seasons.get(seasons.size() - 1);
								season.setSeasonDates(
										new StringBuilder(season.getSeasonDates())
												.append(", ")
												.append(rs.getTimestamp("start_date").toLocalDateTime().format(seasonDatesFormat))
												.append(" - ")
												.append(rs.getTimestamp("end_date").toLocalDateTime().format(seasonDatesFormat)).toString()
										);
							}
						} while (rs.next());
					}
				} ,
				resortId
		);

		return seasons;
	}

	public List<CategoryWithPriceDTO> getSeasonsCategories(Integer resortId) {

		List<CategoryWithPriceDTO> categories = new ArrayList<>();

		jdbcTemplate.query(
				"select id, display_name from resort_categories" +
				" where resort_id = ? and deleted_at is null" +
				" order by display_name",
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						do {
							CategoryWithPriceDTO category = new CategoryWithPriceDTO(
									rs.getInt("id"),
									getJsonValue(rs.getString("display_name"), "en"),
									null, null
							);
							categories.add(category);
						} while (rs.next());
					}
				} ,
				resortId
		);
		// sorting by english title
		Collections.sort(categories, Comparator.comparing(CategoryWithPriceDTO::getCategoryName));
		return categories;
	}

	public List<ProductWithCategoryDTO> getProductsBySeasonId(Integer seasonId, List<CategoryWithPriceDTO> categories) {

		logger.info("getProductsBySeasonId called for seasonId=" + seasonId);

		List<ProductWithCategoryDTO> products = new ArrayList<>();

		jdbcTemplate.query(
				"select list_price_cents, list_price_currency, rs.id as resort_season_id, rs.display_name as resort_season_name," +
						" rc.id as resort_category_id, rc.display_name as resort_category_name," +
						" rt.id as resort_ticket_id, rt.display_name as resort_ticket_name," +
						" rtg.name as resort_ticket_group_name," +
							" supplement_days, supplement_amount_cents, supplement_amount_currency " +
						" from resort_ticket_prices rtp" +
						" JOIN resort_seasons rs on rtp.season_id = rs.id" +
						" JOIN resort_categories rc on rtp.category_id = rc.id" +
						" JOIN resort_tickets rt on rtp.ticket_id = rt.id" +
						" JOIN resort_ticket_groups rtg on rtg.id = rt.group_id" +
						" where rs.id = ?" +
						" order by resort_season_id, resort_ticket_id, resort_category_name;",
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						do {
							Integer ticketId = rs.getInt("resort_ticket_id");
							Integer currentCategoryId = rs.getInt("resort_category_id");

							if (products.isEmpty() || !products.get(products.size() - 1).getTicketId().equals(ticketId)) {

								// create a list of empty categories
								List<CategoryWithPriceDTO> newCategories = new ArrayList<>();

								for (int i = 0; i < categories.size(); i++) {
									if (categories.get(i).getCategoryId() == currentCategoryId) {
										newCategories.add(new CategoryWithPriceDTO(
												rs.getInt("resort_category_id"),
												getJsonValue(rs.getString("resort_category_name"), "en"),
												new StringBuilder()
														.append(rs.getInt("list_price_cents") / 100.00)
														.append(rs.getString("list_price_currency"))
														.toString(),
												rs.getInt("supplement_amount_cents") > 0 ?
														new StringBuilder()
																.append((rs.getInt("supplement_amount_cents") + rs.getInt("list_price_cents")) / 100.00)
																.append(rs.getString("supplement_amount_currency"))
																.toString()
														: null
										));
									} else {
										newCategories.add(new CategoryWithPriceDTO(-1, "", null, null));
									}
								}

								String supplementDescription = null;
								String daysOfWeek = rs.getString("supplement_days");
								if (daysOfWeek != null && daysOfWeek.length() > 2) {
									List<DayOfWeek> days = new ArrayList<>();
									StringBuilder supplementDescBuilder = new StringBuilder("Supplements apply on ");
									String[] supplementDays = daysOfWeek.substring(1, daysOfWeek.length() - 1).split(",");
									for (int i = 0; i < supplementDays.length; i++) {
										DayOfWeek day = DayOfWeek.of(Integer.parseInt(supplementDays[i].replaceAll("\"", "")) + 1);
										day = day.minus(1);
										days.add(day);
									}

									days.sort(Comparator.comparing(DayOfWeek::getValue));

									for (int i = 0; i < days.size(); i++) {
										if (i > 0) {
											supplementDescBuilder.append(", ");
										}
										supplementDescBuilder.append(days.get(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
									}

									supplementDescription = supplementDescBuilder.toString();
								}

								ProductWithCategoryDTO product = new ProductWithCategoryDTO(
										ticketId,
										getJsonNestedValue(rs.getString("resort_ticket_name"), "en"),
										getJsonValue(rs.getString("resort_ticket_group_name"), "en"),
										supplementDescription,
										newCategories
								);
								products.add(product);

							} else {
								ProductWithCategoryDTO product = products.get(products.size() - 1);

								for (int i = 0; i < categories.size(); i++) {
									if (categories.get(i).getCategoryId() == currentCategoryId) {
										CategoryWithPriceDTO categoryToEdit = product.getCategories().get(i);
										categoryToEdit.setCategoryId(rs.getInt("resort_category_id"));
										categoryToEdit.setCategoryName(getJsonValue(rs.getString("resort_category_name"), "en"));
										categoryToEdit.setPrice(new StringBuilder()
												.append(rs.getInt("list_price_cents") / 100.00)
												.append(rs.getString("list_price_currency"))
												.toString());
										if (rs.getInt("supplement_amount_cents") > 0) {
											categoryToEdit.setSupplementPrice(new StringBuilder()
													.append((rs.getInt("supplement_amount_cents") + rs.getInt("list_price_cents")) / 100.00)
													.append(rs.getString("supplement_amount_currency"))
													.toString());
										}
									}
								}
							}
						} while (rs.next());
					}
				} ,
				seasonId
		);

		return products;
	}

	private String getJsonValue(String jsonString) {
		try {
			if (jsonString != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode actualObj = mapper.readTree(jsonString);
				while(actualObj.fields().hasNext()) {
					Map.Entry<String, JsonNode> field = actualObj.fields().next();
					JsonNode jsonValue = field.getValue();
					return jsonValue.asText();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while parsing JSON string from products: " + e.getMessage());
		}

		return null;
	}

	private String getJsonValue(String jsonString, String key) {
		try {
			if (jsonString != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode actualObj = mapper.readTree(jsonString);
				return actualObj.get(key).asText();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while parsing JSON string from products: " + e.getMessage());
		}

		return null;
	}

	private String getJsonNestedValue(String jsonString, String key) {
		try {
			if (jsonString != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode actualObj = mapper.readTree(jsonString);
				return actualObj.get(key).get(key).asText();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while parsing JSON string from products: " + e.getMessage());
		}

		return null;
	}

}
