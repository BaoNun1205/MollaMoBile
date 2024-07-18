package vn.nun.services;

import vn.nun.models.AddressShipping;

public interface AddressShippingService {
    Boolean create (AddressShipping addressShipping);
    AddressShipping findById(Integer id);
}
