package dasturlash.uz.dto;

import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.entity.StudentEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StudentCourseMarkDTO {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Integer mark;
    private LocalDate createdDate;
    private CourseDTO course;
    private StudentDTO student;
}
