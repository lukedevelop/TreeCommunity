package com.toyproject.board.repository;

import com.toyproject.board.dto.SportReplyDTO;
import com.toyproject.board.dto.TravelReplyDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TravelReplyRepository {

    private final SqlSessionTemplate sql;



    public void save(TravelReplyDTO travelReplyDTO) {
        sql.insert("travelReply.save", travelReplyDTO);
    }


    public List<TravelReplyDTO> findAll(Long bno) {
        return sql.selectList("travelReply.findAll",bno);
    }


    public TravelReplyDTO findById(Long bno) {
        return sql.selectOne("travelReply.findById", bno);
    }

    public void delete(Long id) {
        sql.delete("travelReply.delete", id);
    }


}

