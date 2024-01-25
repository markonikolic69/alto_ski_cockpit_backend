package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.FailedTransactionDTO;
import ski.alto.cockpit.server.model.SmartCardUsages;
import ski.alto.cockpit.server.model.TransactionDTO;
import ski.alto.cockpit.server.utility.OwnershipUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionsDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    TransactionsDAO transactionsDAO;

    @Test
    void getTransactionByCardNumberAndResortIdForDateRange() {
        List<TransactionDTO> result = transactionsDAO.getTransactionByCardNumberAndResortIdForDateRange(
                "01-16147133535016079240-5",
                "2021-12-28T09:55:09.000",
                "2021-12-28 15:38:23.000",
                40
        );
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void getLastStripeChargeFailure() {
        Integer result = transactionsDAO.getLastStripeChargeFailure(OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);

    }

    @Test
    void getStripeChargeFailures() {
        List<FailedTransactionDTO> result = transactionsDAO.getStripeChargeFailures(100,
                OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        logger.info(result != null ? "" + result.size() : "No result found");
        assert (result != null);
    }

    @Test
    void getStripeChargeFailuresFromToday() {
        List<FailedTransactionDTO> result = transactionsDAO.getStripeChargeFailuresFromToday(
                OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }
}