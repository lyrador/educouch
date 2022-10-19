package com.educouch.educouchsystem.repository;


import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}