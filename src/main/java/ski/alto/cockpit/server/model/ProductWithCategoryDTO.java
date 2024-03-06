package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductWithCategoryDTO {
    private Integer ticketId;
    private String ticketName;
    private String ticketGroupName;
    private String supplementDescription;
    private List<CategoryWithPriceDTO> categories;
    
    public void addCategory(CategoryWithPriceDTO category) {
    	if(categories == null) {
    		categories = new ArrayList<CategoryWithPriceDTO>();
    	}
    	categories.add(category);
    }
}
