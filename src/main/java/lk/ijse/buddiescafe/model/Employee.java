package lk.ijse.buddiescafe.model;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    private String id;
    private String name;
    private String position;
    private String address;
    private String email;
    private Integer contact;
}
