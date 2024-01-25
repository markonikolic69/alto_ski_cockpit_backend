package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SeasonDTO {
    private Integer seasonId;
    private String seasonName;
    private String seasonDates;
    private List<CategoryWithPriceDTO> categories;
    private List<ProductWithCategoryDTO> products;
}
