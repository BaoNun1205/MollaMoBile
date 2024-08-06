package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.nun.models.Category;
import vn.nun.models.Image;
import vn.nun.models.Product;
import vn.nun.services.CategoryService;
import vn.nun.services.ProductService;

import java.util.*;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewShop(Model model){
        List<Product> listProduct = productService.getAll();
//        model.addAttribute("category", new Category());
        model.addAttribute("listProduct", listProduct);
        return "user/shop";
    }

    @PostMapping("/filter-products")
    public ResponseEntity<Map<String, Object>> filterProducts(@RequestBody Map<String, Object> requestBody) {
        // Log đầu vào
        System.out.println("Request Body: " + requestBody);

        // Lấy danh sách bộ lọc (filters)
        List<String> filters = (List<String>) requestBody.get("filters");
        if (filters == null) {
            filters = new ArrayList<>(); // Nếu filters là null, khởi tạo với danh sách rỗng
        }

        // Lấy danh sách categoryIds và chuyển đổi từ String sang Integer
        List<Object> categoryIds = (List<Object>) requestBody.get("categoryIds");
        System.out.println(categoryIds);
        List<Integer> categoryIntIds = new ArrayList<>();
        if (categoryIds != null) {
            for (Object cateIdObj : categoryIds) {
                try {
                    // Kiểm tra kiểu dữ liệu của cateIdObj và chuyển đổi phù hợp
                    Integer categoryId = null;
                    if (cateIdObj instanceof String) {
                        categoryId = Integer.parseInt((String) cateIdObj);
                    } else if (cateIdObj instanceof Integer) {
                        categoryId = (Integer) cateIdObj;
                    }
                    if (categoryId != null) {
                        categoryIntIds.add(categoryId);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Xử lý lỗi nếu giá trị không thể chuyển đổi
                }
            }
        }

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

        // Log các giá trị đầu ra
        System.out.println("Min Price: " + minPrice + ", Max Price: " + maxPrice);
        System.out.println("Category IDs: " + categoryIntIds);

        // Lọc sản phẩm
        Set<Product> products = new HashSet<>(); // Sử dụng Set để loại bỏ trùng lặp
        if (minPrice != null && maxPrice != null) {
            if (categoryIntIds.isEmpty()) {
                products.addAll(productService.filterByPriceSliderAndCategory(minPrice, maxPrice, null));
            } else {
                for (Integer categoryId : categoryIntIds) {
                    products.addAll(productService.filterByPriceSliderAndCategory(minPrice, maxPrice, categoryService.findById(categoryId)));
                }
            }
        } else {
            if (categoryIntIds.isEmpty()) {
                products.addAll(productService.filterByPriceAndCategory(filters, null));
            } else {
                for (Integer categoryId : categoryIntIds) {
                    products.addAll(productService.filterByPriceAndCategory(filters, categoryService.findById(categoryId)));
                }
            }
        }

        // Xóa thông tin không cần thiết
        for (Product product : products) {
            for (Image image : product.getImages()) {
                image.setProduct(null);
            }
            product.setCategory(null);
            product.setCartItem(null);
        }

        // Tạo phản hồi
        Map<String, Object> response = new HashMap<>();
        response.put("totalCount", products.size());
        response.put("products", new ArrayList<>(products)); // Chuyển Set thành List

        // Log phản hồi
        System.out.println("Response: " + response);

        return ResponseEntity.ok(response);
    }
}
