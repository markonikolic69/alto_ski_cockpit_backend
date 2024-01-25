package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductWithCategoryDTO {
    private Integer ticketId;
    private String ticketName;
    private String ticketGroupName;
    private String supplementDescription;
    private List<CategoryWithPriceDTO> categories;
}
