package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.CourseTagDTO;
import com.educouch.educouchsystem.dto.ReelDTO;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.Reel;
import com.educouch.educouchsystem.repository.ReelRepository;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
import com.educouch.educouchsystem.util.exception.ReelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReelServiceImpl implements ReelService{

    @Autowired
    ReelRepository reelRepository;

    @Autowired
    LearnerService learnerService;

    @Autowired
    EducatorService educatorService;

    @Autowired
    CourseService courseService;

    @Override
    public Reel createReel(ReelDTO reelDTO) throws InstructorNotFoundException, CourseNotFoundException {
        Instructor instructor = educatorService.findInstructorById(reelDTO.getInstructorId());
        Course course = courseService.getCourseById(reelDTO.getCourseId());
        Reel reel = new Reel(reelDTO.getReelTitle(), reelDTO.getReelCaption());
        reel.setReelCreator(instructor);
        reel.setCourseTag(course);
        return reelRepository.save(reel);
    }

    @Override
    public Reel retrieveReelById(Long reelId) throws ReelNotFoundException {
        if(reelRepository.findById(reelId).get().equals(null)) {
            throw new ReelNotFoundException();
        } else {
            return reelRepository.findById(reelId).get();
        }
    }

    @Override
    public List<Reel> getAllReelsByInstructorId(Long instructorId) throws InstructorNotFoundException {
        Instructor i = educatorService.findInstructorById(instructorId);
        return reelRepository.findReelsByInstructorId(i.getInstructorId());
    }

    @Override
    public Long deleteReelById(Long Id) throws ReelNotFoundException {
        return null;
    }

    @Override
    public Reel saveReel(Reel reel) {
        return reelRepository.save(reel);
    }

    //below are learner interaction related
    @Override
    public List<Reel> findReelsByCourseTag(Long courseId) {
        return null;
    }

    @Override
    public Reel likeReel(Long reelId) throws ReelNotFoundException {
        return null;
    }

    @Override
    public Reel unlikeReel(Long reelId) throws ReelNotFoundException {
        return null;
    }



}
