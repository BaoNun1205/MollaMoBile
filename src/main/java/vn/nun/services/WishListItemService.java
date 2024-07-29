package vn.nun.services;

import vn.nun.models.Product;
import vn.nun.models.WishList;
import vn.nun.models.WishListItem;

import java.util.List;

public interface WishListItemService {
    List<WishListItem> findByWishList(WishList wishList);
    Boolean create(WishListItem wishListItem);
    Boolean delete(Integer id);
    boolean existWishlistItem(WishList wishList, Product product);
}
