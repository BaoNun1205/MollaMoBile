package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.AddressShipping;
import vn.nun.repository.AddressShippingRepository;
import vn.nun.services.AddressShippingService;

@Service
public class AddressShippingServiceImpl implements AddressShippingService {
    @Autowired
    private AddressShippingRepository addressShippingRepository;
    @Override
    public Boolean create(AddressShipping addressShipping) {
        try {
            addressShippingRepository.save(addressShipping);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AddressShipping findById(Integer id) {
        return addressShippingRepository.findById(id).get();
    }
}
