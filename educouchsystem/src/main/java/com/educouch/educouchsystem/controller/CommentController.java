package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.CommentDTO;
import com.educouch.educouchsystem.dto.ForumDiscussionDTO;
import com.educouch.educouchsystem.model.Comment;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.model.ForumDiscussion;
import com.educouch.educouchsystem.service.CommentService;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.ForumDiscussionService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ForumDiscussionService forumDiscussionService;

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private EducatorService educatorService;

//    @PostMapping("/forumDiscussions/{forumDiscussionId}/comments")
//    public ResponseEntity<Comment> addComment(@PathVariable(value="forumDiscussionId") Long forumDiscussionId, @RequestBody Comment commentRequest) {
//        try {
//            ForumDiscussion forumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
//            forumDiscussion.getComments().add(commentRequest);
//            commentRequest.setTimestamp(LocalDateTime.now());
//            Comment comment = commentService.saveComment(commentRequest);
//            return new ResponseEntity<>(comment, HttpStatus.OK);
//        } catch (NoSuchElementException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/forumDiscussions/{forumDiscussionId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable(value="forumDiscussionId") Long forumDiscussionId, @RequestBody CommentDTO commentRequest) {
        try {
            ForumDiscussion forumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
            Comment newComment = new Comment();

            newComment.setCommentTitle(commentRequest.getCommentTitle());
            newComment.setContent(commentRequest.getContent());
            newComment.setTimestamp(LocalDateTime.now());

            //if created by learner, find the learner via learnerId and set it as the creator of forum, else it will be educator
            if (commentRequest.getCreatedByUserType().equals("LEARNER")) {
                newComment.setCreatedByLearner(learnerService.getLearnerById(commentRequest.getCreatedByUserId()));
            } else if (commentRequest.getCreatedByUserType().equals("INSTRUCTOR")) {
                newComment.setCreatedByInstructor(educatorService.findInstructorById(commentRequest.getCreatedByUserId()));
            } else if (commentRequest.getCreatedByUserType().equals("ORG_ADMIN")) {
                newComment.setCreatedByOrganisationAdmin(educatorService.findOrganisationAdminById(commentRequest.getCreatedByUserId()));
            }

            forumDiscussion.getComments().add(newComment);

            Comment comment = commentService.saveComment(newComment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reply/{forumDiscussionId}")
    public ResponseEntity<Comment> replyComment(@PathVariable(value="forumDiscussionId") Long forumDiscussionId, @RequestBody CommentDTO commentRequest) {
        try {
            ForumDiscussion forumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
            Comment parentComment = commentService.retrieveCommentById(commentRequest.getCommentId());
            Comment newComment = new Comment();

            newComment.setCommentTitle(commentRequest.getCommentTitle());
            newComment.setContent(commentRequest.getContent());
            newComment.setTimestamp(LocalDateTime.now());

            //if created by learner, find the learner via learnerId and set it as the creator of forum, else it will be educator
            if (commentRequest.getCreatedByUserType().equals("LEARNER")) {
                newComment.setCreatedByLearner(learnerService.getLearnerById(commentRequest.getCreatedByUserId()));
            } else if (commentRequest.getCreatedByUserType().equals("INSTRUCTOR")) {
                newComment.setCreatedByInstructor(educatorService.findInstructorById(commentRequest.getCreatedByUserId()));
            } else if (commentRequest.getCreatedByUserType().equals("ORG_ADMIN")) {
                newComment.setCreatedByOrganisationAdmin(educatorService.findOrganisationAdminById(commentRequest.getCreatedByUserId()));
            }

            parentComment.getComments().add(newComment);
            commentService.saveComment(parentComment);

            forumDiscussion.getComments().add(newComment);

            newComment.setParentComment(parentComment);
            Comment comment = commentService.saveComment(newComment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/forumDiscussions/{forumDiscussionId}/comments")
//    public ResponseEntity<List<Comment>> getAllCommentsByForumDiscussionId(@PathVariable(value="forumDiscussionId") Long forumDiscussionId) {
//        try {
//            ForumDiscussion forumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
//            List<Comment> comments = new ArrayList<Comment>();
//            comments.addAll(forumDiscussion.getComments());
//            return new ResponseEntity<>(comments, HttpStatus.OK);
//
//        } catch (NoSuchElementException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }

    @GetMapping("/forumDiscussions/{forumDiscussionId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByForumDiscussionId(@PathVariable(value="forumDiscussionId") Long forumDiscussionId) {
        try {
            ForumDiscussion forumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
            List<Comment> comments = new ArrayList<Comment>();
            comments.addAll(forumDiscussion.getComments());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<CommentDTO> commentDTOs = new ArrayList<>();
            for (Comment comment : comments) {
                if (comment.getParentComment() == null) {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setCommentId(comment.getCommentId());
                    commentDTO.setCommentTitle(comment.getCommentTitle());
                    commentDTO.setContent(comment.getContent());
                    commentDTO.setCreatedDateTime(comment.getTimestamp().format(formatter));
                    if (comment.getCreatedByLearner() != null) {
                        commentDTO.setCreatedByUserId(comment.getCreatedByLearner().getLearnerId());
                        commentDTO.setCreatedByUserName(comment.getCreatedByLearner().getName());
                        commentDTO.setCreatedByUserProfilePictureURL(comment.getCreatedByLearner().getProfilePictureURL());
                        commentDTO.setCreatedByUserType("LEARNER");
                    } else if (comment.getCreatedByInstructor() != null) {
                        commentDTO.setCreatedByUserId(comment.getCreatedByInstructor().getInstructorId());
                        commentDTO.setCreatedByUserName(comment.getCreatedByInstructor().getName());
                        commentDTO.setCreatedByUserProfilePictureURL(commentDTO.getCreatedByUserProfilePictureURL());
                        commentDTO.setCreatedByUserType("INSTRUCTOR");
                    } else if (comment.getCreatedByOrganisationAdmin() != null) {
                        commentDTO.setCreatedByUserId(comment.getCreatedByOrganisationAdmin().getOrganisationAdminId());
                        commentDTO.setCreatedByUserName(comment.getCreatedByOrganisationAdmin().getName());
                        commentDTO.setCreatedByUserProfilePictureURL(commentDTO.getCreatedByUserProfilePictureURL());
                        commentDTO.setCreatedByUserType("ORG_ADMIN");
                    }
                    if (comment.getComments().size() > 0) {
                        List<Comment> childComments = comment.getComments();
                        for (Comment childComment: childComments) {
                            //                        childComment.setComments(null);
//                            childComment.setParentComment(null);
                        }
                        commentDTO.getChildComments().addAll(childComments);
                        commentDTOs.add(commentDTO);
                    } else if (comment.getParentComment() == null) {
                        commentDTOs.add(commentDTO);
                    }
                }
            }
            for (CommentDTO commentDTO : commentDTOs) {
                List<CommentDTO> childCommentDTOs = new ArrayList<>();
                for (Comment childComment: commentDTO.getChildComments()) {
                    childComment.setComments(null);
                    childComment.setParentComment(null);
                    CommentDTO childCommentDTO = new CommentDTO();
                    childCommentDTO.setCommentId(childComment.getCommentId());
                    childCommentDTO.setCommentTitle(childComment.getCommentTitle());
                    childCommentDTO.setContent(childComment.getContent());
                    childCommentDTO.setCreatedDateTime(childComment.getTimestamp().format(formatter));
                    if (childComment.getCreatedByLearner() != null) {
                        childCommentDTO.setCreatedByUserId(childComment.getCreatedByLearner().getLearnerId());
                        childCommentDTO.setCreatedByUserName(childComment.getCreatedByLearner().getName());
                        childCommentDTO.setCreatedByUserProfilePictureURL(childComment.getCreatedByLearner().getProfilePictureURL());
                        childCommentDTO.setCreatedByUserType("LEARNER");
                    } else if (childComment.getCreatedByInstructor() != null) {
                        childCommentDTO.setCreatedByUserId(childComment.getCreatedByInstructor().getInstructorId());
                        childCommentDTO.setCreatedByUserName(childComment.getCreatedByInstructor().getName());
                        childCommentDTO.setCreatedByUserProfilePictureURL(childCommentDTO.getCreatedByUserProfilePictureURL());
                        childCommentDTO.setCreatedByUserType("INSTRUCTOR");
                    } else if (childComment.getCreatedByOrganisationAdmin() != null) {
                        childCommentDTO.setCreatedByUserId(childComment.getCreatedByOrganisationAdmin().getOrganisationAdminId());
                        childCommentDTO.setCreatedByUserName(childComment.getCreatedByOrganisationAdmin().getName());
                        childCommentDTO.setCreatedByUserProfilePictureURL(childCommentDTO.getCreatedByUserProfilePictureURL());
                        childCommentDTO.setCreatedByUserType("ORG_ADMIN");
                    }
                    childCommentDTOs.add(childCommentDTO);
                }
                commentDTO.setChildCommentDTOs(childCommentDTOs);
                commentDTO.setChildComments(null);
            }

            return new ResponseEntity<>(commentDTOs, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/forumDiscussions/{forumDiscussionId}/comments")
//    public ResponseEntity<List<Comment>> getAllCommentsByForumDiscussionId(@PathVariable(value="forumDiscussionId") Long forumDiscussionId) {
//        try {
//            ForumDiscussion forumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
//            List<Comment> comments = new ArrayList<Comment>();
//            comments.addAll(forumDiscussion.getComments());
//            return new ResponseEntity<>(comments, HttpStatus.OK);
//
//        } catch (NoSuchElementException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> retrieveCommentById(@PathVariable(value = "commentId") Long commentId) {
        try {
            Comment existingComment = commentService.retrieveCommentById(commentId);
            return new ResponseEntity<Comment>(existingComment, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentRequest) {
        try {
            Comment existingComment = commentService.retrieveCommentById(commentId);
            existingComment.setCommentTitle(commentRequest.getCommentTitle());
            existingComment.setContent(commentRequest.getContent());
            existingComment.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<Comment>(commentService.saveComment(existingComment), HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/forumDiscussions/{forumDiscussionId}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsOfForumDiscussion(@PathVariable(value="forumDiscussionId") Long forumDiscussionId) {
        try {
            ForumDiscussion forumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
            //need to go through the list of forum discussions in forum and delete each FD
            //on top of just clearing the list of forum discussions from forum
            List<Comment> commentList = forumDiscussion.getComments();
            for (Comment comment : commentList) {
                commentService.deleteComment(comment.getCommentId());
            }
            forumDiscussion.getComments().clear();
            forumDiscussionService.saveForumDiscussion(forumDiscussion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }



}
