package dasturlash.uz.repository;

import dasturlash.uz.entity.StudentCourseMarkEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer>, PagingAndSortingRepository<StudentCourseMarkEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update StudentCourseMarkEntity set mark =:mark, student.id =:studentId, course.id =:courseId where id = :id")
    public int update(@Param("id") Integer id,@Param("mark") Integer mark,
                      @Param("studentId") Integer studentId,@Param("courseId") Integer courseId);
    @Query("select (scm.id, scm.studentId, scm.courseId, scm.mark, scm.createdDate) from StudentCourseMarkEntity scm where scm.id =:id")
    public StudentCourseMarkEntity getById(@Param("id") Integer id);
    @Query("select (scm.id, scm.studentId, scm.student.name, scm.student.surname, scm.courseId," +
            " scm.course.name, scm.mark, scm.createdDate)from StudentCourseMarkEntity scm where scm.id =: id")
    public StudentCourseMarkEntity getByIdLong(@Param("id") Integer id);
    @Query("select mark from StudentCourseMarkEntity where studentId =:id and createdDate between :from and :to")
    public List<Integer> getMarkByIdAndCreatedDateBetween(@Param("id") Integer id, @Param("from")LocalDateTime from, @Param("to") LocalDateTime to);
    @Query("select mark from StudentCourseMarkEntity where studentId =:studentId order by createdDate desc")
    public List<Integer> getAllMark(@Param("studentId") Integer studentId);
    @Query("select mark from StudentCourseMarkEntity where courseId =:courseId and studentId =:studentId order by createdDate desc")
    public List<Integer> getAllMarkByCourseIdAndStudentId(@Param("courseId") Integer courseId,@Param("studentId") Integer studentId);
    @Query("select scm.mark, scm.course.name from StudentCourseMarkEntity scm where scm.studentId =:studentId order by scm.createdDate desc limit 1")
    public StudentCourseMarkEntity getLastMark(@Param("studentId") Integer studentId);
    @Query("select mark from StudentCourseMarkEntity where studentId =:id order by mark desc limit 3")
    public List<Integer> getTop3Mark(@Param("id") Integer id);
    @Query("select mark from StudentCourseMarkEntity where studentId =:studentId order by createdDate asc limit 1")
    public Integer getFirstMark(@Param("studentId") Integer studentId);
    @Query("select mark from StudentCourseMarkEntity where studentId =:studentId and courseId =:courseId order by createdDate asc limit 1")
    public Integer getFirstMarkByCourseId(@Param("studentId") Integer studentId,@Param("courseId") Integer courseId);
    @Query("select max(mark) from StudentCourseMarkEntity where studentId =:studentId and courseId =:courseId")
    public Integer getMaxMarkByCourseId(@Param("studentId") Integer studentId,@Param("courseId") Integer courseId);
    @Query("select avg (mark) from StudentCourseMarkEntity where studentId =:studentId")
    public Double getAvgMark(@Param("studentId") Integer studentId);
    @Query("select avg(mark) from StudentCourseMarkEntity where studentId =:studentId and courseId =:courseId ")
    public Double getAvgMarkByCourseId(@Param("studentId") Integer studentId,@Param("courseId") Integer courseId);
    @Query("select count (mark) from StudentCourseMarkEntity where studentId =:studentId and mark>:grade")
    public Integer getMarksGreaterThan(@Param("studentId") Integer studentId,@Param("grade") Integer grade);
    @Query("select max (mark) from StudentCourseMarkEntity where courseId =:courseId")
    public Integer getMaxMarkByCourseId(@Param("courseId") Integer courseId);
    @Query("select avg (mark) from StudentCourseMarkEntity where courseId =:courseId")
    public Integer getAvgMarkByCourseId(@Param("courseId") Integer courseId);
    @Query("select count (mark) from StudentCourseMarkEntity where courseId =:courseId")
    public Integer getCountMarkByCourseId(@Param("courseId") Integer courseId);

    public Page<StudentCourseMarkEntity> findByStudentId(@Param("studentId") Integer studentId, Pageable pageable);
    public Page<StudentCourseMarkEntity> findByCourseId(@Param("studentId") Integer courseId, Pageable pageable);
}
