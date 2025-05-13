package dasturlash.uz.controller;

import dasturlash.uz.dto.StudentDTO;
import dasturlash.uz.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // Get All Students
    @GetMapping("")
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> dtoList = studentService.findAll();
        return ResponseEntity.ok(dtoList);
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable("id") Integer id) {
        StudentDTO dto = studentService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // Create
    @PostMapping("")
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO dto) {
        StudentDTO response = studentService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable("id") Integer id, @RequestBody StudentDTO dto) {
        StudentDTO updated = studentService.update2(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        boolean result = studentService.delete(id);
        return ResponseEntity.ok(result);
    }

    // Get by Name
    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<StudentDTO>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.findByName(name));
    }

    // Get by Surname
    @GetMapping("/by-surname/{surname}")
    public ResponseEntity<List<StudentDTO>> getBySurname(@RequestParam String surname) {
        return ResponseEntity.ok(studentService.findBySurname(surname));
    }

    // Get by Level
    @GetMapping("/by-level")
    public ResponseEntity<List<StudentDTO>> getByLevel(@RequestParam Integer level) {
        return ResponseEntity.ok(studentService.findByLevel(level));
    }

    // Get by Age
    @GetMapping("/by-age")
    public ResponseEntity<List<StudentDTO>> getByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(studentService.findByAge(age));
    }

    // Get by Gender
    @GetMapping("/by-gender")
    public ResponseEntity<List<StudentDTO>> getByGender(@RequestParam String gender) {
        return ResponseEntity.ok(studentService.findByGender(gender));
    }

    // Get by Created Date
    @GetMapping("/by-date")
    public ResponseEntity<List<StudentDTO>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(studentService.findByDate(date));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<StudentDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(studentService.pagination(page-1, size));
    }

    @GetMapping("/paginationLevel")
    public ResponseEntity<Page<StudentDTO>> paginationLevel(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                            @RequestParam(value = "level", defaultValue = "1") Integer level) {
        return ResponseEntity.ok(studentService.paginationByLevel(page-1, size, level));
    }

}
