package lk.ijse.buddiescafe.model;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySupplierDetail {
    private String id;
    private String supplierId;
    private String inventoryId;
    private String date;
    private Double unitPrice;
    private int qty;
    }

