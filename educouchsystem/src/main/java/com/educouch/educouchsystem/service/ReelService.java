package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.CourseTagDTO;
import com.educouch.educouchsystem.dto.ReelDTO;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.Reel;
import com.educouch.educouchsystem.model.ReelPreferenceProfile;
import com.educouch.educouchsystem.repository.ReelPreferenceProfileRepository;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
import com.educouch.educouchsystem.util.exception.ReelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReelService {

    public Reel createReel(ReelDTO reelDTO) throws InstructorNotFoundException, CourseNotFoundException;
    public Reel retrieveReelById(Long reelId) throws ReelNotFoundException;
    public List<Reel> getAllReelsByInstructorId(Long instructorId) throws InstructorNotFoundException;
    public Long deleteReelById(Long Id) throws ReelNotFoundException;
    public Reel saveReel(Reel reel);


    //learner interactions
    public ReelPreferenceProfile createReelPreferenceProfile(Long learnerId) throws LearnerNotFoundException;
    public List<Reel> findReelsForLearner(Long learnerId);
    public List<Reel> findReelsByCourseTag(Long courseId);
    public Reel likeReel(Long reelId, Long learnerId) throws ReelNotFoundException;
    public Reel unlikeReel(Long reelId, Long learnerId) throws ReelNotFoundException;
    public void addCourseToPreference(Long reelPreferenceProfileId, Long CourseId)throws ReelNotFoundException, CourseNotFoundException;
    public void removeCourseFromPreferenceLong(Long reelPreferenceProfileId, Long CourseId) throws ReelNotFoundException, CourseNotFoundException;
    public Reel viewReel(Long reelId, long LearnerId)throws ReelNotFoundException;
}
