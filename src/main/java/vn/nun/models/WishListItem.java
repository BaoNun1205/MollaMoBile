package vn.nun.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "wishListId", referencedColumnName = "id")
    private WishList wishList;
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
}
