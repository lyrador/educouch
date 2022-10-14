package com.educouch.educouchsystem.data.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String name;
    private String description;
    private String address;
    private String password;

    @ManyToMany
    @JoinTable(joinColumns=@JoinColumn(name="roomId"))
    private List<Learner> participants;

    @ManyToMany
    @JoinTable(joinColumns=@JoinColumn(name="roomId"))
    private List<Instructor> organizers;



    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "roomId")
    private List<Drawing> drawings;



    public Room() {
        this.participants = new ArrayList<>();
        this.drawings = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.password = generateRandomPassword();
    }

    public Room(String name, String description) {
        new Room();
        this.name = name;
        this.description = description;
    }

    private String generateRandomPassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;

    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Learner> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Learner> participants) {
        this.participants = participants;
    }

    public List<Drawing> getDrawings() {
        return drawings;
    }

    public void setDrawings(List<Drawing> drawings) {
        this.drawings = drawings;
    }

    public List<Instructor> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<Instructor> organizers) {
        this.organizers = organizers;
    }
}