package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
}
