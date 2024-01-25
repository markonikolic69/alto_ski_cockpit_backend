package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductDTO {
    private String productName;
    private Boolean active;
    private String categoryName;
    private Integer priceListId;
    private List<ProductPriceHistoryDTO> priceHistory;
}
