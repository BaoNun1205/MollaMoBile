package vn.nun.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.nun.models.OrderPlaced;

@Repository
public interface OrderRepository extends JpaRepository<OrderPlaced, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderPlaced o WHERE o.orderItems IS EMPTY")
    void deleteByOrderItemsIsEmpty();
}
