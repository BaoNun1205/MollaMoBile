package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nun.models.Product;
import vn.nun.models.WishList;
import vn.nun.models.WishListItem;

import java.util.List;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItem, Integer> {
    List<WishListItem> findByWishList(WishList wishList);
    boolean existsByWishListAndProduct(WishList wishList, Product product);
}
