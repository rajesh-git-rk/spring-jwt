package com.security.spring_security_jwt.SpringBatch.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Employee")
public class Employ {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EMPID")
    private int id;
    @Column(name = "ENAME")
    private String empName;
    @Column(name = "EMAIL")
    private String empEmailId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmailId() {
        return empEmailId;
    }

    public void setEmpEmailId(String empEmailId) {
        this.empEmailId = empEmailId;
    }
}
