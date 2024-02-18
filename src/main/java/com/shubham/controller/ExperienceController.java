package com.shubham.controller;

import com.shubham.dto.Experience;
import com.shubham.repository.ExperienceRepository;
import com.shubham.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/experience")
//@CrossOrigin("http://localhost:3000/")
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @PostMapping()
    public ResponseEntity<Experience> create(@RequestBody Experience experience){
        return experienceService.createExperience(experience);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody Experience experience){
        return experienceService.updateExperience(id, experience);
    }

    @GetMapping("/{id}")
    public Experience getExperience(@PathVariable long id){
        return experienceService.getExperience(id);
    }
    @GetMapping("/getAll")
    public List<Experience> getAllExperience(){
        return experienceService.getAllExperience();
    }

    @DeleteMapping("/{id}")
    public String deleteExperience(@PathVariable long id){
        return experienceService.deleteExperience(id);
    }
}
