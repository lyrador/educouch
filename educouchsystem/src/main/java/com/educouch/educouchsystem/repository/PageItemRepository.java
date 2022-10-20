package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.PageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageItemRepository extends JpaRepository<PageItem, Long> {
}
