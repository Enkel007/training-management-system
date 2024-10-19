package al.training.management.request;

import al.training.management.model.BlockOfClasses;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateClassRequest {
    private String subject;
    private Date date;
    private BlockOfClasses blockOfClasses;
}