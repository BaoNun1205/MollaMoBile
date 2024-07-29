package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.User;
import vn.nun.models.WishList;
import vn.nun.repository.WishListRepository;
import vn.nun.services.UserService;
import vn.nun.services.WishListService;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserService userService;
    @Override
    public WishList findByUser(User user) {
        return wishListRepository.findByUser(user);
    }

    @Override
    public Boolean create(WishList wishList) {
        try {
            wishListRepository.save(wishList);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public WishList getWishlistCurrentUser() {
        User user = userService.currentUser();
        return findByUser(user);
    }
}
