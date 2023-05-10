package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, BigDecimal>{

    // select * from board order by no desc
    List<Board> findAllByOrderByNoDesc();

    // 검색어 타입에 따른 메소드 3개 만들기
    // select * from board where title likt '%' || ? || '%' order by no desc
    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title);
    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content);
    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer);

    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title, PageRequest pageRequest);
    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content, PageRequest pageRequest);
    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer, PageRequest pageRequest);

    long countByTitleIgnoreCaseContainingOrderByNoDesc(String title);
    long countByContentIgnoreCaseContainingOrderByNoDesc(String content);
    long countByWriterIgnoreCaseContainingOrderByNoDesc(String writer);
    
}
