package lk.ijse.buddiescafe.model.TM;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySupplierDetailTM {
    private String id;
    private String description;
    private String supName;
    private String date;
    private String unitPrice;
    private String qty;

}