package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nun.models.AddressShipping;

@Repository
public interface AddressShippingRepository extends JpaRepository<AddressShipping, Integer> {
}
