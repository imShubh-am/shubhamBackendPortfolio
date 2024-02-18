package com.shubham.service;

import com.shubham.dto.Experience;
import com.shubham.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {
    @Autowired
    ExperienceRepository experienceRepository;
    public ResponseEntity<Experience> createExperience(Experience experience) {
        Experience exp = experienceRepository.save(experience);
        return new ResponseEntity<>(exp, HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateExperience(long id, Experience experience) {
        Experience exp = experienceRepository.findById(id).get();
        exp.setCompanyName(experience.getCompanyName());
        exp.setDesignation(experience.getDesignation());
        exp.setJoiningDate(experience.getJoiningDate());
        exp.setDescription(experience.getDescription());
        exp.setTechnologiesUsed(experience.getTechnologiesUsed());
        experienceRepository.save(exp);
        return new ResponseEntity<>("Data updated successfully with Id: " + id, HttpStatus.OK);
    }

    public List<Experience> getAllExperience() {
        return experienceRepository.findAll();
    }

    public String deleteExperience(long id) {
        experienceRepository.deleteById(id);
        return "Data with id: " + id + " has been deleted.";
    }

    public Experience getExperience(long id) {
        return experienceRepository.findById(id).get();
    }
}
