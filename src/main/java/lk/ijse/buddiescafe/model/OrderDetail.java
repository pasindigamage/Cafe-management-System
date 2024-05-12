package lk.ijse.buddiescafe.model;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetail {
        private String orderId;
        private String foodItemId;
        private int qty;
}
