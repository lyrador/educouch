package com.educouch.educouchsystem.dto;

import java.util.List;

public class EditCourseTagsDTO {

    private Long courseId;
    private List<String> tagIds;

    public EditCourseTagsDTO(Long courseId, List<String> tagIds) {
        this.courseId = courseId;
        this.tagIds = tagIds;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }
}
