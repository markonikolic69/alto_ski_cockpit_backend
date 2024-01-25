package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductPriceHistoryDTO {
    private Integer priceListId;
    private String price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
