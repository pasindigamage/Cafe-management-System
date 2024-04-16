package lk.ijse.buddiescafe.model;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier {
    private String id;
    private String nic;
    private String name;
    private String companyAddress;
    private String email;
    private Integer contact;
}
