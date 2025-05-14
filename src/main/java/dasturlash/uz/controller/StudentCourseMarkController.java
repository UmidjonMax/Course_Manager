package dasturlash.uz.controller;

import dasturlash.uz.dto.StudentCourseMarkDTO;
import dasturlash.uz.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<StudentCourseMarkDTO> getDetailedById(@PathVariable int id) {
        StudentCourseMarkDTO studentCourseMarkDTO = studentCourseMarkService.findByIdLong(id);
        return new ResponseEntity<>(studentCourseMarkDTO, HttpStatus.OK);
    }

        
}
