package dasturlash.uz.service;

import dasturlash.uz.dto.CourseDTO;
import dasturlash.uz.dto.CourseFilterDTO;
import dasturlash.uz.dto.StudentDTO;
import dasturlash.uz.dto.StudentFilterDTO;
import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.entity.StudentEntity;
import dasturlash.uz.repository.CourseCustomRepository;
import dasturlash.uz.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseCustomRepository courseCustomRepository;

    public CourseDTO create(CourseDTO courseDTO) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(courseDTO.getName());
        courseEntity.setPrice(courseDTO.getPrice());
        courseEntity.setDuration(courseDTO.getDuration());
        courseEntity.setCreatedDate(LocalDateTime.now());
        courseRepository.save(courseEntity);
        courseDTO.setId(courseEntity.getId());
        return courseDTO;
    }

    public CourseDTO getByID(Integer id) {
        Optional<CourseEntity> courseEntity = courseRepository.findById(id);
        return courseEntity.map(this::toDTO).orElse(null);
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> iterable = courseRepository.findAll();
        List<CourseDTO> courseDTOList = new LinkedList<>();
        iterable.forEach(courseEntity -> courseDTOList.add(toDTO(courseEntity)));
        return courseDTOList;
    }

    public CourseDTO update(Integer id, CourseDTO dto) {
        int affectedRows = courseRepository.updateCourse(id, dto.getName(), dto.getPrice(), dto.getDuration());
        return dto;
    }

    public boolean delete(Integer id) {
        courseRepository.deleteById(id);
        return true;
    }

    public List<CourseDTO> getByName(String name) {
        List<CourseDTO> courseDTOList = new LinkedList<>();
        List<CourseEntity> list = courseRepository.getByName("%" + name + "%");
        list.forEach(courseEntity -> courseDTOList.add(toDTO(courseEntity)));
        return courseDTOList;
    }

    public List<CourseDTO> getByPrice(Integer price) {
        List<CourseDTO> courseDTOList = new LinkedList<>();
        List<CourseEntity> list = courseRepository.getByPrice(price);
        list.forEach(courseEntity -> courseDTOList.add(toDTO(courseEntity)));
        return courseDTOList;
    }

    public List<CourseDTO> getByDuration(Integer duration) {
        List<CourseDTO> courseDTOList = new LinkedList<>();
        List<CourseEntity> list = courseRepository.getByDuration(duration);
        list.forEach(courseEntity -> courseDTOList.add(toDTO(courseEntity)));
        return courseDTOList;
    }

    public List<CourseDTO> getByPriceBetween(Integer minPrice, Integer maxPrice) {
        List<CourseDTO> courseDTOList = new LinkedList<>();
        List<CourseEntity> list = courseRepository.getByPriceBetween(minPrice, maxPrice);
        list.forEach(courseEntity -> courseDTOList.add(toDTO(courseEntity)));
        return courseDTOList;
    }

    public List<CourseDTO> getByCreatedDateBetween(LocalDate minCreatedDate, LocalDate maxCreatedDate) {
        List<CourseDTO> courseDTOList = new LinkedList<>();
        List<CourseEntity> list = courseRepository.getByCreatedDateBetween(minCreatedDate, maxCreatedDate);
        list.forEach(courseEntity -> courseDTOList.add(toDTO(courseEntity)));
        return courseDTOList;
    }

    public PageImpl<CourseDTO> pagination(int size, int page) {
        PageRequest pageRequest = PageRequest.of(size, page); // order by createDate desc limit ? offset ?
        Page<CourseEntity> pageObj = courseRepository.findAll(pageRequest);// select * from ...

        List<CourseEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<CourseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<CourseDTO>(dtoList, pageRequest, totalElement);
    }

    public PageImpl<CourseDTO> paginationSorted(int size, int page) {
        Sort sort = Sort.by("createdDate").descending(); // order by createDate desc
        PageRequest pageRequest = PageRequest.of(size, page, sort); // order by createDate desc limit ? offset ?
        Page<CourseEntity> pageObj = courseRepository.findAll(pageRequest);// select * from ...

        List<CourseEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<CourseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<CourseDTO>(dtoList, pageRequest, totalElement);
    }

    public PageImpl<CourseDTO> paginationByPrice(int size, int page, Integer price) {
        Sort sort = Sort.by("createdDate").descending(); // order by createDate desc
        PageRequest pageRequest = PageRequest.of(size, page, sort); // order by createDate desc limit ? offset ?
        Page<CourseEntity> pageObj = courseRepository.findByPrice(price, pageRequest);// select * from ...

        List<CourseEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<CourseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<CourseDTO>(dtoList, pageRequest, totalElement);
    }

    public PageImpl<CourseDTO> paginationByPriceBetween(int size, int page, Integer priceFrom, Integer priceTo) {
        Sort sort = Sort.by("createdDate").descending(); // order by createDate desc
        PageRequest pageRequest = PageRequest.of(size, page, sort); // order by createDate desc limit ? offset ?
        Page<CourseEntity> pageObj = courseRepository.findByPriceBetween(priceFrom, priceTo, pageRequest);// select * from ...

        List<CourseEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<CourseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<CourseDTO>(dtoList, pageRequest, totalElement);
    }

    public Page<CourseDTO> filter(CourseFilterDTO filterDTO, int page, int size) {
        Page<CourseEntity> pageObj = courseCustomRepository.filter(filterDTO, page, size);

        List<CourseEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<CourseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<CourseDTO>(dtoList, PageRequest.of(page, size), totalElement);
    }



    public CourseDTO toDTO(CourseEntity courseEntity) {
        CourseDTO dto = new CourseDTO();
        dto.setId(courseEntity.getId());
        dto.setName(courseEntity.getName());
        dto.setPrice(courseEntity.getPrice());
        dto.setDuration(courseEntity.getDuration());
        dto.setCreatedDate(courseEntity.getCreatedDate());
        return dto;
    }
}
