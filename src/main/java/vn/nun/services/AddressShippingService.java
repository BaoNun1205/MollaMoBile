package vn.nun.services;

import vn.nun.models.AddressShipping;
import vn.nun.models.User;

public interface AddressShippingService {
    AddressShipping save (AddressShipping addressShipping);
    AddressShipping findById(Integer id);
    AddressShipping findByUser(User user);
    AddressShipping getAddressShippingForCurrentUser();
}
