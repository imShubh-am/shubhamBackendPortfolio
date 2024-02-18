package com.shubham.service;

import com.shubham.dto.Education;
import com.shubham.repository.EducationRepository;
import com.shubham.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationService {
    @Autowired
    EducationRepository educationRepository;

    public ResponseEntity<Education> createEducation(Education education) {
        Education edu = educationRepository.save(education);
        return new ResponseEntity<>(education, HttpStatus.CREATED);
    }

    public List<Education> getAllEducations() {
        return educationRepository.findAll();
    }

    public Education updateEducation(long id, Education newEducation){
        Education education = educationRepository.findById(id).get();
        education.setInstitution(newEducation.getInstitution());
        education.setDegree(newEducation.getDegree());
        education.setDescription(newEducation.getDescription());
        education.setSession(newEducation.getSession());
        return educationRepository.save(education);
    }
    public String deleteEducation(long id) {
        educationRepository.deleteById(id);
        return "Data with id: " + id + " has been deleted.";
    }
}
