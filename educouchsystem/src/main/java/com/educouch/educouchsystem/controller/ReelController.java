package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.QuizDTO;
import com.educouch.educouchsystem.dto.ReelDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.AttachmentService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.ReelService;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Autowired
    AttachmentService attachmentService;

    //<<lms admin related>>
    @GetMapping("/getAllReels")
    public ResponseEntity<List<Reel>> getAllReels() {
        List<Reel> reels = reelService.getAllReels();
        for(Reel r : reels) {
            r = unmarshallReel(r);
        }
        return new ResponseEntity<>(reels, HttpStatus.OK);
    }

    @GetMapping("/getAllPendingReels")
    public ResponseEntity<List<ReelDTO>> getAllPendingReels() {
        List<Reel> reels = reelService.getAllPendingReels();
        for(Reel r : reels) {
            r = unmarshallReel(r);
        }
        List<ReelDTO> reelDTOS = convertReelsToReelDTOs(reels);
        return new ResponseEntity<>(reelDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllRejectedReels")
    public ResponseEntity<List<ReelDTO>> getAllRejectedReels() {
        List<Reel> reels = reelService.getAllRejectedReels();
        for(Reel r : reels) {
            r = unmarshallReel(r);
        }
        List<ReelDTO> reelDTOS = convertReelsToReelDTOs(reels);
        return new ResponseEntity<>(reelDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllApprovedReels")
    public ResponseEntity<List<ReelDTO>> getAllApprovedReels() {
        List<Reel> reels = reelService.getAllApprovedReels();
        for(Reel r : reels) {
            r = unmarshallReel(r);
        }
        List<ReelDTO> reelDTOS = convertReelsToReelDTOs(reels);
        return new ResponseEntity<>(reelDTOS, HttpStatus.OK);
    }

    @PutMapping("/approveReel/{reelId}")
    public ResponseEntity<Reel> approveReel(@PathVariable(value = "reelId") Long reelId) {
        try {
            Reel reel = reelService.approveReel(reelId);
            reel = unmarshallReel(reel);
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch (ReelNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/rejectReel/{reelId}")
    public ResponseEntity<Reel> rejectReel(@PathVariable(value = "reelId") Long reelId, @RequestBody String rejectionReason) {
        try {
            Reel reel = reelService.rejectReel(reelId, rejectionReason);
            reel = unmarshallReel(reel);
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch (ReelNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //<<instructor interaction related>>
    @GetMapping("/getReel/{reelId}")
    public ResponseEntity<ReelDTO> getReel(@PathVariable(value = "reelId") Long reelId) {
        try {
            Reel reel = reelService.retrieveReelById(reelId);
            reel = unmarshallReel(reel);
            ReelDTO reelDTO = convertReelToReelDTO(reel);
            return new ResponseEntity<ReelDTO>(reelDTO, HttpStatus.OK);
        } catch (ReelNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createReel")
    public ResponseEntity<Reel> createReel(@RequestBody ReelDTO reelDTO) {
        try {
            Reel reel = reelService.createReel(reelDTO);
            reel = unmarshallReel(reel);
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch (CourseNotFoundException | InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/uploadVideoToReel/{reelId}/{file}")
    public ResponseEntity<Reel> uploadVideoToReel(@PathVariable("file") Long file, @PathVariable("reelId") Long reelId) {
        Attachment attachment = null;
        try {
            attachment = attachmentService.getAttachment(file);
            Reel reel = reelService.retrieveReelById(reelId);
            if(reel.getVideo()!=null) {
                Long attachmentIdToDelete = reel.getVideo().getAttachmentId();
                reel.setVideo(null);
                attachmentService.deleteAttachment(attachmentIdToDelete);
            }
            //need to write new service below
            attachmentService.uploadVideoToReel(attachment, reelId);
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch ( FileNotFoundException | ReelNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

        @PostMapping("/uploadThumbnailToReel/{reelId}/{file}")
    public ResponseEntity<Reel> uploadThumbnailToReel(@PathVariable("file") Long file, @PathVariable("reelId") Long reelId) {
        Attachment attachment = null;
        try {
            attachment = attachmentService.getAttachment(file);
            Reel reel = reelService.retrieveReelById(reelId);
            if(reel.getThumbnail()!=null) {
                Long attachmentIdToDelete = reel.getThumbnail().getAttachmentId();
                reel.setThumbnail(null);
                attachmentService.deleteAttachment(attachmentIdToDelete);
            }
            attachmentService.uploadThumbnailToReel(attachment, reelId);
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch ( FileNotFoundException | ReelNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @PutMapping("/updateReel/{reelId}")
    public ResponseEntity<Reel> updateReel(@PathVariable("reelId") Long reelId, @RequestBody ReelDTO incompleteDTO) {
        try {
            Reel updatedReel = reelService.updateReel(reelId, incompleteDTO);
            updatedReel = unmarshallReel(updatedReel);
            return new ResponseEntity<>(updatedReel, HttpStatus.OK);
        }catch ( ReelNotFoundException | CourseNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @PutMapping("/submitReel/{reelId}")
    public ResponseEntity<Reel> submitReel(@PathVariable("reelId") Long reelId, @RequestBody ReelDTO incompleteDTO) {
        try {
            Reel updatedReel = reelService.submitReel(reelId, incompleteDTO);
            updatedReel = unmarshallReel(updatedReel);
            return new ResponseEntity<>(updatedReel, HttpStatus.OK);
        }catch ( ReelNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }


    @PutMapping("/deleteReel/{reelId}")
    public ResponseEntity<Reel> deleteReel(@PathVariable(value = "reelId") Long reelId) {
        try {
            Reel reel = reelService.deleteReelById(reelId);
            reel = unmarshallReel(reel);
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch (ReelNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //all reels as those without "deleted" enum
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

    @GetMapping("/getCoursesUnderInstructor/{instructorId}")
    public ResponseEntity<List<Course>> getCoursesUnderInstructor(@PathVariable(value = "instructorId") Long instructorId) {
        try {
            List<Course> courses = reelService.findCoursesUnderInstructor(instructorId);
            for(Course c : courses) {
                c.setClassRuns(new ArrayList<>());
                c.setAssessments(new ArrayList<>());
                c.setInteractiveBooks(new ArrayList<>());
                c.setAssessments(new ArrayList<>());
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    //<<learner interaction related>>
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

    @PutMapping("/likeReel/{reelId}/{learnerId}")
    public ResponseEntity<Reel> likeReel(@PathVariable(value = "reelId") Long reelId,
                                         @PathVariable(value = "learnerId") Long learnerId) {
        try {
            Reel updatedReel = reelService.likeReel(reelId, learnerId);
            updatedReel = unmarshallReel(updatedReel);
            return new ResponseEntity<>(updatedReel, HttpStatus.OK);
        } catch (ReelNotFoundException | CourseNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findReelsForLearner/{learnerId}")
    public ResponseEntity<List<Reel>> findReelsForLearner(@PathVariable(value = "learnerId") Long learnerId) {
        List<Reel> reels = reelService.findReelsForLearner(learnerId);
        for(Reel r : reels) {
            r = unmarshallReel(r);
        }
        return new ResponseEntity<>(reels, HttpStatus.OK);
    }

    @GetMapping("/findLearnerLikedReels/{learnerId}")
    public ResponseEntity<List<Reel>> findLearnerLikedReels(@PathVariable(value = "learnerId") Long learnerId) {
        try {
            List<Reel> reels = reelService.findLearnerLikedReels(learnerId);
            return new ResponseEntity<>(reels, HttpStatus.OK);
        } catch (LearnerNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //nextReel api to trigger "viewReel"
    public Reel unmarshallReel(Reel reel) {
        reel.getCourseTag().setClassRuns(new ArrayList<>());
        reel.getCourseTag().setAssessments(new ArrayList<>());
        reel.getCourseTag().setInteractiveBooks(new ArrayList<>());
        reel.getCourseTag().setAssessments(new ArrayList<>());
        reel.getReelCreator().setClassRuns(new ArrayList<>());
        reel.getViewers().stream().forEach( learner -> {
            learner.setClassRuns(new ArrayList<>());
            learner.setEnrolmentStatusTrackers(new ArrayList<>());
            learner.setLearnerTransactions(new ArrayList<>());
            learner.setStripeCustomerId(null);
            learner.setPaymentAcc(null);
        });
        reel.getLikers().stream().forEach( learner -> {
            learner.setClassRuns(new ArrayList<>());
            learner.setEnrolmentStatusTrackers(new ArrayList<>());
            learner.setLearnerTransactions(new ArrayList<>());
            learner.setStripeCustomerId(null);
            learner.setPaymentAcc(null);
        });
        return reel;
    }

    public List<ReelDTO> convertReelsToReelDTOs(List<Reel> reels) {
        List<ReelDTO> reelDTOS = new ArrayList<>();
        for(Reel r : reels) {
            r = unmarshallReel(r);
            ReelDTO reelDTO = convertReelToReelDTO(r);
            reelDTOS.add(reelDTO);
        }
        return reelDTOS;
    }

    public ReelDTO convertReelToReelDTO(Reel r) {
        //does not set likers
        //does not set viewers
        ReelDTO reelDTO = new ReelDTO(r.getReelId(),r.getReelTitle(),
                r.getReelCaption(),r.getNumLikes(),r.getNumViews(),
                r.getReelApprovalStatusEnum(), r.getReelCreator().getInstructorId(),
                r.getReelCreator().getName(), r.getCourseTag().getCourseId(), r.getVideo(),
                r.getReelTimeStamp(), r.getRejectionReason());
        reelDTO.setReelCreator(r.getReelCreator());
        reelDTO.setVideo(r.getVideo());
        reelDTO.setThumbnail(r.getThumbnail());
        return reelDTO;
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
