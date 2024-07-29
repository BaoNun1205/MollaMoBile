package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.Product;
import vn.nun.models.WishList;
import vn.nun.models.WishListItem;
import vn.nun.repository.WishListItemRepository;
import vn.nun.services.WishListItemService;

import java.util.List;

@Service
public class WishListItemServiceImpl implements WishListItemService {
    @Autowired
    private WishListItemRepository wishListItemRepository;
    @Override
    public List<WishListItem> findByWishList(WishList wishList) {
        return wishListItemRepository.findByWishList(wishList);
    }

    @Override
    public Boolean create(WishListItem wishListItem) {
        try {
            wishListItemRepository.save(wishListItem);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            wishListItemRepository.delete(wishListItemRepository.findById(id).get());
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existWishlistItem(WishList wishList, Product product) {
        return wishListItemRepository.existsByWishListAndProduct(wishList, product);
    }
}
