package com.educouch.educouchsystem.repository;


import com.educouch.educouchsystem.data.model.Drawing;
import com.educouch.educouchsystem.data.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawingRepository extends JpaRepository<Drawing, Long> {
}