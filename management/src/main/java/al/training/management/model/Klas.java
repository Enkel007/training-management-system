package al.training.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Klas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String subject;
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "block_id", referencedColumnName = "id")
    private BlockOfClasses blockOfClasses;
    
    public Klas(String subject, Date date, BlockOfClasses block) {
        this.subject = subject;
        this.date = date;
        this.blockOfClasses = block;
    }
}