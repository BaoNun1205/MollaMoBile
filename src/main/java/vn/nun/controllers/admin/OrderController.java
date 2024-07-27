package vn.nun.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nun.models.OrderItem;
import vn.nun.models.OrderPlaced;
import vn.nun.services.OrderItemService;
import vn.nun.services.OrderPlacedService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderPlacedService orderPlacedService;
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/order")
    public String getOrders(Model model){
        try {
            orderPlacedService.deleteByOrderItemsIsEmpty();
        } catch (Exception e){
            e.printStackTrace();
        }

        List<OrderPlaced> list = orderPlacedService.getAll();
        model.addAttribute("list", list);
        return "admin/order/index";
    }

    @GetMapping("/detail-order/{id}")
    public String detailOrder(@PathVariable("id") Integer id, Model model){
        List<OrderItem> list = orderItemService.findByOrder(orderPlacedService.findById(id));
        model.addAttribute("list", list);
        return "admin/order/detail";
    }

    @PostMapping("/update-status-order")
    public ResponseEntity<String> updateOrderStatus(@RequestParam("id") Integer id, @RequestParam("status") String status) {
        OrderPlaced order = orderPlacedService.findById(id);
        if (order != null) {
            orderPlacedService.updateStatus(order, status);
            return ResponseEntity.ok("Status updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
    }
}
