package vn.nun.controllers.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nun.models.Category;
import vn.nun.models.Image;
import vn.nun.models.Product;
import vn.nun.services.CategoryService;
import vn.nun.services.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/category")
public class CategoryUserController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public String DetailCategory(Model model, @PathVariable("id") Integer id){
        Category category = categoryService.findById(id);
        List<Product> listProduct = category.getProducts();

        model.addAttribute("category", category);
        model.addAttribute("listProduct", listProduct);
        return ("user/category");
    }

    @PostMapping("/filter-products")
    public ResponseEntity<Map<String, Object>> filterProducts(@RequestBody Map<String, Object> requestBody) {
        List<String> filters = (List<String>) requestBody.get("filters");
        // Chuyển đổi giá trị minPrice và maxPrice từ String sang Double
        Double minPrice = null;
        Double maxPrice = null;
        try {
            if (requestBody.get("minPrice") != null) {
                minPrice = Double.parseDouble(requestBody.get("minPrice").toString());
            }
            if (requestBody.get("maxPrice") != null) {
                maxPrice = Double.parseDouble(requestBody.get("maxPrice").toString());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Xử lý lỗi nếu giá trị không thể chuyển đổi
        }

        // Chuyển đổi giá trị categoryId từ String sang Integer
        Integer categoryId = null;
        try {
            if (requestBody.get("categoryId") != null) {
                categoryId = Integer.parseInt(requestBody.get("categoryId").toString());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Xử lý lỗi nếu giá trị không thể chuyển đổi
        }

        if (filters == null) {
            filters = new ArrayList<>(); // Hoặc xử lý khác nếu filters là null
        }

        List<Product> products;

        if (minPrice != null && maxPrice != null) {
            products = productService.filterByPriceSliderAndCategory(minPrice, maxPrice, categoryService.findById(categoryId));
        } else {
            products = productService.filterByPriceAndCategory(filters, categoryService.findById(categoryId)); // Nếu không có minPrice và maxPrice
        }

        for (Product product : products){
            for (Image image : product.getImages()){
                image.setProduct(null);
            }
            product.setCategory(null);
            product.setCartItem(null);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("totalCount", products.size());
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter-products-by-price-slider")
    public ResponseEntity<Map<String, Object>> filterProductsByPriceSlider(@RequestBody Map<String, Object> requestBody) {
        List<String> filters = (List<String>) requestBody.get("filters");
        Double minPrice = (Double) requestBody.get("minPrice");
        Double maxPrice = (Double) requestBody.get("maxPrice");
        Integer categoryId = (Integer) requestBody.get("categoryId");

//        log.info("Min Price: " + minPrice);
//        log.info("Max Price: " + maxPrice);
//        log.info("Category ID: " + categoryId);
//
//        if (filters == null) {
//            filters = new ArrayList<>(); // Hoặc xử lý khác nếu filters là null
//        }

        List<Product> products = productService.filterByPriceSliderAndCategory(minPrice, maxPrice, categoryService.findById(3));
//        if (minPrice != null && maxPrice != null) {
//            products = productService.filterByPriceSliderAndCategory(minPrice, maxPrice, categoryService.findById(categoryId));
//        } else {
//            products = productService.filterByPriceAndCategory(filters, categoryService.findById(categoryId)); // Nếu không có minPrice và maxPrice
//        }

        for (Product product : products) {
            for (Image image : product.getImages()){
                image.setProduct(null);
            }
            product.setCategory(null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

}
