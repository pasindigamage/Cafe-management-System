package lk.ijse.buddiescafe.model.TM;
import lk.ijse.buddiescafe.model.Inventory;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryTM extends Inventory {
    private String id;
    private String description;
    private String date;
    private String unitPrice;
    private String qty;
}
