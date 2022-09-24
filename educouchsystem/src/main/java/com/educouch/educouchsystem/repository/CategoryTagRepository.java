package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.CategoryTag;
import com.educouch.educouchsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryTagRepository extends JpaRepository<CategoryTag, Long> {

    @Query("SELECT c FROM CategoryTag c WHERE c.tagName=:catName")
    CategoryTag findCategoryTagByName(String catName);
}
