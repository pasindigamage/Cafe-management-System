package lk.ijse.buddiescafe.model.TM;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryTM{
    private String id;
    private String description;
    private String supName;
    private String unitPrice;
    private String qty;
    private String date;
}