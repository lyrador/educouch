package com.educouch.educouchsystem.data.model;

import com.educouch.educouchsystem.data.model.Coordinates;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Drawing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drawingId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "drawingId")
    private List<Coordinates> line;

    public Drawing() {
        this.line = new ArrayList<>();
    }

    public Drawing(List<Coordinates> line) {
        this.line = line;
    }

    public List<Coordinates> getLine() {
        return line;
    }

    public void setLine(List<Coordinates> line) {
        this.line = line;
    }
}
