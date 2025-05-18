package ma.enset.Digital_Banking.dtos;

import lombok.Data;


@Data
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
}
