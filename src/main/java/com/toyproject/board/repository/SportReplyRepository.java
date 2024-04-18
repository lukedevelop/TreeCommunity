package com.toyproject.board.repository;

import com.toyproject.board.dto.ReplyDTO;
import com.toyproject.board.dto.SportReplyDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SportReplyRepository {

    private final SqlSessionTemplate sql;



    public void save(SportReplyDTO sportReplyDTO) {
        sql.insert("sportReply.save", sportReplyDTO);
    }


    public List<SportReplyDTO> findAll(Long bno) {
        return sql.selectList("sportReply.findAll",bno);
    }


    public SportReplyDTO findById(Long bno) {
        return sql.selectOne("sportReply.findById", bno);
    }

    public void delete(Long id) {
        sql.delete("sportReply.delete", id);
    }


}

