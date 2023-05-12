package com.example.entity.library;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "LIBRARY2")
@SequenceGenerator(name = "SEQ_LIBRARY2_CODE", sequenceName = "SEQ_LIBRARY2_CODE", initialValue = 1, allocationSize = 1)
public class Library2 {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LIBRARY2_CODE")
    @Column(name = "CODE")
    private BigInteger code;

    private String name;

    private String address;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    

    @ToString.Exclude
    @OneToMany(mappedBy = "libcode", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Admin2> admin2 = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "libcode", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Book2> book2 = new ArrayList<>();

}
