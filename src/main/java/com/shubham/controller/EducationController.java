package com.shubham.controller;

import com.shubham.dto.Education;
import com.shubham.repository.EducationRepository;
import com.shubham.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
@CrossOrigin("http://localhost:3000/")
public class EducationController {
    @Autowired
    EducationService educationService;
    @Autowired
    private EducationRepository educationRepository;

    @PostMapping()
    public ResponseEntity<Education> create(@RequestBody Education education){
        return educationService.createEducation(education);
    }
    @GetMapping("/getAll")
    public List<Education> getAllEducations(){
        return educationService.getAllEducations();
    }
    @PostMapping("/{id}")
    public Education update(@PathVariable long id, @RequestBody Education education){
        return educationService.updateEducation(id, education);
    }
    @DeleteMapping("/{id}")
    public String deleteEducation(@PathVariable long id){
        return educationService.deleteEducation(id);
    }

}
