package com.educouch.educouchsystem.model;
import com.educouch.educouchsystem.util.enumeration.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organisationId;
    @Column(nullable = false)
    private String organisationName;

    @OneToOne
    @JoinColumn(name = "organisationAdminId")
    private OrganisationAdmin organisationAdmin;
    @OneToMany(mappedBy = "organisation")
    private List<Instructor> instructors;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organisation")
    private List<Course> courses;

    @Column(nullable = false)
    private String orgDescription;

    @Column(nullable = false)
    private String paymentAcc;
    private BigDecimal orgBalance;

    private PaymentStatusEnum paymentStatus;

    public Organisation() {
        this.instructors = new ArrayList<>();
        this.orgBalance = new BigDecimal(0);
        this.paymentStatus = PaymentStatusEnum.PAID;
    }

    public Organisation(String organisationName, String orgDescription, String paymentAcc) {
        this();
        this.orgDescription = orgDescription;
        this.paymentAcc = paymentAcc;
        this.organisationName = organisationName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getPaymentAcc() {
        return paymentAcc;
    }

    public void setPaymentAcc(String paymentAcc) {
        this.paymentAcc = paymentAcc;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }
    @JsonBackReference
    public OrganisationAdmin getOrganisationAdmin() {
        return organisationAdmin;
    }

    public void setOrganisationAdmin(OrganisationAdmin organisationAdmin) {
        this.organisationAdmin = organisationAdmin;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    @JsonIgnore
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public BigDecimal getOrgBalance() {
        return orgBalance;
    }

    public void setOrgBalance(BigDecimal orgBalance) {
        this.orgBalance = orgBalance;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
