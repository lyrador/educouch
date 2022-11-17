package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.QuizDTO;
import com.educouch.educouchsystem.dto.ReelDTO;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.model.Reel;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.ReelService;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.ReelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/reel")

public class ReelController {

    @Autowired
    ReelService reelService;

    @Autowired
    LearnerService learnerService;

    @PostMapping("/createReel")
    public ResponseEntity<Reel> createQuiz(@RequestBody ReelDTO reelDTO) {
        try {
            Reel reel = reelService.createReel(reelDTO);
            reel = unmarshallReel(reel);
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch (CourseNotFoundException | InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllReelsByInstructorId/{instructorId}")
    public ResponseEntity<List<Reel>> getAllReelsByInstructorId(@PathVariable(value = "instructorId") Long instructorId) {
        try {
            List<Reel> reels = reelService.getAllReelsByInstructorId(instructorId);
            for(Reel r : reels) {
                r = unmarshallReel(r);
            }
            return new ResponseEntity<>(reels, HttpStatus.OK);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //from here onwards is learner interaction related
    @PutMapping("/viewReel/{reelId}/{learnerId}")
    public ResponseEntity<Reel> viewReel(@PathVariable(value = "reelId") Long reelId,
                                         @PathVariable(value = "learnerId") Long learnerId) {
        try {
            Reel updatedReel = reelService.viewReel(reelId, learnerId);
            updatedReel = unmarshallReel(updatedReel);
            return new ResponseEntity<>(updatedReel, HttpStatus.OK);
        } catch (ReelNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //might need to unmarshall for learner also
    public Reel unmarshallReel(Reel reel) {
        reel.getCourseTag().setClassRuns(new ArrayList<>());
        reel.getCourseTag().setAssessments(new ArrayList<>());
        reel.getCourseTag().setInteractiveBooks(new ArrayList<>());
        reel.getCourseTag().setAssessments(new ArrayList<>());
        reel.getReelCreator().setClassRuns(new ArrayList<>());
        reel.getViewers().stream().forEach( r -> {
            r.setClassRuns(new ArrayList<>());
            r.setEnrolmentStatusTrackers(new ArrayList<>());
            r.setLearnerTransactions(new ArrayList<>());
        });
        reel.getLikers().stream().forEach( r -> r.setClassRuns(new ArrayList<>()));
        return reel;
    }

    public List<ReelDTO> convertReelsToReelDTOs(List<Reel> reels) {
        List<ReelDTO> reelDTOS = new ArrayList<>();
        for(Reel r : reels) {
            r = unmarshallReel(r);
            //does not set likers
            //does not set viewers
            ReelDTO reelDTO = new ReelDTO(r.getReelId(),r.getReelTitle(),
                    r.getReelCaption(),r.getNumLikes(),
                    r.getReelApprovalStatusEnum(), r.getReelCreator().getInstructorId(),
                    r.getCourseTag().getCourseId(), r.getVideo());
            reelDTOS.add(reelDTO);
        }
        return reelDTOS;
    }

    //only used for learner side
    public List<ReelDTO> checkLiked(Long learnerId, List<ReelDTO> reels) {
        Learner learner = learnerService.getLearnerById(learnerId);
        for(ReelDTO r : reels) {
            if(r.getLikers().contains(learner)){
                r.setLiked(true);
            }
        }
        return reels;
    }
}
