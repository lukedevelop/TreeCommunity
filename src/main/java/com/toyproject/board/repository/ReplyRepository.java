package com.toyproject.board.repository;

import com.toyproject.board.dto.ReplyBoardDTO;
import com.toyproject.board.dto.ReplyDTO;
import com.toyproject.board.dto.SportReplyDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {

    private final SqlSessionTemplate sql;



    public void save(ReplyDTO replyDTO) {
        sql.insert("Reply.save", replyDTO);
    }


    public List<ReplyDTO> findAll(Long bno) {
        return sql.selectList("Reply.findAll",bno);
    }


    public ReplyDTO findById(Long bno) {
        return sql.selectOne("Reply.findById", bno);
    }

    public void delete(Long id) {
        sql.delete("Reply.delete", id);
    }


}

