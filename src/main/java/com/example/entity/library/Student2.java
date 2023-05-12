package com.example.entity.library;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="STUDENT2")
public class Student2 {

    @Id
    private String email;
    
    private String name;
    
    private String phone;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name="REGDATE", updatable = false)
    private Date regdate;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "stdemail", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Checkout2> checkout2 = new ArrayList<>();
}

