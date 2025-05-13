package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CourseDTO {
    private Integer id;
    private String name;
    private Integer price;
    private Integer duration;
    private LocalDate createdDate;
}
