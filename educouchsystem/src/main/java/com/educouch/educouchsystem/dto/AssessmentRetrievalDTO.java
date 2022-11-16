package com.educouch.educouchsystem.dto;

import java.util.ArrayList;
import java.util.List;

public class AssessmentRetrievalDTO {

    private Double maxAssignmentPoints;

    private List<AssessmentDTO> listOfAssessments;

    public AssessmentRetrievalDTO() {
        listOfAssessments = new ArrayList<>();
    }

    public AssessmentRetrievalDTO(Double maxAssignmentPoints, List<AssessmentDTO> listOfAssessments) {
        this.maxAssignmentPoints = maxAssignmentPoints;
        this.listOfAssessments = listOfAssessments;
    }

    public Double getMaxAssignmentPoints() {
        return maxAssignmentPoints;
    }

    public void setMaxAssignmentPoints(Double maxAssignmentPoints) {
        this.maxAssignmentPoints = maxAssignmentPoints;
    }

    public List<AssessmentDTO> getListOfAssessments() {
        return listOfAssessments;
    }

    public void setListOfAssessments(List<AssessmentDTO> listOfAssessments) {
        this.listOfAssessments = listOfAssessments;
    }
}
