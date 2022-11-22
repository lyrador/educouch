package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Gallery;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.CourseRepository;
import com.educouch.educouchsystem.repository.GalleryRepository;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import org.apache.catalina.User;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//to annotate that it is a service class
@Service
public class LearnerServiceImpl implements LearnerService {

    //this annotation injects the student repo into the service class
    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private GalleryRepository galleryRepository;


    @Override
    public Learner saveLearner(Learner learner) {
        Gallery gallery = new Gallery();
        gallery = galleryRepository.save(gallery);
        learner = learnerRepository.save(learner);

        //connect learner and gallery
        learner.setGallery(gallery);
        gallery.setLearner(learner);

        galleryRepository.save(gallery);
        learner = learnerRepository.save(learner);

        return learner;
    }

    public Learner saveLearnerWithoutGallery(Learner learner) {
        learner = learnerRepository.save(learner);
        return learner;
    }

    @Override
    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();

    }

    @Override
    public List<Learner> getAllKidsLearner() {
        List<Learner> listOfAllLearners = getAllLearners();
        List<Learner> kidsLearner = new ArrayList<>();
        for(Learner l: listOfAllLearners) {
            if(l.getKid()) {
                kidsLearner.add(l);
            }
        }
        return kidsLearner;
    }

    @Override
    public Learner getLearnerById(Long learnerId) {
        return learnerRepository.findById(learnerId).get();
    }

    @Override
    public Learner findLearnerByUsername(String username) throws UsernameNotFoundException {
        Learner learner = learnerRepository.findByUsername(username);
        if(learner != null) {
            return learner;
        }
        throw new UsernameNotFoundException("Username not found");
    }

    @Override
    public Learner findLearnerByUsernameNonException(String username) throws UsernameNotFoundException {
        Learner learner = learnerRepository.findByUsername(username);
        return learner;
    }

    @Override
    public void deleteLearner(Long id) {
        learnerRepository.deleteById(id);
    }

    @Override
    public Learner updateLearner(Learner learner) throws InvalidLoginCredentialsException{
        Learner updateLearner = learnerRepository.findById(learner.getLearnerId()).get();
        if(updateLearner.getUsername().equals(learner.getUsername()) && updateLearner.getPassword().equals(learner.getPassword())) {
            updateLearner.setActive(learner.getActive());
//            updateLearner.setAddress(learner.getAddress());
            updateLearner.setEmail(learner.getEmail());
            updateLearner.setName(learner.getName());
            updateLearner.setProfilePictureURL(learner.getProfilePictureURL());
            learnerRepository.save(updateLearner);
            return updateLearner;
        }
        throw new InvalidLoginCredentialsException("Could not update as Lms Admin object to update has a different password");

    }


}
