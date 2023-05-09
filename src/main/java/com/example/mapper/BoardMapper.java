package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.example.dto.Board;

@Mapper
public interface BoardMapper {
    
    // 글 작성하기
	@Insert({ " INSERT INTO BOARD(TITLE, CONTENT, WRITER)  VALUES(#{obj.title},#{obj.content},#{obj.writer}) " })
    public int insertBoardOne(@Param("obj") Board obj);

    // 전체 목록 조회
	@Select({ " SELECT b.* FROM board b ORDER BY no DESC " })
	public List<Board> selectBoardList();

    // 게시글 1개 조회
	@Select({ " SELECT b.* FROM BOARD b WHERE NO = #{no} " })
	public Board selectBoardOne(@Param("no") long no);

    // 게시글 1개 수정
    // sql문이 없음 => resources/mappers/파일명Mapper.xml
    public int updateBoardOne(Board obj); 

    // 게시글 1개 삭제
    // sql문지 없음 => resources/mappers/파일명Mapper.xml
    public int deleteBoardOne(long no);

    // 조회수증가(글번호가 오면 해당 글번호 조회수만 1증가)
    public int updateBoardHit(long no); 


}
