package dasturlash.uz.service;

import dasturlash.uz.dto.StudentDTO;
import dasturlash.uz.dto.StudentFilterDTO;
import dasturlash.uz.entity.StudentEntity;
import dasturlash.uz.repository.StudentCustomRepository;
import dasturlash.uz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentCustomRepository studentCustomRepository;

    public StudentDTO create(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDTO.getName());
        studentEntity.setSurname(studentDTO.getSurname());
        studentEntity.setLevel(studentDTO.getLevel());
        studentEntity.setAge(studentDTO.getAge());
        studentEntity.setGender(studentDTO.getGender());
        studentEntity.setCreatedDate(LocalDateTime.now());
        studentRepository.save(studentEntity);
        studentDTO.setId(studentEntity.getId());
        return studentDTO;
    }

    public List<StudentDTO> findAll() {
        Iterable<StudentEntity> studentEntities = studentRepository.findAll();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        studentEntities.forEach(studentEntity -> studentDTOList.add(toDTO(studentEntity)));
        return studentDTOList;
    }

    public StudentDTO findById(int id) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        return studentEntity.map(this::toDTO).orElse(null);
    }

//    public StudentDTO update(Integer id, StudentDTO studentDTO) {
//        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
//        if (studentEntity.isEmpty()){
//            return null;
//        }
//        StudentEntity studentEntity1 = studentEntity.get();
//        studentEntity1.setName(studentDTO.getName());
//        studentEntity1.setSurname(studentDTO.getSurname());
//        studentEntity1.setLevel(studentDTO.getLevel());
//        studentEntity1.setAge(studentDTO.getAge());
//        studentEntity1.setGender(studentDTO.getGender());
//        studentRepository.save(studentEntity1);
//        return studentDTO;
//    }

    public boolean delete(int id) {
        studentRepository.deleteById(id);
        return true;
    }

    public List<StudentDTO> findByName(String name) {
        List<StudentEntity> list = studentRepository.getByName("%" + name + "%");
        List<StudentDTO> studentDTOList = new ArrayList<>();
        list.forEach(studentEntity -> studentDTOList.add(toDTO(studentEntity)));
        return studentDTOList;
    }

    public List<StudentDTO> findBySurname(String surname) {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        List<StudentEntity> list = studentRepository.getBySurname("%" + surname + "%");
        list.forEach(studentEntity -> studentDTOList.add(toDTO(studentEntity)));
        return studentDTOList;
    }

    public List<StudentDTO> findByAge(int age) {
        List<StudentEntity> list = studentRepository.getByAge(age);
        List<StudentDTO> studentDTOList = new ArrayList<>();
        list.forEach(studentEntity -> studentDTOList.add(toDTO(studentEntity)));
        return studentDTOList;
    }

    public List<StudentDTO> findByGender(String gender) {
        List<StudentEntity> list = studentRepository.getByGender(gender);
        List<StudentDTO> studentDTOList = new ArrayList<>();
        list.forEach(studentEntity -> studentDTOList.add(toDTO(studentEntity)));
        return studentDTOList;
    }

    public List<StudentDTO> findByLevel(int level) {
        List<StudentEntity> list = studentRepository.getByLevel(level);
        List<StudentDTO> studentDTOList = new ArrayList<>();
        list.forEach(studentEntity -> studentDTOList.add(toDTO(studentEntity)));
        return studentDTOList;
    }

    public List<StudentDTO> findByDate(LocalDate date) {
        LocalDateTime dateFrom = LocalDateTime.of(date, LocalTime.MIN); // 06.05.2025  00:00:00
        LocalDateTime dateTo = LocalDateTime.of(date, LocalTime.MAX); // 06.05.2025  00:00:00
        List<StudentEntity> entityList = studentRepository.getByCreatedDateBetween(dateFrom, dateTo);
        return null;
    }

    public List<StudentDTO> findByDateBetween(LocalDateTime date1, LocalDateTime date2) {
        List<StudentEntity> list = studentRepository.getByCreatedDateBetween(date1, date2);
        List<StudentDTO> studentDTOList = new ArrayList<>();
        list.forEach(studentEntity -> studentDTOList.add(toDTO(studentEntity)));
        return studentDTOList;
    }

    public StudentDTO toDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setLevel(entity.getLevel());
        dto.setAge(entity.getAge());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<StudentDTO> pagination(int size, int page) {
        Sort sort = Sort.by("createdDate").descending(); // order by createDate desc
        PageRequest pageRequest = PageRequest.of(size, page, sort); // order by createDate desc limit ? offset ?
        Page<StudentEntity> pageObj = studentRepository.findAll(pageRequest);// select * from ...

        List<StudentEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentDTO>(dtoList, pageRequest, totalElement);
    }

    public PageImpl<StudentDTO> paginationByLevel(int size, int page, Integer level) {
        Sort sort = Sort.by("id").descending(); // order by createDate desc
        PageRequest pageRequest = PageRequest.of(size, page, sort); // order by createDate desc limit ? offset ?
        Page<StudentEntity> pageObj = studentRepository.findByLevel(level, pageRequest);// select * from ...

        List<StudentEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentDTO>(dtoList, pageRequest, totalElement);
    }

    public PageImpl<StudentDTO> paginationByGender(int size, int page, String gender) {
        Sort sort = Sort.by("createdDate").descending(); // order by createDate desc
        PageRequest pageRequest = PageRequest.of(size, page, sort); // order by createDate desc limit ? offset ?
        Page<StudentEntity> pageObj = studentRepository.findByGender(gender, pageRequest);// select * from ...

        List<StudentEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentDTO>(dtoList, pageRequest, totalElement);
    }

    public Page<StudentDTO> filter(StudentFilterDTO filterDTO, int page, int size) {
        Page<StudentEntity> pageObj = studentCustomRepository.filter(filterDTO, page, size);

        List<StudentEntity> entityList = pageObj.getContent();
        Long totalElement = pageObj.getTotalElements();

        List<StudentDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<StudentDTO>(dtoList, PageRequest.of(page, size), totalElement);
    }


    public StudentDTO update2(Integer id, StudentDTO dto) {
        int effectedRows = studentRepository.updateStudent(id, dto.getName(), dto.getSurname(), dto.getAge(), dto.getLevel(), dto.getGender());
        return dto;
    }

}