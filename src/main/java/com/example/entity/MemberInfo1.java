package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBERINFO1")

public class MemberInfo1 {

    @Id
    @Column(name = "ID1")
    private String id1;

    @MapsId // 컬럼을 줄이고 ID1칼럼 하나만 생성(외래키 제약) // ID이면서도 외래키
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID1")
    private Member1 member1; 

    private String info;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;
}
