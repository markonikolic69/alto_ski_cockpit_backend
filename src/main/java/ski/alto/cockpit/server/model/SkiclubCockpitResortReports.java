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
@Table(name = "skiclub_cockpit_resort_reports")
public class SkiclubCockpitResortReports {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "resort_id")
	    private Integer resortId;

	    @Column(name = "product_name")
	    private String productName;

	    @Column(name = "transaction_date")
	    private LocalDateTime transactionDate;

	    @Column(name = "transaction_amount_gross")
	    private Double transactionAmountGross;

	    @Column(name = "transaction_amount_net")
	    private Double transactionAmountNet;

	    @Column(name = "report_id")
	    private Integer reportId;

}
