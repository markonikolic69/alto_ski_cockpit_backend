package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ProductDAO productDAO;

    @Test
    void getProductsByResortId() {
        List<ProductDTO> result = productDAO.getProductsByResortId(3023);
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void getProductsPriceHistory() {
        Map<Integer, List<ProductPriceHistoryDTO>> result = productDAO.getProductsPriceHistory(3023);
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void getSeasonsByResortId() {
        List<SeasonDTO> result = productDAO.getSeasonsByResortId(3023);
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void getSeasonsCategories() {
        List<CategoryWithPriceDTO> result = productDAO.getSeasonsCategories(3023);
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void getProductsBySeasonId() {
        List<CategoryWithPriceDTO> seasons = productDAO.getSeasonsCategories(3);
        List<ProductWithCategoryDTO> result = productDAO.getProductsBySeasonId(3, seasons);
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }
}