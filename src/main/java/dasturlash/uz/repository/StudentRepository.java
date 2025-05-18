package dasturlash.uz.repository;

import dasturlash.uz.entity.StudentEntity;
import dasturlash.uz.mapper.StudentMapper;
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

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>, PagingAndSortingRepository<StudentEntity, Integer> {
    @Query("from StudentEntity where name like :name")
    public List<StudentEntity> getByName(@Param("name") String name);
    @Query("from StudentEntity where surname like :surname")
    public List<StudentEntity> getBySurname(@Param("surname") String surname);
    @Query("from StudentEntity where level = :level")
    public List<StudentEntity> getByLevel(@Param("level") Integer level);
    @Query("from StudentEntity where age = :age")
    public List<StudentEntity> getByAge(@Param("age") Integer age);
    @Query("from StudentEntity where gender = :gender")
    public List<StudentEntity> getByGender(@Param("gender") String gender);
    @Query("from StudentEntity where createdDate between :from and :to")
    public List<StudentEntity> getByCreatedDateBetween(@Param("from") LocalDateTime from,@Param("to") LocalDateTime to);

    List<StudentEntity> findTop3ByName(String name);
    @Query("select count(*) from StudentEntity where name = :name")
    Integer count(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("update StudentEntity  set name =:name , surname=:surname, age =:age, level =:level, gender =:gender where id=:id")
    int updateStudent(@Param("id") Integer id, @Param("name") String name, @Param("surname") String surname
            , @Param("age") Integer age, @Param("level") Integer level, @Param("gender") String gender);

    public Page<StudentEntity> findByLevel(Integer level, Pageable pageable);

    public Page<StudentEntity> findByGender(String gender, Pageable pageable);

    @Query("select  id, name From StudentEntity  where id =?1")
    List<Object[]> byId(Integer id);
    // Object[] -> [id,name]
    @Query("select  id as id, name as name From StudentEntity  where id =?1")
    List<StudentMapper> byIdMapper(Integer id);

}