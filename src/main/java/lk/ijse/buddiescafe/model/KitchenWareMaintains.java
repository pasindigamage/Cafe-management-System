package lk.ijse.buddiescafe.model;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class KitchenWareMaintains {
    private String id;
    private String kitchenWareId;
    private String description;
    private String date;
    private String amount;
}
