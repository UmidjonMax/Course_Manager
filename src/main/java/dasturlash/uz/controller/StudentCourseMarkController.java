package dasturlash.uz.controller;

import dasturlash.uz.dto.CourseDTO;
import dasturlash.uz.dto.CourseFilterDTO;
import dasturlash.uz.dto.SCMFilterDTO;
import dasturlash.uz.dto.StudentCourseMarkDTO;
import dasturlash.uz.mapper.StudentCourseMarkMapper;
import dasturlash.uz.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mark")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService studentCourseMarkService;


    //create mark
    @PostMapping("")
    public ResponseEntity<StudentCourseMarkDTO> create(@RequestBody StudentCourseMarkDTO dto) {
        StudentCourseMarkDTO studentCourseMarkDTO = studentCourseMarkService.create(dto);
        return new ResponseEntity<>(studentCourseMarkDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseMarkDTO> getById(@PathVariable int id) {
        StudentCourseMarkDTO studentCourseMarkDTO = studentCourseMarkService.findById(id);
        return new ResponseEntity<>(studentCourseMarkDTO, HttpStatus.OK);
    }

    @GetMapping("/detailed/{id}")
    public ResponseEntity<StudentCourseMarkMapper> getDetailedById(@PathVariable int id) {
        return ResponseEntity.ok(studentCourseMarkService.findByIdLong(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<StudentCourseMarkDTO>> filter(
            @RequestBody SCMFilterDTO filterDTO,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.filter(filterDTO, page - 1, size));
    }
}
