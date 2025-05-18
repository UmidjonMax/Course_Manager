package dasturlash.uz.dto;

import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.entity.StudentEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class SCMFilterDTO {
    private Integer id;
    private StudentEntity studentId;
    private CourseEntity courseId;
    private Integer mark;
    private LocalDate createdDate;
}
