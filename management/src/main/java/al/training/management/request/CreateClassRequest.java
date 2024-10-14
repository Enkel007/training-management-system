package al.training.management.request;

import al.training.management.model.BlockOfClasses;
import lombok.Data;

import java.util.Date;

@Data
public class CreateClassRequest {
    private String subject;
    private Date date;
    private BlockOfClasses blockOfClasses;
}
