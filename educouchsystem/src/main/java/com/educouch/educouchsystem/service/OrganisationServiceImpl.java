package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.CourseStats;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.repository.CourseStatsRepository;
import com.educouch.educouchsystem.repository.InstructorRepository;
import com.educouch.educouchsystem.repository.OrganisationRepository;
import com.educouch.educouchsystem.util.enumeration.PaymentStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseStatsRepository courseStatsRepository;
    @Autowired
    private EducatorService educatorService;

    public Organisation findOrganisationById(Long Id) {

        Organisation managedOrganisation = organisationRepository.findOrganisationById(Id);
        return managedOrganisation;
    }

    public Organisation findOrganisationByOrganisationName(String organisationName) {
        return organisationRepository.findOrganisationByOrganisationName(organisationName);
    }

    public Organisation saveOrganisation(Organisation organisation) {

        return organisationRepository.save(organisation);
    }

    public Organisation instantiateOrganisation(OrganisationAdmin organisationAdmin, Organisation organisation) {

        //save orgAdmin and org
        educatorService.saveOrganisationAdmin(organisationAdmin);
        saveOrganisation(organisation);

        Organisation managedOrganisation = findOrganisationById(organisation.getOrganisationId());
        OrganisationAdmin managedOrganisationAdmin = educatorService.findOrganisationAdminById(organisationAdmin.getOrganisationAdminId());

        managedOrganisation.setOrganisationAdmin(managedOrganisationAdmin);
        managedOrganisationAdmin.setOrganisation(managedOrganisation);

        saveOrganisation(managedOrganisation);
        educatorService.saveOrganisationAdmin(managedOrganisationAdmin);

        return organisation;
    }

    public List<Instructor> findAllInstructors(String organisationId) {

            List<Instructor> instructors = instructorRepository.findAllInstructorsInOrganisation(Long.parseLong(organisationId));
            return instructors;

    }

//    @Override
//    public void dayOneOrgScheduling() {
//        List<Organisation> list = findAllOrganisation();
//        for(Organisation org : list) {
//            if(!(org.getOrgBalance().compareTo(BigDecimal.ZERO) == 0)) {
//                org.setPaymentStatus(PaymentStatusEnum.DUE);
//                organisationRepository.save(org);
//            }
//        }
//    }
//
//    @Override
//    public void daySevenOrgScheduling() {
//        List<Organisation> list = findAllDue();
//        for(Organisation org: list) {
//            org.setPaymentStatus(PaymentStatusEnum.OVERDUE);
//            organisationRepository.save(org);
//        }
//
//    }

//    @Override
//    public List<Organisation> findAllDue() {
//        return organisationRepository.findAllDueOrganisations();
//    }

    @Override
    public CourseStats createCourseStats(Long organisationId, Long courseId, String courseName, BigDecimal courseFee) {
        CourseStats newStats = new CourseStats(courseId,courseName,courseFee);
        CourseStats savedStats=courseStatsRepository.save(newStats);
        Organisation org = findOrganisationById(organisationId);
        org.getCourseStatsList().add(savedStats);
        organisationRepository.save(org);
        return savedStats;
    }

    @Override
    public void addLearner(Long courseId) {
        CourseStats stats = courseStatsRepository.findCourseStatsByCourseId(courseId);
        stats.setLearnerEnrolled(stats.getLearnerEnrolled()+1);
        stats.setTotalRevenue(stats.getCourseFee().multiply(new BigDecimal(stats.getLearnerEnrolled())));
        stats.setLmsCut(stats.getTotalRevenue().multiply(new BigDecimal("0.05")));
        stats.setNetRevenue(stats.getTotalRevenue().multiply(new BigDecimal("0.95")));
        courseStatsRepository.save(stats);
    }

    @Override
    public void courseStatsRefresh() {
        List<CourseStats> list = courseStatsRepository.findAll();
        for(CourseStats c : list) {
            c.setLearnerEnrolled(0);
            c.setTotalRevenue(new BigDecimal(0));
            c.setLmsCut(new BigDecimal(0));
            c.setNetRevenue(new BigDecimal(0));
            courseStatsRepository.save(c);
        }
    }

    //not tested
    public Instructor addInstructor(String organisationId, Instructor instructor) {

        Organisation managedOrganisation = findOrganisationById(Long.parseLong(organisationId));

        //save instructor
        instructor.setOrganisation(managedOrganisation);
        instructorRepository.save(instructor);

        Instructor managedInstructor = educatorService.findInstructorByUsername(instructor.getUsername());
        return managedInstructor;

    }

    public List<Organisation> findAllOrganisation() {
        return organisationRepository.findAll();
    }
}
