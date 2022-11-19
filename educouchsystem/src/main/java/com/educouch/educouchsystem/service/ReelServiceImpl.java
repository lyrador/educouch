package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.ReelDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.ReelPreferenceProfileRepository;
import com.educouch.educouchsystem.repository.ReelRepository;
import com.educouch.educouchsystem.util.enumeration.ReelApprovalStatusEnum;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
import com.educouch.educouchsystem.util.exception.ReelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    //<<below are instructor interaction related>>
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
        List<Reel>  reels = reelRepository.findReelsByInstructorId(i.getInstructorId());
        return reels.stream().filter(reel -> (!reel.getReelApprovalStatusEnum().equals(ReelApprovalStatusEnum.DELETED))).collect(Collectors.toList());
    }

    //does not actually delete, only sets enum as deleted
    @Override
    public Reel deleteReelById(Long reelId) throws ReelNotFoundException {
        Reel reelToDelete = reelRepository.getReferenceById(reelId);
        reelToDelete.setReelApprovalStatusEnum(ReelApprovalStatusEnum.DELETED);
        reelToDelete.setRejectionReason("");
        return reelRepository.save(reelToDelete);    }

    @Override
    public Reel saveReel(Reel reel) {
        return reelRepository.save(reel);
    }



    //<<below are lms admin interaction related>>
    @Override
    public Reel approveReel(Long reelId) throws ReelNotFoundException {
        Reel reelToApprove = reelRepository.getReferenceById(reelId);
        reelToApprove.setReelApprovalStatusEnum(ReelApprovalStatusEnum.LIVE);
        reelToApprove.setRejectionReason("");
        return reelRepository.save(reelToApprove);
    }

    @Override
    public Reel rejectReel(Long reelId, String rejectionReason) throws ReelNotFoundException {
        Reel reelToReject = reelRepository.getReferenceById(reelId);
        reelToReject.setReelApprovalStatusEnum(ReelApprovalStatusEnum.REJECTED);
        reelToReject.setRejectionReason(rejectionReason);
        return reelRepository.save(reelToReject);
    }

    @Override
    public List<Reel> getAllReels() {
        return reelRepository.findAll();
    }

    @Override
    public List<Reel> getAllPendingReels() {
        return reelRepository.findAllReelsChronologically(ReelApprovalStatusEnum.PENDING);
    }



    //<<below are learner interaction related>>
    @Override
    public ReelPreferenceProfile createReelPreferenceProfile(Long learnerId) throws LearnerNotFoundException {
        ReelPreferenceProfile r = new ReelPreferenceProfile();
        return reelPreferenceProfileRepository.save(r);
    }

    //finds 5 reels only
    @Override
    public List<Reel> findReelsForLearner(Long learnerId) {

        List<Reel> reelsToReturn = new ArrayList<>();
        Learner learner = learnerService.getLearnerById(learnerId);

        //check if this learner has a preference, if dont have then create one
        ReelPreferenceProfile preferenceProfile = reelPreferenceProfileRepository.findReelPreferenceProfileByLearnerId(learnerId);
        if(preferenceProfile==null) {
            ReelPreferenceProfile newPreferenceProfile = new ReelPreferenceProfile();
            newPreferenceProfile.setLearner(learner);
            reelPreferenceProfileRepository.save(newPreferenceProfile);
            //fetch random shit cos no preference
            reelsToReturn = findRecentReels(learnerId);
        } else {
            //loop through preferences, fetch reels by course tag
            Set<Course> courseTags = preferenceProfile.getCourseTags();
            for(Course c : courseTags) {
                List<Reel> reelsUnderCourseTag = findReelsByCourseTag(c.getCourseId());
                for(Reel reel : reelsUnderCourseTag) {
                    if(!reel.getViewers().contains(learner)) {
                        //learner hasnt seen this reel before
                        reelsToReturn.add(reel);
                        if (reelsToReturn.size()>=5) {
                            break;
                        }
                    }
                }
                break;
            }
            if(reelsToReturn.size()<5) {
                //not enough videos
                List<Reel> recentReels = findRecentReels(learnerId);
                for(Reel recentReel : recentReels) {
                    reelsToReturn.add(recentReel);
                }
            }
        }
        return reelsToReturn;
    }

    @Override
    public List<Reel> findRecentReels(Long learnerId) {
        Learner learner = learnerService.getLearnerById(learnerId);
        List<Reel> allReels = reelRepository.findAllReelsChronologically(ReelApprovalStatusEnum.LIVE);
        List<Reel> recentReels = new ArrayList<>();
        int i = 0;
        while(i<allReels.size()) {
            if(recentReels.size()>5) {
                break;
            }
            Reel r = allReels.get(i);
            if(!r.getViewers().contains(learner)) {
                recentReels.add(r);
            }
            i++;
        }
        return recentReels;
    }

    @Override
    public List<Reel> findReelsByCourseTag(Long courseId) {
        return reelRepository.findReelsByCourseTag(courseId, ReelApprovalStatusEnum.LIVE);
    }

    @Override
    public Reel likeReel(Long reelId, Long learnerId) throws ReelNotFoundException, CourseNotFoundException {

        Reel r = reelRepository.getReferenceById(reelId);
        Course courseTag = courseService.getCourseById(r.getCourseTag().getCourseId());
        Learner learner = learnerService.getLearnerById(learnerId);

        //update preference
        ReelPreferenceProfile preferenceProfile = reelPreferenceProfileRepository.findReelPreferenceProfileByLearnerId(learnerId);
        Set<Course> courseTags = preferenceProfile.getCourseTags();
        courseTags.add(courseTag);
        preferenceProfile.setCourseTags(courseTags);
        reelPreferenceProfileRepository.save(preferenceProfile);

        //update likes on reel
        if(!r.getLikers().contains(learner)) {
            r.setNumLikes(r.getNumLikes() + 1);
            r.getLikers().add(learner);
            reelRepository.save(r);
        }
        return r;

    }

    @Override
    public Reel unlikeReel(Long reelId, Long learnerId) throws ReelNotFoundException {
        //find learner's preference profile and remove the course
        Reel r = reelRepository.getReferenceById(reelId);
        Course courseTag = r.getCourseTag();
        Learner learner = learnerService.getLearnerById(learnerId);

        //update preference
        ReelPreferenceProfile preferenceProfile = reelPreferenceProfileRepository.findReelPreferenceProfileByLearnerId(learnerId);
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

        if(!r.getViewers().contains(l)) {
            r.setNumViews(r.getNumViews() +1);
            r.getViewers().add(l);
        }
        return reelRepository.save(r);
    }
}
