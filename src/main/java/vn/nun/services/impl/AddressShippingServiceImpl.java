package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.nun.models.AddressShipping;
import vn.nun.models.CustomUserDetail;
import vn.nun.models.User;
import vn.nun.repository.AddressShippingRepository;
import vn.nun.services.AddressShippingService;

@Service
public class AddressShippingServiceImpl implements AddressShippingService {
    @Autowired
    private AddressShippingRepository addressShippingRepository;
    @Override
    public Boolean save(AddressShipping addressShipping) {
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

    @Override
    public AddressShipping findByUser(User user) {
        return addressShippingRepository.findByUser(user);
    }

    @Override
    public AddressShipping getAddressShippingForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User user = userDetails.getUser();
        return findByUser(user);
    }
}
