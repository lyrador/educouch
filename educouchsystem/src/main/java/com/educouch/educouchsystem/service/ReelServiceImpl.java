package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.CourseTagDTO;
import com.educouch.educouchsystem.dto.ReelDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.ReelPreferenceProfileRepository;
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
    ReelPreferenceProfileRepository reelPreferenceProfileRepository;

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

    //not done
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
    public ReelPreferenceProfile createReelPreferenceProfile(Long learnerId) throws LearnerNotFoundException {
        ReelPreferenceProfile r = new ReelPreferenceProfile();
        return reelPreferenceProfileRepository.save(r);
    }

    //finds 10 reels
    @Override
    public List<Reel> findReelsForLearner(Long learnerId) {

        List<Reel> reelsToReturn = new ArrayList<>();
        //check if this learner has a preference, if dont have then create one
        List<ReelPreferenceProfile> r = reelPreferenceProfileRepository.findReelPreferenceProfileByLearnerId(learnerId);
        if(r.size() < 1) {
            ReelPreferenceProfile preferenceProfile = new ReelPreferenceProfile();
            Learner learner = learnerService.getLearnerById(learnerId);
            preferenceProfile.setLearner(learner);
            reelPreferenceProfileRepository.save(preferenceProfile);
            //fetch random shit
        } else {
            //loop through preferences, fetch reels by course tag
            ReelPreferenceProfile preferenceProfile = r.get(0);
            List<Course> courseTags = preferenceProfile.getCourseTags();
            for(Course c : courseTags) {
                List<Reel> reelsUnderCourseTag = findReelsByCourseTag(c.getCourseId());
                for(Reel reel : reelsUnderCourseTag) {

                }
            }

        }
        return null;
    }

    @Override
    public List<Reel> findReelsByCourseTag(Long courseId) {
        return reelRepository.findReelsByCourseTag(courseId);
    }

    @Override
    public Reel likeReel(Long reelId, Long learnerId) throws ReelNotFoundException {
        //find learner's preference profile and add the course in
        Reel r = reelRepository.getReferenceById(reelId);
        Course courseTag = r.getCourseTag();
        Learner learner = learnerService.getLearnerById(learnerId);

        //update preference
        List<ReelPreferenceProfile> preferenceProfiles = reelPreferenceProfileRepository.findReelPreferenceProfileByLearnerId(learnerId);
        ReelPreferenceProfile preferenceProfile = preferenceProfiles.get(0);
        preferenceProfile.getCourseTags().add(courseTag);
        reelPreferenceProfileRepository.save(preferenceProfile);
        return r;

    }

    @Override
    public Reel unlikeReel(Long reelId, Long learnerId) throws ReelNotFoundException {
        //find learner's preference profile and remove the course
        Reel r = reelRepository.getReferenceById(reelId);
        Course courseTag = r.getCourseTag();
        Learner learner = learnerService.getLearnerById(learnerId);

        //update preference
        List<ReelPreferenceProfile> preferenceProfiles = reelPreferenceProfileRepository.findReelPreferenceProfileByLearnerId(learnerId);
        ReelPreferenceProfile preferenceProfile = preferenceProfiles.get(0);
        for(Course c : preferenceProfile.getCourseTags()) {
            if(c.getCourseId().equals(courseTag.getCourseId())) {
                preferenceProfile.getCourseTags().remove(c);
                break;
            }
        }
        reelPreferenceProfileRepository.save(preferenceProfile);
        return r;
    }

    @Override
    public void addCourseToPreference(Long reelPreferenceProfileId, Long CourseId) throws ReelNotFoundException, CourseNotFoundException {
    }

    @Override
    public void removeCourseFromPreferenceLong(Long reelPreferenceProfileId, Long CourseId) throws ReelNotFoundException, CourseNotFoundException {
    }

    @Override
    public Reel viewReel(Long reelId, long learnerId) throws ReelNotFoundException {
        Reel r = reelRepository.getReferenceById(reelId);
        Learner l = learnerService.getLearnerById(learnerId);
        r.getViewers().add(l);
        return reelRepository.save(r);
    }
}
