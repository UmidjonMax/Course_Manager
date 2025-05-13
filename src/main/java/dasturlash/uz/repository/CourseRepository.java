package dasturlash.uz.repository;

import dasturlash.uz.entity.CourseEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer>, PagingAndSortingRepository<CourseEntity, Integer> {
    @Query("from CourseEntity where name like :name")
    public List<CourseEntity> getByName(@Param("name") String name);
    @Query("from CourseEntity where price =: price")
    public List<CourseEntity> getByPrice(@Param("price") Integer price);
    @Query("from CourseEntity where duration =: duration")
    public List<CourseEntity> getByDuration(@Param("duration") Integer duration);
    @Query("from CourseEntity where price between :from and :to")
    public List<CourseEntity> getByPriceBetween(@Param("from") Integer from,@Param("to") Integer to);
    @Query("from CourseEntity where createdDate between :from and :to")
    public List<CourseEntity> getByCreatedDateBetween(@Param("from") LocalDate from,@Param("to") LocalDate to);
    @Transactional
    @Modifying
    @Query("update CourseEntity set name = :name , price = :price, duration = :duration where id = :id")
    public int updateCourse(@Param("id") Integer id, @Param("name") String name,@Param("price") Integer price,@Param("duration") Integer duration);

    public Page<CourseEntity> findByPrice(Integer price, Pageable pageable);

    public Page<CourseEntity> findByPriceBetween(Integer price1, Integer price2, Pageable pageable);
}
