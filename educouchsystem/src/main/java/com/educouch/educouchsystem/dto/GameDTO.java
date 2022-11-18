package com.educouch.educouchsystem.dto;

public class GameDTO {

    private Long gameId;
    private String gameDescription;
    private Integer numOfQuestions;
    private String gameType;

    public GameDTO() {
    }

    public GameDTO(Long gameId, String gameDescription, Integer numOfQuestions, String gameType) {
        this.gameId = gameId;
        this.gameDescription = gameDescription;
        this.numOfQuestions = numOfQuestions;
        this.gameType = gameType;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public Integer getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(Integer numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
