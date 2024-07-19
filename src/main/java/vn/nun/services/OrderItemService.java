package vn.nun.services;

import vn.nun.models.OrderPlaced;
import vn.nun.models.OrderItem;

public interface OrderItemService {
    Boolean save(OrderItem orderItem);
}
