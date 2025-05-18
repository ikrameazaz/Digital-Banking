package ma.enset.Digital_Banking.dtos;

import lombok.Data;
@Data
public class TransferRequestDTO {
    private String sourceAccountId;
    private String destinationAccountId;
    private double amount;
    private String description;
}
