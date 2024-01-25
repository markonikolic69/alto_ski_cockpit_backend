package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CategoryWithPriceDTO {
    private Integer categoryId;
    private String categoryName;
    private String price;
    private String supplementPrice;
}
