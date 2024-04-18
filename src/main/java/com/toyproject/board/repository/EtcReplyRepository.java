package com.toyproject.board.repository;

import com.toyproject.board.dto.EtcReplyDTO;
import com.toyproject.board.dto.SportReplyDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EtcReplyRepository {

    private final SqlSessionTemplate sql;



    public void save(EtcReplyDTO etcReplyDTO) {
        sql.insert("etcReply.save", etcReplyDTO);
    }


    public List<EtcReplyDTO> findAll(Long bno) {
        return sql.selectList("etcReply.findAll",bno);
    }


    public EtcReplyDTO findById(Long bno) {
        return sql.selectOne("etcReply.findById", bno);
    }

    public void delete(Long id) {
        sql.delete("etcReply.delete", id);
    }


}

