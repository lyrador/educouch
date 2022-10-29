package com.educouch.educouchsystem.dto;

public class ChapterToReorderDTO {
    private Long chapterId;
    private String chapterIndex;
    private String chapterTitle;

    public ChapterToReorderDTO() {
    }

    public ChapterToReorderDTO(Long chapterId, String chapterIndex, String chapterTitle) {
        this.chapterId = chapterId;
        this.chapterIndex = chapterIndex;
        this.chapterTitle = chapterTitle;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(String chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }
}
