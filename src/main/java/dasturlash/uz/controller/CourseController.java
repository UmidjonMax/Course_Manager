package dasturlash.uz.controller;

import dasturlash.uz.dto.CourseDTO;
import dasturlash.uz.dto.CourseFilterDTO;
import dasturlash.uz.dto.StudentDTO;
import dasturlash.uz.dto.StudentFilterDTO;
import dasturlash.uz.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Create Course
    @PostMapping("")
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto) {
        CourseDTO courseDto = courseService.create(dto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<List<CourseDTO>> getAll() {
        List<CourseDTO> dtoList = courseService.getAll();
        return ResponseEntity.ok(dtoList);
    }

    // Get Course by ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable Integer id) {
        CourseDTO courseDto = courseService.getByID(id);
        return ResponseEntity.ok(courseDto);
    }


    // Update Course
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable("id") Integer id, @RequestBody CourseDTO dto) {
        CourseDTO updated = courseService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete Course
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        boolean result = courseService.delete(id);
        return ResponseEntity.ok(result);
    }

    // Get Courses by Name
    @GetMapping("/by-name")
    public ResponseEntity<List<CourseDTO>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(courseService.getByName(name));
    }

    // Get Courses by Price
    @GetMapping("/by-price")
    public ResponseEntity<List<CourseDTO>> getByPrice(@RequestParam Integer price) {
        return ResponseEntity.ok(courseService.getByPrice(price));
    }

    // Get Courses by Duration
    @GetMapping("/by-duration")
    public ResponseEntity<List<CourseDTO>> getByDuration(@RequestParam Integer duration) {
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }

    // Get Courses by Price Range
    @GetMapping("/by-price-range")
    public ResponseEntity<List<CourseDTO>> getByPriceRange(@RequestParam Integer from, @RequestParam Integer to) {
        return ResponseEntity.ok(courseService.getByPriceBetween(from, to));
    }

    // Get Courses by Date Range
    @GetMapping("/by-date-range")
    public ResponseEntity<List<CourseDTO>> getByDateRange(@RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok(courseService.getByCreatedDateBetween(LocalDate.parse(from), LocalDate.parse(to)));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<CourseDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(courseService.pagination(page-1, size));
    }

    @GetMapping("/paginationSorted")
    public ResponseEntity<Page<CourseDTO>> paginationSorted(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(courseService.pagination(page-1, size));
    }

    @GetMapping("/pagination/price")
    public ResponseEntity<Page<CourseDTO>> paginationByPrice(@RequestParam(value = "price") Integer price,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(courseService.paginationByPrice(page-1, size, price));
    }

    @GetMapping("/pagination/priceBetween")
    public ResponseEntity<Page<CourseDTO>> paginationByPriceBetween(@RequestParam(value = "from") Integer from,
                                                                    @RequestParam(value = "to") Integer to,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(courseService.paginationByPriceBetween(page-1, size, from, to));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<CourseDTO>> filter(
            @RequestBody CourseFilterDTO filterDTO,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size) {
        return ResponseEntity.ok(courseService.filter(filterDTO, page - 1, size));
    }
}

