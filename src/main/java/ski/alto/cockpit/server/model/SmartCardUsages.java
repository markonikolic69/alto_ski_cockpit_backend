package ski.alto.cockpit.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "smart_card_usages")
public class SmartCardUsages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resort_id")
    private Integer resortId;

    @Column(name = "charged_product_name")
    private String chargedProductName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "charged_price")
    private Double chargedPrice;

    @Column(name = "charged_currency")
    private String chargedCurrency;

    @Column(name = "charged_consumer_category_name")
    private String chargedConsumerCategoryName;

}
