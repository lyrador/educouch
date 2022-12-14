package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.*;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.*;
import com.educouch.educouchsystem.util.enumeration.AssessmentStatusEnum;
import com.educouch.educouchsystem.util.enumeration.QuestionTypeEnum;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.CascadeType;
import javax.websocket.server.PathParam;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private InteractivePageService interactivePageService;

    @PostMapping("/createQuiz/{courseId}")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDTO quizDTO, @PathVariable(value="courseId") Long courseId) {

        try {
            //Instantiate quiz
            Quiz newQuiz = instantiateQuiz(quizDTO, courseId);
            //Add questions
            newQuiz = addQuestions(newQuiz, quizDTO.getQuestions());
            quizService.saveQuiz(courseId, newQuiz);
            return new ResponseEntity<>(newQuiz, HttpStatus.OK);
        } catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createQuizForInteractivePage/{interactivePageId}")
    public ResponseEntity<Quiz> createQuizForInteractivePage(@RequestBody QuizDTO quizDTO, @PathVariable(value="interactivePageId") Long interactivePageId) {

        try {
            //Instantiate quiz
            Quiz newQuiz = instantiateQuiz(quizDTO, interactivePageId);
            //Add questions
            newQuiz = addQuestions(newQuiz, quizDTO.getQuestions());
            InteractivePage interactivePage = interactivePageService.getInteractivePageById(interactivePageId);
            newQuiz.setInteractivePage(interactivePage);
            interactivePage.setPageQuiz(newQuiz);
            //interactivePageService.saveInteractivePage(interactivePage);
            quizService.saveQuiz(newQuiz);
            return new ResponseEntity<>(newQuiz, HttpStatus.OK);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getQuizById/{quizId}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable(value="quizId") Long quizId) {
        try {
            Quiz quiz = quizService.retrieveQuizById(quizId);
            QuizDTO quizDto = convertQuizToDTO(quiz);
            return new ResponseEntity<>(quizDto, HttpStatus.OK);
        } catch (QuizNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getQuizByInteractivePageId/{interactivePageId}")
    public ResponseEntity<QuizDTO> getQuizByInteractivePageId(@PathVariable(value="interactivePageId") Long interactivePageId) {
        try {
            InteractivePage interactivePage = interactivePageService.getInteractivePageById(interactivePageId);
            QuizDTO quizDTO = convertQuizToDTO(interactivePage.getPageQuiz());
            return new ResponseEntity<>(quizDTO, HttpStatus.OK);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateQuizById/{quizId}")
    public ResponseEntity<Quiz> updateQuizById(@RequestBody QuizDTO updatedQuizDTO, @PathVariable(value="quizId") Long quizId) {
        try {
            Quiz updatedQuiz = quizService.retrieveQuizById(quizId);
            if(!(updatedQuiz.getTitle().equals(updatedQuizDTO.getAssessmentTitle()))) {
                updatedQuiz.setTitle(updatedQuizDTO.getAssessmentTitle());
            }
            if(!(updatedQuiz.getDescription().equals(updatedQuizDTO.getAssessmentDescription()))) {
                updatedQuiz.setDescription(updatedQuizDTO.getAssessmentDescription());
            }
            if(!(updatedQuiz.getMaxScore().equals(updatedQuizDTO.getAssessmentMaxScore()))) {
                updatedQuiz.setMaxScore(updatedQuizDTO.getAssessmentMaxScore());
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            if(!(updatedQuiz.getStartDate().toString().equals(updatedQuizDTO.getAssessmentStartDate()))) {
                updatedQuiz.setStartDate((Date) formatter.parse(updatedQuizDTO.getAssessmentStartDate()));
            }
            if(!(updatedQuiz.getEndDate().toString().equals(updatedQuizDTO.getAssessmentEndDate()))) {
                updatedQuiz.setEndDate((Date) formatter.parse(updatedQuizDTO.getAssessmentEndDate()));
            }
            updatedQuiz.setDiscountPointForAssessment(updatedQuizDTO.getDiscountPointForAssessment());
            updatedQuiz.setDiscountPointToTopPercent(updatedQuizDTO.getDiscountPointToTopPercent());
            if (updatedQuizDTO.getHasTimeLimit().equals("true")) {
                updatedQuiz.setHasTimeLimit(Boolean.TRUE);
            } else if (updatedQuizDTO.getHasTimeLimit().equals("false")) {
                updatedQuiz.setHasTimeLimit(Boolean.FALSE);
            }

            if(updatedQuizDTO.getHasMaxAttempts().equals("true")) {
                updatedQuiz.setHasMaxAttempts(Boolean.TRUE);
                updatedQuiz.setMaxAttempts(updatedQuizDTO.getMaxAttempts());
            } else {
                updatedQuiz.setHasMaxAttempts(Boolean.FALSE);
                updatedQuiz.setMaxAttempts(0);
            }

            updatedQuiz.setTimeLimit(updatedQuizDTO.getTimeLimit());
            updatedQuiz.setQuestionCounter(updatedQuizDTO.getQuestionCounter());
            if (updatedQuizDTO.getIsAutoRelease().equals("true")) {
                updatedQuiz.setAutoRelease(Boolean.TRUE);
            } else if (updatedQuizDTO.getIsAutoRelease().equals("false")) {
                updatedQuiz.setAutoRelease(Boolean.FALSE);
            }


            //doesnt update isOpen
            updatedQuiz = updateQuizQuestions(updatedQuiz, updatedQuizDTO.getQuestions());
            quizService.saveQuiz(updatedQuiz);
            return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
        } catch (QuizNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.valueOf("Date Parsing failed"));
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.valueOf("Something went wrong updating questions"));
        } catch (OptionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.valueOf("Something went wrong updating options"));

        }
    }

    public Quiz updateQuizQuestions(Quiz oldQuiz, List<QuestionDTO> questionDTOs) throws QuestionNotFoundException, OptionNotFoundException{

        List<Question> questions = oldQuiz.getQuizQuestions();
        for (int j=0; j<questions.size(); j++) {
            List<Option> options = questions.get(j).getOptions();
            questionService.deleteQuestion(questions.get(j).getQuestionId());
        }
        while(questions.size()>0) {
            questions.remove(0);
        }

        Quiz updatedQuiz = addQuestions(oldQuiz, questionDTOs);
        return updatedQuiz;
    }

    public Quiz instantiateQuiz(QuizDTO quizDTO, Long courseId) throws CourseNotFoundException, ParseException{

        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(quizDTO.getAssessmentTitle());
        newQuiz.setDescription(quizDTO.getAssessmentDescription());
        newQuiz.setMaxScore(quizDTO.getAssessmentMaxScore());
        newQuiz.setDiscountPointForAssessment(quizDTO.getDiscountPointForAssessment());
        newQuiz.setDiscountPointToTopPercent(quizDTO.getDiscountPointToTopPercent());
        newQuiz.setAssessmentStatus(AssessmentStatusEnum.PENDING);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = (Date) formatter.parse(quizDTO.getAssessmentStartDate());
        Date endDate = (Date) formatter.parse(quizDTO.getAssessmentEndDate());
        newQuiz.setStartDate(startDate);
        newQuiz.setEndDate(endDate);
        newQuiz.setOpen(Boolean.FALSE);
        if(quizDTO.getHasMaxAttempts().equals("true")) {
            newQuiz.setHasMaxAttempts(Boolean.TRUE);
            newQuiz.setMaxAttempts(quizDTO.getMaxAttempts());
        } else {
            newQuiz.setHasMaxAttempts(Boolean.FALSE);
            newQuiz.setMaxAttempts(0);
        }

        if (quizDTO.getHasTimeLimit().equals("true")) {
            newQuiz.setHasTimeLimit(Boolean.TRUE);
        } else if (quizDTO.getHasTimeLimit().equals("false")) {
            newQuiz.setHasTimeLimit(Boolean.FALSE);
        }

        newQuiz.setTimeLimit(quizDTO.getTimeLimit());

        newQuiz.setQuestionCounter(quizDTO.getQuestionCounter());
        newQuiz.setOpen(false);
        if (quizDTO.getIsAutoRelease().equals("true")) {
            newQuiz.setAutoRelease(Boolean.TRUE);
        } else if (quizDTO.getIsAutoRelease().equals("false")) {
            newQuiz.setAutoRelease(Boolean.FALSE);
        }

        newQuiz.setQuizQuestions(new ArrayList<>());
        return newQuiz;
    }

    public Quiz addQuestions(Quiz quiz, List<QuestionDTO> questionDTOs) {

        List<Question> questions = quiz.getQuizQuestions();

        for(QuestionDTO q : questionDTOs) {
            //add question to quiz
            Question question = convertQuestionDTOToQuestion(q);
            questions.add(question);

        }

        quiz.setQuizQuestions(questions);
        return quiz;
    }

    public Question convertQuestionDTOToQuestion (QuestionDTO q) {
        Question question = new Question();
        question.setLocalid(q.getLocalid());
        question.setQuestionTitle(q.getQuestionTitle());
        if(q.getQuestionType().equals("mcq")) {
            question.setQuestionType(QuestionTypeEnum.MCQ);
        } else if(q.getQuestionType().equals("shortAnswer")) {
            question.setQuestionType(QuestionTypeEnum.OPEN_ENDED);
        } else if(q.getQuestionType().equals("trueFalse")) {
            question.setQuestionType(QuestionTypeEnum.TRUE_FALSE);
        }
        question.setQuestionContent(q.getQuestionContent());
        question.setQuestionHint(q.getQuestionHint());
        try {
            question.setQuestionMaxScore(Double.parseDouble(q.getQuestionMaxPoints()));
        } catch (Exception e) {
            question.setQuestionMaxScore(0.0);
        }

        //for each question, add options to question
        question = addOptions(question, q.getOptions());
        if(!q.getCorrectOption().equals("")) {
            question.setCorrectOption(new Option(q.getCorrectOption()));
        }

        return question;

    }

    public Question addOptions(Question question, List<String> optionDTOs) {

        List<Option> options = question.getOptions();

        for(String o : optionDTOs ) {
            Option option = new Option(o);
            options.add(option);
        }

        question.setOptions(options);
        return question;
    }

    public QuizDTO convertQuizToDTO(Quiz quiz) {

        QuizDTO quizDTO = new QuizDTO();

        quizDTO.setAssessmentId(quiz.getAssessmentId());
        quizDTO.setAssessmentTitle(quiz.getTitle());
        quizDTO.setAssessmentDescription(quiz.getDescription());
        quizDTO.setAssessmentMaxScore(quiz.getMaxScore());
        quizDTO.setDiscountPointForAssessment(quiz.getDiscountPointForAssessment());
        quizDTO.setDiscountPointToTopPercent(quiz.getDiscountPointToTopPercent());
        quizDTO.setAssessmentStatusEnum(quiz.getAssessmentStatus().toString());

        quizDTO.setAssessmentStartDate(quiz.getStartDate().toString());
        quizDTO.setAssessmentEndDate(quiz.getEndDate().toString());
        quizDTO.setAssessmentIsOpen(quiz.getOpen().toString());
        Date today = new Date();
        if(today.after(quiz.getEndDate())) {
            quizDTO.setIsExpired("true");
        } else {
            quizDTO.setIsExpired("false");
        }

        if(quiz.getHasTimeLimit()) {
            quizDTO.setHasTimeLimit("true");
        } else {
            quizDTO.setHasTimeLimit("false");
        }

        if(quiz.getHasMaxAttempts()) {
            quizDTO.setHasMaxAttempts("true");
            quizDTO.setMaxAttempts(quiz.getMaxAttempts());
        } else {
            quizDTO.setHasMaxAttempts("false");
            quizDTO.setMaxAttempts(0);
        }

        quizDTO.setTimeLimit(quiz.getTimeLimit());

        quizDTO.setQuestionCounter(quiz.getQuestionCounter());

        if(quiz.getAutoRelease()) {
            quizDTO.setIsAutoRelease("true");
        } else {
            quizDTO.setIsAutoRelease("false");
        }

        List<QuestionDTO> questionDTOs = convertQuestionsToQuestionDTOs(quiz.getQuizQuestions());
        quizDTO.setQuestions(questionDTOs);

        return quizDTO;
    }

    public List<QuestionDTO> convertQuestionsToQuestionDTOs(List<Question> questions) {

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for(Question q : questions) {
            QuestionDTO questionDTO = convertQuestionToQuestionDTO(q);
            questionDTOs.add(questionDTO);
            if(!(q.getCorrectOption()==null)) {
                questionDTO.setCorrectOption(q.getCorrectOption().getOptionContent());
            }
        }
        return questionDTOs;
    }

    public QuestionDTO convertQuestionToQuestionDTO(Question q) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(q.getQuestionId().toString());
        questionDTO.setLocalid(q.getLocalid());
        questionDTO.setQuestionTitle(q.getQuestionTitle());
        String questionType = q.getQuestionType().toString();
        if(questionType.equals("MCQ")) {
            questionDTO.setQuestionType("mcq");
        } else if(questionType.equals("TRUE_FALSE")) {
            questionDTO.setQuestionType("trueFalse");
        } else if(questionType.equals("OPEN_ENDED")) {
            questionDTO.setQuestionType("shortAnswer");
        }
        questionDTO.setQuestionContent(q.getQuestionContent());
        questionDTO.setQuestionHint(q.getQuestionHint());
        questionDTO.setQuestionMaxPoints(q.getQuestionMaxScore().toString());
        if(!questionType.equals("OPEN_ENDED")) {
            questionDTO.setCorrectOption(q.getCorrectOption().getOptionContent());
        }

        //link options
        questionDTO.setOptions(convertOptionsToOptionDTOs(q.getOptions()));
        return questionDTO;
    }

    public List<String> convertOptionsToOptionDTOs(List<Option> options) {

        List<String> optionStrings = new ArrayList<>();
        for(Option o : options) {
            optionStrings.add(o.getOptionContent());
        }
        return optionStrings;
    }

}
