package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nun.models.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
