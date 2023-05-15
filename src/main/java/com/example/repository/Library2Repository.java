package com.example.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.library.Library2;

@Repository
public interface Library2Repository extends JpaRepository<Library2, BigInteger>{

    // select * from 테이블명 where 컬럼=?
    //                        findBy+컬럼

    // select * from 테이블명 order by name asc
    List<Library2> findAllByOrderByNameAsc();
    
    // 연락처별 내림차순 정렬
    List<Library2> findAllByOrderByPhoneDesc();
}
