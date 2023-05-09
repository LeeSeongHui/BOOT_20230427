package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper bMapper;


    @Override
    public int insertBoardOne(Board obj) {
        try {
            return bMapper.insertBoardOne(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'insertBoardOne'");
        }
    }


    @Override
    public List<Board> selectBoardList() {
        try {
            return bMapper.selectBoardList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }


    @Override
    public Board selectBoardOne(long no) {
        try {
            return bMapper.selectBoardOne(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }


    @Override
    public int updateBoardOne(Board board) {
        try {
            return bMapper.updateBoardOne(board);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public int deleteBoardOne(long no) {
        try {
            return bMapper.deleteBoardOne(no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    
    
}
