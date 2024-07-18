package vn.nun.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.nun.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressShippingDTO {
    private Integer id;
    private String recipientName;
    private String address;
    private String phone;
}
