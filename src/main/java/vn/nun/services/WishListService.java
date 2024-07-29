package vn.nun.services;

import vn.nun.models.User;
import vn.nun.models.WishList;

import java.util.List;

public interface WishListService {
    WishList findByUser(User user);
    Boolean create(WishList wishList);
    WishList getWishlistCurrentUser();
}
