package dasturlash.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "s_c_mark")
@Getter
@Setter
public class StudentCourseMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer mark;
    private LocalDate createdDate;
    @Column(name = "student_id")
    private Integer studentId;
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity student;

    @Column(name = "course_id")
    private Integer courseId;
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private CourseEntity course;
}
