package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailedTransactionDTO {
    private String product_name;
    private String date;
    private Integer transaction_id;	// for now id
    private Integer price;	// charged_price
    private String currency;	//	charged_currency
    private String resort_name;
    private String stripe_id;
}
