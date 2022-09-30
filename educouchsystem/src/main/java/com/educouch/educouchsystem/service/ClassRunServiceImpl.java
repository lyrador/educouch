package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.ClassRunDTO;
import com.educouch.educouchsystem.dto.ForumDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.ClassRunRepository;
import com.educouch.educouchsystem.repository.CourseRepository;
import com.educouch.educouchsystem.util.enumeration.RecurringEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ClassRunServiceImpl implements ClassRunService {

    @Autowired
    private ClassRunRepository classRunRepository;

    @Autowired
    private CourseRepository courseRepository;

    private CourseService courseService;

    private EducatorService educatorService;

    private LearnerService learnerService;

    private EventService eventService;

    public ClassRunServiceImpl(CourseService courseService, EducatorService educatorService, LearnerService learnerService, EventService eventService) {
        this.courseService = courseService;
        this.educatorService = educatorService;
        this.learnerService = learnerService;
        this.eventService = eventService;
    }

    public ClassRun saveClassRun(ClassRun classRun) {
        return classRunRepository.save(classRun);
    }

    @Override
    public List<ClassRun> getAllClassRuns() {
        return (List<ClassRun>) classRunRepository.findAll();
    }

    @Override
    public ClassRun retrieveClassRunById(Long id) {
        return classRunRepository.findById(id).get();
    }

    @Override
    public void deleteClassRun(Long id) {
        classRunRepository.deleteById(id);
    }

    @Override
    public ClassRun addClassRunFromCourseId(Long courseId, ClassRunDTO classRunDTORequest) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Course course = courseService.retrieveCourseById(courseId);
        ClassRun classRun = new ClassRun();
        classRun.setCourse(course);
        Date startDate = (Date)formatter.parse(classRunDTORequest.getClassRunStart());
        Date endDate = (Date)formatter.parse(classRunDTORequest.getClassRunEnd());
        classRun.setClassRunStart(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        classRun.setClassRunEnd(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        classRun.setMinClassSize(classRunDTORequest.getMinClassSize());
        classRun.setMaximumCapacity(classRunDTORequest.getMaximumCapacity());
        classRun.setClassRunDaysOfTheWeek(classRunDTORequest.getClassRunDaysOfTheWeek());
        classRun.setRecurringEnum(RecurringEnum.valueOf(classRunDTORequest.getRecurringEnumString()));
//        classRun.setCalendar(classRunDTORequest.getCalendarId());
        Instructor instructor = educatorService.findInstructorByUsername(classRunDTORequest.getInstructorUsername());
        classRun.setInstructor(instructor);
        List<Learner> learners = new ArrayList<>();
//        for (String learnerUsername : classRunDTORequest.getEnrolledLearnersUsernames()) {
//            Learner learner = learnerService.findLearnerByUsername(learnerUsername);
//            learners.add(learner);
//        }
        classRun.setEnrolledLearners(learners);
        classRun.setClassRunName(classRunDTORequest.getClassRunName());
        classRun.setClassRunDescription(classRunDTORequest.getClassRunDescription());
        classRunRepository.save(classRun);
        course.getClassRuns().add(classRun);
        courseRepository.save(course);
        return classRun;
    }

    @Override
    public ClassRun updateClassRun(Long classRunId, ClassRunDTO classRunDTORequest) throws ParseException {
        ClassRun classRunToUpdate = classRunRepository.findById(classRunDTORequest.getClassRunId()).get();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = (Date)formatter.parse(classRunDTORequest.getClassRunStart());
        Date endDate = (Date)formatter.parse(classRunDTORequest.getClassRunEnd());
        classRunToUpdate.setClassRunStart(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        classRunToUpdate.setClassRunEnd(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        classRunToUpdate.setMinClassSize(classRunDTORequest.getMinClassSize());
        classRunToUpdate.setMaximumCapacity(classRunDTORequest.getMaximumCapacity());
        classRunToUpdate.setClassRunDaysOfTheWeek(classRunDTORequest.getClassRunDaysOfTheWeek());
        classRunToUpdate.setRecurringEnum(RecurringEnum.valueOf(classRunDTORequest.getRecurringEnumString()));
//        classRunToUpdate.setCalendar(classRunDTORequest.getCalendarId());
        Instructor instructor = educatorService.findInstructorByUsername(classRunDTORequest.getInstructorUsername());
        classRunToUpdate.setInstructor(instructor);
        List<Learner> learners = new ArrayList<>();
        for (String learnerUsername : classRunDTORequest.getEnrolledLearnersUsernames()) {
            Learner learner = learnerService.findLearnerByUsername(learnerUsername);
            learners.add(learner);
        }
        classRunToUpdate.setEnrolledLearners(learners);
        classRunToUpdate.setClassRunName(classRunDTORequest.getClassRunName());
        classRunToUpdate.setClassRunDescription(classRunDTORequest.getClassRunDescription());
        classRunRepository.save(classRunToUpdate);
        return classRunToUpdate;
    }

    @Override
    public List<ClassRunDTO> findClassRunsFromCourseId (Long courseId) {
        List<ClassRun> classRuns = classRunRepository.findClassRunsFromCourseId(courseId);
        List<ClassRunDTO> classRunDTOs= new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (ClassRun classRun : classRuns) {
            ClassRunDTO classRunDTO = new ClassRunDTO(
                    classRun.getClassRunId(),
                    classRun.getClassRunStart().format(formatter),
                    classRun.getClassRunEnd().format(formatter),
                    classRun.getMinClassSize(),
                    classRun.getMaximumCapacity(),
                    classRun.getClassRunDaysOfTheWeek(),
                    classRun.getRecurringEnum().toString(),
                    null,
                    classRun.getInstructor().getUsername(),
                    null,
                    classRun.getClassRunName(),
                    classRun.getClassRunDescription());
            classRunDTOs.add(classRunDTO);
        }
        return classRunDTOs;
    }

    @Override
    public List<Event> generateClassEventsFromClassRunId(Long classRunId) {
        List<Event> newEvents = new ArrayList<>();
        ClassRun classRun = classRunRepository.findById(classRunId).get();
        HashMap<Integer, DayOfWeek> numToDateOfWeekMap = new HashMap<>();
        numToDateOfWeekMap.put(0, DayOfWeek.SUNDAY);
        numToDateOfWeekMap.put(1, DayOfWeek.MONDAY);
        numToDateOfWeekMap.put(2, DayOfWeek.TUESDAY);
        numToDateOfWeekMap.put(3, DayOfWeek.WEDNESDAY);
        numToDateOfWeekMap.put(4, DayOfWeek.THURSDAY);
        numToDateOfWeekMap.put(5, DayOfWeek.FRIDAY);
        numToDateOfWeekMap.put(6, DayOfWeek.SATURDAY);

        //check if its only occuring on 1 day of the week
        if (classRun.getClassRunDaysOfTheWeek().length == 1) {
            //check if its occuring weekly
            if (classRun.getRecurringEnum() == RecurringEnum.WEEKLY) {
                //initiate a pointer at the start date
                LocalDate localDatePointer = classRun.getClassRunStart();
                while (localDatePointer.getDayOfWeek() != numToDateOfWeekMap.get(classRun.getClassRunDaysOfTheWeek()[0])) {
                    localDatePointer.plusDays(1);
                }
                while (localDatePointer.isBefore(classRun.getClassRunEnd())) {
                    Event newEvent = new Event();
                    newEvent.setClassRun(classRun);
                    newEvent.setTitle(classRun.getClassRunName() + " " + "Class Event");
                    newEvent.setEventDescription(classRun.getClassRunName() + " " + "Class Event");
                    newEvent.setStartDate(LocalDateTime.of(classRun.getClassRunStart(), classRun.getClassRunStartTime()));
                    newEvent.setEventEndDate(LocalDateTime.of(classRun.getClassRunEnd(), classRun.getClassRunEndTime()));
                    eventService.saveEvent(newEvent);
                    newEvents.add(newEvent);
                    localDatePointer.plusDays(7);
                }
            }
        }
        return newEvents;
    }
}
