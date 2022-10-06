package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.ClassRunDTO;
import com.educouch.educouchsystem.dto.ForumDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.ClassRunRepository;
import com.educouch.educouchsystem.repository.CourseRepository;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.util.enumeration.RecurringEnum;
import com.educouch.educouchsystem.util.exception.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        ClassRun classRunToDelete = retrieveClassRunById(id);
        for (Event event : classRunToDelete.getEvents()) {
            event.setClassRun(null);
        }
        classRunToDelete.getEvents().clear();
        classRunRepository.deleteById(id);
    }

    @Override
    public ClassRun addClassRunFromCourseId(Long courseId, ClassRunDTO classRunDTORequest) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Course course = courseService.retrieveCourseById(courseId);
        ClassRun classRun = new ClassRun();
        classRun.setCourse(course);
        Date startDate = (Date) formatter.parse(classRunDTORequest.getClassRunStart());
        Date endDate = (Date) formatter.parse(classRunDTORequest.getClassRunEnd());
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
        classRun.setClassRunStartTime(LocalTime.parse(classRunDTORequest.getClassRunStartTime(), timeFormatter));
        classRun.setClassRunEndTime(LocalTime.parse(classRunDTORequest.getClassRunEndTime(), timeFormatter));
        classRunRepository.save(classRun);
        course.getClassRuns().add(classRun);
        courseRepository.save(course);
        generateClassEventsFromClassRunId(classRun.getClassRunId());
        return classRun;
    }

    @Override
    public ClassRun updateClassRun(Long classRunId, ClassRunDTO classRunDTORequest) throws ParseException {
        ClassRun classRunToUpdate = classRunRepository.findById(classRunDTORequest.getClassRunId()).get();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Date startDate = (Date) formatter.parse(classRunDTORequest.getClassRunStart());
        Date endDate = (Date) formatter.parse(classRunDTORequest.getClassRunEnd());
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
        classRunToUpdate.setClassRunStartTime(LocalTime.parse(classRunDTORequest.getClassRunStartTime(), timeFormatter));
        classRunToUpdate.setClassRunEndTime(LocalTime.parse(classRunDTORequest.getClassRunEndTime(), timeFormatter));
        classRunRepository.save(classRunToUpdate);
        return classRunToUpdate;
    }

    @Override
    public List<ClassRunDTO> findClassRunsFromCourseId(Long courseId) {
        List<ClassRun> classRuns = classRunRepository.findClassRunsFromCourseId(courseId);
        List<ClassRunDTO> classRunDTOs = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
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
                    classRun.getClassRunDescription(),
                    classRun.getClassRunStartTime().format(timeFormatter),
                    classRun.getClassRunEndTime().format(timeFormatter));
            classRunDTO.setClassEvents(classRun.getEvents());
            classRunDTOs.add(classRunDTO);
        }
        return classRunDTOs;
    }

    @Override
    public List<ClassRunDTO> findClassRunsFromInstructorId(Long instructorId) {
        List<ClassRun> classRuns = classRunRepository.findClassRunsFromInstructorId(instructorId);

        List<ClassRunDTO> classRunDTOs = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (ClassRun classRun : classRuns) {
            ClassRunDTO classRunDTO = new ClassRunDTO(
                    classRun.getClassRunId(),
                    classRun.getClassRunStart().format(formatter),
                    classRun.getClassRunEnd().format(formatter),
                    classRun.getMinClassSize(),
                    classRun.getMaximumCapacity(),
                    classRun.getClassRunDaysOfTheWeek(),
                    null,
                    null,
                    classRun.getInstructor().getUsername(),
                    null,
                    classRun.getClassRunName(),
                    classRun.getClassRunDescription(),
                    classRun.getClassRunStartTime().format(timeFormatter),
                    classRun.getClassRunEndTime().format(timeFormatter));
            classRunDTO.setClassEvents(classRun.getEvents());
            classRunDTOs.add(classRunDTO);
        }
        return classRunDTOs;
    }

//    @Override
//    public List<ClassRunDTO> findClassRunsFromLearnerId(Long learnerId) {
//        List<ClassRun> classRuns = classRunRepository.findClassRunsFromLearnerId(learnerId);
//
//        List<ClassRunDTO> classRunDTOs = new ArrayList<>();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//        for (ClassRun classRun : classRuns) {
//            ClassRunDTO classRunDTO = new ClassRunDTO(
//                    classRun.getClassRunId(),
//                    classRun.getClassRunStart().format(formatter),
//                    classRun.getClassRunEnd().format(formatter),
//                    classRun.getMinClassSize(),
//                    classRun.getMaximumCapacity(),
//                    classRun.getClassRunDaysOfTheWeek(),
//                    null,
//                    null,
//                    classRun.getInstructor().getUsername(),
//                    null,
//                    classRun.getClassRunName(),
//                    classRun.getClassRunDescription(),
//                    classRun.getClassRunStartTime().format(timeFormatter),
//                    classRun.getClassRunEndTime().format(timeFormatter));
//            classRunDTO.setClassEvents(classRun.getEvents());
//            classRunDTOs.add(classRunDTO);
//        }
//        return classRunDTOs;
//    }

    @Override
    public List<Event> generateClassEventsFromClassRunId(Long classRunId) {
        List<Event> newEvents = new ArrayList<>();
        ClassRun classRun = classRunRepository.findById(classRunId).get();
//        HashMap<Integer, DayOfWeek> numToDateOfWeekMap = new HashMap<>();
//        numToDateOfWeekMap.put(0, DayOfWeek.SUNDAY);
//        numToDateOfWeekMap.put(1, DayOfWeek.MONDAY);
//        numToDateOfWeekMap.put(2, DayOfWeek.TUESDAY);
//        numToDateOfWeekMap.put(3, DayOfWeek.WEDNESDAY);
//        numToDateOfWeekMap.put(4, DayOfWeek.THURSDAY);
//        numToDateOfWeekMap.put(5, DayOfWeek.FRIDAY);
//        numToDateOfWeekMap.put(6, DayOfWeek.SATURDAY);

        Set<DayOfWeek> daysOfWeekSet = new HashSet<>();
        //0 in classRunDaysOfTheWeek means sunday, 1 means monday and so on till 6 means saturday
        for (Integer dayOfWeekInInteger : classRun.getClassRunDaysOfTheWeek()) {
            if (dayOfWeekInInteger == 0) {
                daysOfWeekSet.add(DayOfWeek.SUNDAY);
            } else if (dayOfWeekInInteger == 1) {
                daysOfWeekSet.add(DayOfWeek.MONDAY);
            } else if (dayOfWeekInInteger == 2) {
                daysOfWeekSet.add(DayOfWeek.TUESDAY);
            } else if (dayOfWeekInInteger == 3) {
                daysOfWeekSet.add(DayOfWeek.WEDNESDAY);
            } else if (dayOfWeekInInteger == 4) {
                daysOfWeekSet.add(DayOfWeek.THURSDAY);
            } else if (dayOfWeekInInteger == 5) {
                daysOfWeekSet.add(DayOfWeek.FRIDAY);
            } else if (dayOfWeekInInteger == 6) {
                daysOfWeekSet.add(DayOfWeek.SATURDAY);
            }
        }

        //check if its occuring weekly
        if (classRun.getRecurringEnum() == RecurringEnum.WEEKLY) {
            //initiate a pointer at the start date
            LocalDate localDatePointer = classRun.getClassRunStart();
            while (localDatePointer.isBefore(classRun.getClassRunEnd())) {
                if (daysOfWeekSet.contains(localDatePointer.getDayOfWeek())) {
                    Event newEvent = new Event();
                    newEvent.setClassRun(classRun);
                    newEvent.setTitle(classRun.getClassRunName() + " " + "Class Event");
                    newEvent.setNotes(classRun.getClassRunName() + " " + "Class Event");
                    LocalDateTime startLocalDateTime = LocalDateTime.of(localDatePointer, classRun.getClassRunStartTime());
                    LocalDateTime endLocalDateTime = LocalDateTime.of(localDatePointer, classRun.getClassRunEndTime());
                    newEvent.setStartDate(Date.from(startLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                    newEvent.setEndDate(Date.from(endLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                    newEvent.setAllDay(false);
                    eventService.saveEvent(newEvent);
                    newEvents.add(newEvent);

                    classRun.getEvents().add(newEvent);
                    saveClassRun(classRun);
                }
                localDatePointer = localDatePointer.plusDays(1);
            }
        } else if (classRun.getRecurringEnum() == RecurringEnum.ALTERNATE) {
            Integer weekNum = 0;
            Integer daysCount = 0;
            LocalDate localDatePointer = classRun.getClassRunStart();
            while (localDatePointer.isBefore(classRun.getClassRunEnd())) {
                if (daysCount != 0 && daysCount % 7 == 0) {
                    localDatePointer = localDatePointer.plusDays(7);
                }
                if (daysOfWeekSet.contains(localDatePointer.getDayOfWeek())) {
                    Event newEvent = new Event();
                    newEvent.setClassRun(classRun);
                    newEvent.setTitle(classRun.getClassRunName() + " " + "Class Event");
                    newEvent.setNotes(classRun.getClassRunName() + " " + "Class Event");
                    LocalDateTime startLocalDateTime = LocalDateTime.of(localDatePointer, classRun.getClassRunStartTime());
                    LocalDateTime endLocalDateTime = LocalDateTime.of(localDatePointer, classRun.getClassRunEndTime());
                    newEvent.setStartDate(Date.from(startLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                    newEvent.setEndDate(Date.from(endLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                    newEvent.setAllDay(false);
                    eventService.saveEvent(newEvent);
                    newEvents.add(newEvent);

                    classRun.getEvents().add(newEvent);
                    saveClassRun(classRun);
                }
                localDatePointer = localDatePointer.plusDays(1);
                daysCount++;
            }
        }
        return newEvents;
    }

    @Override
    public void deleteClassEvent(Long id) throws EventNotFoundException {
        Event classEventToDelete = eventService.getEventById(id);
        ClassRun classRun = classEventToDelete.getClassRun();
        classRun.getEvents().remove(classEventToDelete);
        classRunRepository.save(classRun);
//        classEventToDelete.setClassRun(null);
//        eventService.deleteEvent(id);
    }

    @Override
    public Event addClassEvent(Long classRunId, Event eventRequest) throws EventNotFoundException {
        ClassRun classRun = classRunRepository.findById(classRunId).get();
        classRun.getEvents().add(eventRequest);
        eventRequest.setClassRun(classRun);
        eventService.saveEvent(eventRequest);
        classRunRepository.save(classRun);
        return eventRequest;
//        classEventToDelete.setClassRun(null);
//        eventService.deleteEvent(id);
    }
}
