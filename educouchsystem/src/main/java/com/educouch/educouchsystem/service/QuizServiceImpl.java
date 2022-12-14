package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.OptionRepository;
import com.educouch.educouchsystem.repository.QuestionRepository;
import com.educouch.educouchsystem.repository.QuizRepository;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private OptionRepository optionRepository;

    @Override
    //for updating quiz
    public Quiz saveQuiz(Quiz quiz) {
        List<Question> questions = quiz.getQuizQuestions();
        for(Question q : questions) {
            List<Option> options = q.getOptions();
            if(options.size()>0) {
                for(Option o : options) {
                    optionRepository.save(o);
                }
            }
            if(!(q.getCorrectOption() == null)) {
                optionRepository.save(q.getCorrectOption());
            }
            questionRepository.save(q);
        }
        return quizRepository.save(quiz);
    }

    //creates quiz
    @Override
    public Quiz saveQuiz(Long courseId, Quiz quiz) throws CourseNotFoundException {

        Course course = courseService.getCourseById(courseId);
        if (course != null) {
            List<Question> questions = quiz.getQuizQuestions();
            for(Question q : questions) {
                List<Option> options = q.getOptions();
                if(options.size()>0) {
                    for(Option o : options) {
                        optionRepository.save(o);
                    }
                }
                if(!(q.getCorrectOption() == null)) {
                    optionRepository.save(q.getCorrectOption());
                }
                questionRepository.save(q);
            }

            course.getAssessments().add(quiz);
            courseService.saveCourse(course);
//            quizRepository.save(quiz);
            return quiz;

        } else {
            throw new CourseNotFoundException("Course Id " + courseId + " does not exist!");
        }
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz retrieveQuizById(Long assessmentId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(assessmentId).get();
        if (quiz != null) {
            return quiz;
        } else {
            throw new QuizNotFoundException("Quiz Id " + assessmentId + " does not exist!");
        }
    }

    @Override
    public List<Quiz> getAllQuizzesByCourseId(Long courseId) throws CourseNotFoundException {
        Course course = courseService.retrieveCourseById(courseId);
        if (course != null) {
            List<Assessment> assessments = course.getAssessments();
            List<Quiz> quizzes = new ArrayList<>();
            for (Assessment assessment : assessments) {
                if (assessment instanceof Quiz) {
                    Quiz quiz = (Quiz) assessment;
                    quizzes.add(quiz);
                }
            }
            return quizzes;
        } else {
            throw new CourseNotFoundException("Course Id " + courseId + " does not exist!");
        }
    }

    @Override
    public void deleteQuiz(Long quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId).get();
        if (quiz != null) {
            quizRepository.deleteById(quizId);
        } else {
            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
        }
    }

    @Override
    public Quiz updateQuiz(Quiz quizToUpdate, Quiz quiz) throws QuizNotFoundException {
        if (quizToUpdate != null && quizToUpdate.getAssessmentId().equals(quiz.getAssessmentId())) {
            quizToUpdate.setTitle(quiz.getTitle());
            quizToUpdate.setDescription(quiz.getDescription());
            quizToUpdate.setMaxScore(quiz.getMaxScore());
            quizToUpdate.setDiscountPointToTopPercent(quiz.getDiscountPointToTopPercent());
            quizToUpdate.setDiscountPointToTopPercent(quiz.getDiscountPointToTopPercent());
            quizToUpdate.setStartDate(quiz.getStartDate());
            quizToUpdate.setEndDate(quiz.getEndDate());
            quizToUpdate.setOpen(quiz.getOpen());
            quizToUpdate.setAssessmentStatus(quiz.getAssessmentStatus());
            quizToUpdate.setHasTimeLimit(quiz.getHasTimeLimit());
            quizToUpdate.setAutoRelease(quiz.getAutoRelease());
            quizRepository.save(quizToUpdate);
            return quizToUpdate;
        } else {
            throw new QuizNotFoundException("Quiz to be updated does not exist!");
        }
    }

//    @Override
//    public void addQuestionToQuiz(Long quizId, Question question) throws QuizNotFoundException, EntityInstanceExistsInCollectionException {
//        Quiz quizToEdit = retrieveQuizById(quizId);
//        List<Question> quizQuestions = quizToEdit.getQuizQuestions();
//        if (!quizQuestions.contains(question)) {
//            quizQuestions.add(question);
//            saveQuiz(quizToEdit);
//            quizRepository.save(quizToEdit);
//        } else {
//            throw new EntityInstanceExistsInCollectionException("Question already exists in quiz!");
//        }
//    }

//    @Override
//    public void deleteQuestionFromQuizId(Long questionId, Long quizId) throws QuizNotFoundException, QuestionNotFoundException {
//        Question question = questionRepository.findById(questionId).get();
//        retrieveQuizById(quizId).getQuizQuestions().remove(question);
//        if (question != null) {
//            questionRepository.deleteById(questionId);
//        } else {
//            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
//        }
//    }
    //    @Override
//    public List<Question> getAllQuestionsInQuiz(Long quizId) throws QuizNotFoundException {
//        Quiz quiz = quizRepository.findById(quizId).get();
//        if (quiz != null) {
//            List<Question> questions = quiz.getQuizQuestions();
//            return questions;
//        } else {
//            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
//        }
//    }

//    @Override
//    public List<QuizAttempt> getAllQuizAttemptsInQuiz(Long quizId) throws QuizNotFoundException {
//        Quiz quiz = quizRepository.findById(quizId).get();
//        if (quiz != null) {
//            List<QuizAttempt> quizAttempts = quiz.getQuizAttempts();
//            return quizAttempts;
//        } else {
//            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
//        }
//    }
}
