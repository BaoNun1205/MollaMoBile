package vn.nun.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private OrderPlaced order;
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
    private Integer count;
}
