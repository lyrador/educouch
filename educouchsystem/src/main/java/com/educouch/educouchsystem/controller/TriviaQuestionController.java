package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ChapterToReorderDTO;
import com.educouch.educouchsystem.dto.FileItemDTO;
import com.educouch.educouchsystem.dto.QuestionToReorderDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.AttachmentService;
import com.educouch.educouchsystem.service.TriviaQuestionService;
import com.educouch.educouchsystem.service.TriviaQuizService;
import com.educouch.educouchsystem.util.comparator.ChapterComparator;
import com.educouch.educouchsystem.util.comparator.TriviaQuestionComparator;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/triviaQuestion")
@CrossOrigin
public class TriviaQuestionController {

    @Autowired
    private TriviaQuestionService triviaQuestionService;

    @Autowired
    private TriviaQuizService triviaQuizService;

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/{triviaQuizId}/triviaQuestions")
    public ResponseEntity<TriviaQuestion> addTriviaQuestion(@PathVariable(value="triviaQuizId") Long triviaQuizId, @RequestBody TriviaQuestion triviaQuestionRequest) {
        try {
            TriviaQuiz triviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            triviaQuestionRequest.setTriviaQuiz(triviaQuiz);
            if (triviaQuiz.getTriviaQuestions() != null) {
                triviaQuiz.getTriviaQuestions().add(triviaQuestionRequest);
                triviaQuestionRequest.setQuestionNumber(triviaQuiz.getTriviaQuestions().size() + 1);
            } else {
                List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
                triviaQuestionRequest.setQuestionNumber(1);
                triviaQuestionList.add(triviaQuestionRequest);
                triviaQuiz.setTriviaQuestions(triviaQuestionList);
            }

            if(triviaQuestionRequest.getTriviaQuiz() != null) {
                if (triviaQuestionRequest.getTriviaQuiz().getTriviaQuestions() != null) {
                    triviaQuestionRequest.setQuestionNumber(triviaQuestionRequest.getTriviaQuiz().getTriviaQuestions().size());
                    triviaQuiz.setNumOfQuestions(triviaQuestionRequest.getTriviaQuiz().getTriviaQuestions().size());
                }
            }
            triviaQuestionRequest.setTriviaQuestionType(triviaQuestionRequest.getTriviaQuestionType());
            triviaQuestionRequest.setQuestionIsValid(checkAndApplyQuestionIsValid(triviaQuestionRequest));
            TriviaQuestion triviaQuestion = triviaQuestionService.saveTriviaQuestion(triviaQuestionRequest);
            triviaQuizService.saveTriviaQuiz(triviaQuiz);

            return new ResponseEntity<>(triviaQuestion, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaQuestions")
    public ResponseEntity<List<TriviaQuestion>> getAllTriviaQuestions() {
        List<TriviaQuestion> allTriviaQuestions = new ArrayList<>();
        if (!triviaQuizService.getAllTriviaQuizzes().isEmpty()) {
            allTriviaQuestions = triviaQuestionService.getAllTriviaQuestions();
            return new ResponseEntity<>(allTriviaQuestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{triviaQuestionId}/triviaQuestions")
    public ResponseEntity<TriviaQuestion> getTriviaQuestionById(@PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            return new ResponseEntity<>(triviaQuestion, HttpStatus.OK);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{triviaQuestionId}/triviaQuestionsUnmarshalledQuestionFromOptions")
    public ResponseEntity<TriviaQuestion> getTriviaQuestionByIdWithUnmarshalledQuestionFromOptions(@PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            for (TriviaOption triviaOption : triviaQuestion.getTriviaOptions()) {
                triviaOption.setTriviaQuestion(null);
            }
            return new ResponseEntity<>(triviaQuestion, HttpStatus.OK);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/triviaQuestions/{triviaQuestionId}")
    public ResponseEntity<TriviaQuestion> deleteTriviaQuestion(@PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion existingTriviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            TriviaQuiz triviaQuiz = existingTriviaQuestion.getTriviaQuiz();

            List<TriviaQuestion> triviaQuestionList = triviaQuiz.getTriviaQuestions();
            if (triviaQuestionList.size() != existingTriviaQuestion.getQuestionNumber()) {
                for (int i = existingTriviaQuestion.getQuestionNumber(); i < triviaQuestionList.size(); i++) {
                    TriviaQuestion triviaQuestionToUpdate = triviaQuestionList.get(i);
                    Integer updatedQuestionNumber = triviaQuestionToUpdate.getQuestionNumber() - 1;
                    triviaQuestionToUpdate.setQuestionNumber(updatedQuestionNumber);
                    triviaQuestionService.saveTriviaQuestion(triviaQuestionToUpdate);
                }
            }

            triviaQuiz.getTriviaQuestions().remove(existingTriviaQuestion);
            existingTriviaQuestion.setTriviaQuiz(null);

            triviaQuestionService.deleteTriviaQuestion(triviaQuestionId);

            triviaQuiz.setNumOfQuestions(triviaQuiz.getTriviaQuestions().size());
            triviaQuizService.saveTriviaQuiz(triviaQuiz);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/triviaQuestions/{triviaQuizId}/deleteAll")
    public ResponseEntity<TriviaQuestion> deleteAllTriviaQuestionsOfQuiz (@PathVariable("triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            existingTriviaQuiz.getTriviaQuestions().clear();
            triviaQuizService.saveTriviaQuiz(existingTriviaQuiz);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/triviaQuestions/{triviaQuestionId}")
    public ResponseEntity<TriviaQuestion> updateTriviaQuestion (@RequestBody TriviaQuestion triviaQuestion, @PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion existingTriviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            existingTriviaQuestion.setQuestionTitle(triviaQuestion.getQuestionTitle());
            existingTriviaQuestion.setQuestionTimeLimit(triviaQuestion.getQuestionTimeLimit());
            existingTriviaQuestion.setHasTimeLimit(triviaQuestion.getHasTimeLimit());
            existingTriviaQuestion.setTriviaQuestionType(triviaQuestion.getTriviaQuestionType());
            existingTriviaQuestion.setQuestionIsValid(checkAndApplyQuestionIsValid(existingTriviaQuestion));
            triviaQuestionService.saveTriviaQuestion(existingTriviaQuestion);
            return new ResponseEntity<>(existingTriviaQuestion, HttpStatus.OK);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaQuiz/{triviaQuizId}/triviaQuestions")
    public ResponseEntity<List<TriviaQuestion>> getAllTriviaQuestionsByTriviaQuizId(@PathVariable("triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
            triviaQuestionList.addAll(existingTriviaQuiz.getTriviaQuestions());
            Collections.sort(triviaQuestionList, new TriviaQuestionComparator());
            return new ResponseEntity<>(triviaQuestionList, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaQuiz/{triviaQuizId}/validTriviaQuestions")
    public ResponseEntity<List<TriviaQuestion>> getAllValidTriviaQuestionsByTriviaQuizId(@PathVariable("triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
            triviaQuestionList.addAll(existingTriviaQuiz.getTriviaQuestions());
            Collections.sort(triviaQuestionList, new TriviaQuestionComparator());
            for (TriviaQuestion triviaQuestion : triviaQuestionList) {
                if (triviaQuestion.getQuestionIsValid() == false) {
                    triviaQuestionList.remove(triviaQuestion);
                }
            }
            return new ResponseEntity<>(triviaQuestionList, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/triviaQuiz/{triviaQuizId}/reorderQuestions")
    public ResponseEntity<List<TriviaQuestion>> reorderTriviaQuestions(@PathVariable(value="triviaQuizId") Long triviaQuizId, @RequestBody List<QuestionToReorderDTO> questionToReorderDTOList) {
        try {
            TriviaQuiz triviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            for (QuestionToReorderDTO questionToReorderDTO : questionToReorderDTOList) {
                TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(questionToReorderDTO.getTriviaQuestionId());
                triviaQuestion.setQuestionNumber(Integer.valueOf(questionToReorderDTO.getQuestionNumber()));
                triviaQuestion = triviaQuestionService.saveTriviaQuestion(triviaQuestion);
            }
            List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
            triviaQuestionList.addAll(triviaQuiz.getTriviaQuestions());
            return new ResponseEntity<>(triviaQuestionList, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TriviaQuestionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{triviaQuestionId}/addFileItem")
    public ResponseEntity<TriviaQuestion> addFileItem(@PathVariable(value="triviaQuestionId") Long triviaQuestionId, @RequestBody FileItemDTO fileItemDTORequest) {
        try {
            TriviaQuestion existingTriviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            if (existingTriviaQuestion.getAttachment() != null) {
                Long attachmentIdToDelete = existingTriviaQuestion.getAttachment().getAttachmentId();
                existingTriviaQuestion.setAttachment(null);
                attachmentService.deleteAttachment(attachmentIdToDelete);
            }
            Attachment attachment = attachmentService.getAttachment(fileItemDTORequest.getAttachmentId());
            existingTriviaQuestion.setAttachment(attachment);
            triviaQuestionService.saveTriviaQuestion(existingTriviaQuestion);
            return new ResponseEntity<>(existingTriviaQuestion, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TriviaQuestionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{triviaQuestionId}/removeFileItem")
    public ResponseEntity<TriviaQuestion> removeFileItem(@PathVariable(value="triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion existingTriviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            if (existingTriviaQuestion.getAttachment() != null) {
                Long attachmentIdToDelete = existingTriviaQuestion.getAttachment().getAttachmentId();
                existingTriviaQuestion.setAttachment(null);
                attachmentService.deleteAttachment(attachmentIdToDelete);
            }
            triviaQuestionService.saveTriviaQuestion(existingTriviaQuestion);
            return new ResponseEntity<>(existingTriviaQuestion, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TriviaQuestionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public Boolean checkAndApplyQuestionIsValid(TriviaQuestion triviaQuestion) {
        //check for questionTitle
        if (triviaQuestion.getQuestionTitle().length() > 0) {
            //check for at least 2 options populated
            Boolean hasFirstOption = false;
            Boolean hasSecondOption = false;
            for (TriviaOption triviaOption : triviaQuestion.getTriviaOptions()) {
                if (triviaOption.getOptionNumber() == 1) {
                    hasFirstOption = true;
                }
                if (triviaOption.getOptionNumber() == 2) {
                    hasSecondOption = true;
                }
            }
            //check for there must be exactly one correct answer options populated
            if (hasFirstOption && hasSecondOption) {
                for (TriviaOption triviaOption : triviaQuestion.getTriviaOptions()) {
                    if (triviaOption.getCorrectAnswer()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
