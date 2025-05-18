package dasturlash.uz.repository;

import dasturlash.uz.dto.CourseFilterDTO;
import dasturlash.uz.dto.SCMFilterDTO;
import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.entity.StudentCourseMarkEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SCMCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl<StudentCourseMarkEntity> filter(SCMFilterDTO filterDTO, int page, int size) {
        StringBuilder conditionBuilder = new StringBuilder(" where 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getId() != null) {
            conditionBuilder.append(" and id=:id ");
            params.put("id", filterDTO.getId());
        }

        //..
        StringBuilder selectBuilder = new StringBuilder("From StudentCourseMarkEntity ");
        selectBuilder.append(conditionBuilder);
        selectBuilder.append(" order by createdDate desc ");


        StringBuilder countBuilder = new StringBuilder(" select count(*) From StudentCourseMarkEntity ");
        countBuilder.append(conditionBuilder);

        // get content
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(page * size); // offset
        List<StudentCourseMarkEntity> entityList = selectQuery.getResultList(); // get content

        // get totalCount
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) countQuery.getSingleResult();

        return new PageImpl<>(entityList, PageRequest.of(page, size), totalCount);
    }
}
