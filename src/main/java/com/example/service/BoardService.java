package com.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.Board;

@Service
public interface BoardService {
    // 게시글등록
    public int insertBoardOne(@Param("obj") Board obj);

    // 게시글목록조회
    public List<Board> selectBoardList();

    // 게시글1개조회
    public Board selectBoardOne(@Param("no") long no);

    // 게시글수정(xml구문을 사용한 mapper)
    public int updateBoardOne(Board board);

    // 게시글삭제(xml구문을 사용한 mapper)
    public int deleteBoardOne(long no);
}
