package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.data.model.Option;
import com.educouch.educouchsystem.data.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}