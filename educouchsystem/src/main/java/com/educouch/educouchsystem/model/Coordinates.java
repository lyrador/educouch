package com.educouch.educouchsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coordinatesId;
    private Long x0;
    private Long y0;
    private Long x1;
    private Long y1;
    private String color;
    private String username;
    private Long lineWidth;
    private String instrument;

    public Coordinates() {
    }

    public Coordinates(Long x0, Long y0, Long x1, Long y1, String color, String username, Long lineWidth, String instrument) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.username = username;
        this.lineWidth = lineWidth;
        this.instrument = instrument;
    }

    public Long getX0() {
        return x0;
    }

    public void setX0(Long x0) {
        this.x0 = x0;
    }

    public Long getY0() {
        return y0;
    }

    public void setY0(Long y0) {
        this.y0 = y0;
    }

    public Long getX1() {
        return x1;
    }

    public void setX1(Long x1) {
        this.x1 = x1;
    }

    public Long getY1() {
        return y1;
    }

    public void setY1(Long y1) {
        this.y1 = y1;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Long lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Long getCoordinatesId() {
        return coordinatesId;
    }

    public void setCoordinatesId(Long coordinatesId) {
        this.coordinatesId = coordinatesId;
    }
}
