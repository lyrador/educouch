package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.InteractiveChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractiveChapterRepository extends JpaRepository<InteractiveChapter, Long> {
}
