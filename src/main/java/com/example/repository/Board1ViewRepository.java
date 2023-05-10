package com.example.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board1View;

@Repository
public interface Board1ViewRepository extends JpaRepository<Board1View, Long>{
    
    List<Board1View> findAllByOrderByNoDesc();

    List<Board1View> findByNoAndTitleIgnoreCaseOrderByNoDesc(long no, String title);

    List<Board1View> findByNoOrTitleIgnoreCaseOrderByNoDesc(long no, String title);

    List<Board1View> findByNoIn(long[] no);

    List<Board1View> findByTitleIn(String[] title);
}
