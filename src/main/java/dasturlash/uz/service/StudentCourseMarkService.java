package dasturlash.uz.service;

import dasturlash.uz.dto.CourseDTO;
import dasturlash.uz.dto.CourseFilterDTO;
import dasturlash.uz.dto.SCMFilterDTO;
import dasturlash.uz.dto.StudentCourseMarkDTO;
import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.entity.StudentCourseMarkEntity;
import dasturlash.uz.entity.StudentEntity;
import dasturlash.uz.mapper.StudentCourseMarkMapper;
import dasturlash.uz.repository.CourseRepository;
import dasturlash.uz.repository.SCMCustomRepository;
import dasturlash.uz.repository.StudentCourseMarkRepository;
import dasturlash.uz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;
    @Autowired
    private SCMCustomRepository scmRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public StudentCourseMarkDTO create(StudentCourseMarkDTO dto) {
        StudentEntity student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        CourseEntity   course  = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        StudentCourseMarkEntity entity = new StudentCourseMarkEntity();
        entity.setStudentId(student.getId());
        entity.setCourseId(course.getId());
        entity.setMark(dto.getMark());
        entity.setCreatedDate(LocalDate.now());

        studentCourseMarkRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(LocalDate.now());
        return dto;
    }

    public StudentCourseMarkDTO findById(Integer id) {
        List<StudentCourseMarkMapper> objList = studentCourseMarkRepository.getById(id);
        for (StudentCourseMarkMapper studentCourseMarkMapper : objList) {
            StudentCourseMarkDTO studentCourseMarkDTO = new StudentCourseMarkDTO();
            studentCourseMarkDTO.setId(studentCourseMarkMapper.getId());
            studentCourseMarkDTO.setStudentId(studentCourseMarkMapper.getStudentId());
            studentCourseMarkDTO.setCourseId(studentCourseMarkMapper.getCourseId());
            studentCourseMarkDTO.setMark(studentCourseMarkMapper.getMark());
            studentCourseMarkDTO.setCreatedDate(studentCourseMarkMapper.getCreatedDate());
            return studentCourseMarkDTO;
        }
        return null;
    }
    public StudentCourseMarkMapper findByIdLong(Integer id) {
        return studentCourseMarkRepository.findViewById(id)
                .orElse(null);
    }

    public boolean delete(Integer id) {
        studentCourseMarkRepository.deleteById(id);
        return true;
    }

    public List<StudentCourseMarkDTO> findAll() {
        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        Iterable<StudentCourseMarkEntity> entities = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : entities) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<Integer> getMarkByIdAndDate(Integer id, LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        return studentCourseMarkRepository.getMarkByIdAndCreatedDateBetween(id, from, to);
    }

    public List<Integer> getMarkIdByDate(Integer id, LocalDate from, LocalDate to) {
        return studentCourseMarkRepository.getMarkByIdAndCreatedDateBetween(id, LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX));
    }

    public List<Integer> getAllMark(Integer id) {
        return studentCourseMarkRepository.getAllMark(id);
    }

    public List<Integer> getAllMarksorted(Integer studentId, Integer courseId) {
        return studentCourseMarkRepository.getAllMarkByCourseIdAndStudentId(courseId, studentId);
    }

    public StudentCourseMarkDTO getLastStudentCourseMark(Integer id) {
        return toDTO(studentCourseMarkRepository.getLastMark(id));
    }

    public List<Integer> getTop3Mark(Integer studentId) {
        return studentCourseMarkRepository.getTop3Mark(studentId);
    }

    public Integer getFirstMark(Integer studentId) {
        return studentCourseMarkRepository.getFirstMark(studentId);
    }

    public PageImpl<StudentCourseMarkDTO> pagination(int size, int page) {
        PageRequest pageRequest = PageRequest.of(size, page); // order by createDate desc limit ? offset ?
        Page<StudentCourseMarkEntity> pageObj = studentCourseMarkRepository.findAll(pageRequest);// select * from ...

        List<StudentCourseMarkEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentCourseMarkDTO>(dtoList, pageRequest, totalElement);
    }

    public PageImpl<StudentCourseMarkDTO> paginationByStudent(int size, int page, Integer studentId) {
        Sort sort = Sort.by("createdDate").descending();
        PageRequest pageRequest = PageRequest.of(size, page); // order by createDate desc limit ? offset ?
        Page<StudentCourseMarkEntity> pageObj = studentCourseMarkRepository.findByStudentId(studentId, pageRequest);// select * from ...

        List<StudentCourseMarkEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentCourseMarkDTO>(dtoList, pageRequest, totalElement);
    }

    public PageImpl<StudentCourseMarkDTO> paginationByCourse(int size, int page, Integer courseId) {
        Sort sort = Sort.by("createdDate").descending();
        PageRequest pageRequest = PageRequest.of(size, page); // order by createDate desc limit ? offset ?
        Page<StudentCourseMarkEntity> pageObj = studentCourseMarkRepository.findByCourseId(courseId, pageRequest);// select * from ...

        List<StudentCourseMarkEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentCourseMarkDTO>(dtoList, pageRequest, totalElement);
    }

    public Page<StudentCourseMarkDTO> filter(SCMFilterDTO filterDTO, int page, int size) {
        Page<StudentCourseMarkEntity> pageObj = scmRepository.filter(filterDTO, page, size);

        List<StudentCourseMarkEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentCourseMarkDTO>(dtoList, PageRequest.of(page, size), totalElement);
    }

    public StudentCourseMarkDTO toDTO(StudentCourseMarkEntity entity) {
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setMark(entity.getMark());
        dto.setCourseId(entity.getCourseId());
        dto.setStudentId(entity.getStudentId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
