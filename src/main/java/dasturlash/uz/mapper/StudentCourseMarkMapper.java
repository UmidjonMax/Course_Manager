package dasturlash.uz.mapper;

import java.time.LocalDate;

public interface StudentCourseMarkMapper {
    Integer getId();
    Integer getStudentId();
    String  getStudentName();
    String  getStudentSurname();
    Integer getCourseId();
    String  getCourseName();
    Integer getMark();
    LocalDate getCreatedDate();
}
