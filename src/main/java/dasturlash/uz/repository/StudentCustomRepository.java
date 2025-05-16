package dasturlash.uz.repository;

import dasturlash.uz.dto.StudentFilterDTO;
import dasturlash.uz.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl<StudentEntity> filter(StudentFilterDTO filterDTO, int page, int size) {
        StringBuilder conditionBuilder = new StringBuilder(" where 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getId() != null) {
            conditionBuilder.append(" and id=:id ");
            params.put("id", filterDTO.getId());
        }
        if (filterDTO.getName() != null) {
            conditionBuilder.append(" and name like :name ");
            params.put("name", "%" + filterDTO.getName() + "%");
        }
        if (filterDTO.getSurname() != null) {
            conditionBuilder.append(" and surname like :surname ");
            params.put("surname", "%" + filterDTO.getSurname() + "%");
        }
        if (filterDTO.getLevel() != null) {
            conditionBuilder.append(" and level = :level ");
            params.put("level", filterDTO.getLevel());
        }
        if (filterDTO.getAge() != null) {
            conditionBuilder.append(" and age = :age ");
            params.put("age", filterDTO.getAge());
        }
        if (filterDTO.getGender() != null) {
            conditionBuilder.append(" and gender = :gender ");
            params.put("gender", filterDTO.getGender());
        }
        //..
        StringBuilder selectBuilder = new StringBuilder("From StudentEntity ");
        selectBuilder.append(conditionBuilder);
        selectBuilder.append(" order by createdDate desc ");
        // "From StudentEntity   where 1=1 and id=:id


        StringBuilder countBuilder = new StringBuilder(" select count(*) From StudentEntity ");
        countBuilder.append(conditionBuilder);
        // select count(*) From StudentEntity where 1=1 and id=:id

        // get content
        Query selectQuery = entityManager.createQuery(conditionBuilder.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(page * size); // offset
        List<StudentEntity> entityList = selectQuery.getResultList(); // get content

        // get totalCount
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) countQuery.getSingleResult();

        return new PageImpl<>(entityList, null, totalCount);
    }
}
