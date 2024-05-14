package lk.ijse.buddiescafe.model;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySupplierDetail {
    private String supplierId;
    private String inventoryId;
    private String date;
}

