package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nun.models.OrderPlaced;

@Repository
public interface OrderRepository extends JpaRepository<OrderPlaced, Integer> {
}
