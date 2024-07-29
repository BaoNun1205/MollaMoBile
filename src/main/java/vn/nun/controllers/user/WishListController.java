package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.nun.models.Product;
import vn.nun.models.WishList;
import vn.nun.models.WishListItem;
import vn.nun.services.ProductService;
import vn.nun.services.WishListItemService;
import vn.nun.services.WishListService;

import java.util.List;

@Controller
@RequestMapping("/wish-list")
public class WishListController {
    @Autowired
    private WishListService wishListService;
    @Autowired
    private WishListItemService wishListItemService;
    @Autowired
    private ProductService productService;
    @GetMapping
    public String getWishList(Model model){
        return "user/wishList";
    }

    @GetMapping("/add-wish-list/{id}")
    public ResponseEntity<Void> addWishlist(@PathVariable("id") Integer id){
        try {
            WishList wishList = wishListService.getWishlistCurrentUser();

            Product product = productService.findById(id);
            if (wishListItemService.existWishlistItem(wishList, product)){
                // Trả về phản hồi thành công
                return ResponseEntity.noContent().build(); // 204 No Content
            } else {
                WishListItem wishListItem = new WishListItem();
                wishListItem.setWishList(wishList);
                wishListItem.setProduct(product);
                wishListItemService.create(wishListItem);
            }
            // Trả về phản hồi thành công
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e){
            e.printStackTrace();
            // Trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @GetMapping("/delete-item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Integer id){
        try {
            wishListItemService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
