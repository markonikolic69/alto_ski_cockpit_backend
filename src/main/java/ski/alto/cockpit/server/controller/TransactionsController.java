package ski.alto.cockpit.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ski.alto.cockpit.server.controller.request.FailedTransactionsRequest;
import ski.alto.cockpit.server.model.ActivityDTO;
import ski.alto.cockpit.server.model.FailedTransactionDTO;
import ski.alto.cockpit.server.model.TransactionDTO;
import ski.alto.cockpit.server.repository.TransactionsDAO;
import ski.alto.cockpit.server.utility.OwnershipUtil;

@RestController
public class TransactionsController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    TransactionsDAO transactionsDAO;

//    @CrossOrigin(origins = "http://94.127.4.240:4200")	//	SERVER CONFIG
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081"})
    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(@RequestParam Map<String,String> requestParams) {

    	String card_number = requestParams.get("card_number");
    	String start_date = requestParams.get("start_date");
    	String end_date = requestParams.get("end_date");
    	Integer resort_id = Integer.parseInt(requestParams.get("resort_id"));
		
    	
		
        boolean result = false;
        List<TransactionDTO> transactions = transactionsDAO.getTransactionByCardNumberAndResortIdForDateRange(card_number, start_date, end_date, resort_id);
        
        return transactions;
    }

    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081"})
    @PostMapping("/last_failed_transaction")
    public Integer getLastFailedTransactionId(@RequestParam String ownership) {
        return transactionsDAO.getLastStripeChargeFailure(OwnershipUtil.parseOwnership(ownership));
    }

    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081"})
    @PostMapping("/failed_transactions")
    public List<FailedTransactionDTO> getFailedTransactions(@RequestBody FailedTransactionsRequest request) {
        return transactionsDAO.getStripeChargeFailures(request.getLastKnownFailedChargeId(), OwnershipUtil.parseOwnership(request.getOwnership()));
    }

    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081"})
    @PostMapping("/failed_transactions_today")
    public List<FailedTransactionDTO> getFailedTransactionsToday(@RequestParam String ownership) {
        return transactionsDAO.getStripeChargeFailuresFromToday(OwnershipUtil.parseOwnership(ownership));
    }
}
