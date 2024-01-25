package ski.alto.cockpit.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.model.CategoryWithPriceDTO;
import ski.alto.cockpit.server.model.ProductDTO;
import ski.alto.cockpit.server.model.ProductPriceHistoryDTO;
import ski.alto.cockpit.server.model.SeasonDTO;
import ski.alto.cockpit.server.repository.ProductDAO;

import java.util.List;
import java.util.Map;

@Service
public class ProductsService {

    @Autowired
    ProductDAO productDAO;

    public List<ProductDTO> getProductsByResortId(Integer resortId) {

        List<ProductDTO> products = productDAO.getProductsByResortId(resortId);
        Map<Integer, List<ProductPriceHistoryDTO>> priceHistoryMap = productDAO.getProductsPriceHistory(resortId);

        for (ProductDTO product: products) {
            List<ProductPriceHistoryDTO> priceHistory = priceHistoryMap.get(product.getPriceListId());
            product.setPriceHistory(priceHistory);
        }

        return products;
    }

    public List<SeasonDTO> getSeasonsByResortId(Integer resortId) {

        List<SeasonDTO> seasons = productDAO.getSeasonsByResortId(resortId);
        List<CategoryWithPriceDTO> categories = productDAO.getSeasonsCategories(resortId);
        for (SeasonDTO season: seasons) {
            season.setCategories(categories);
            season.setProducts(productDAO.getProductsBySeasonId(season.getSeasonId(), categories));
        }
        return seasons;
    }
}
