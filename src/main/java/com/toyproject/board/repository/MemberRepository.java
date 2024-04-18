package com.toyproject.board.repository;

import com.toyproject.board.dto.MemberDTO;
import com.toyproject.board.dto.MemberFileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final SqlSessionTemplate sql;

    public int save(MemberDTO memberDTO) {
        return sql.insert("Member.save", memberDTO);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        return sql.selectOne("Member.login", memberDTO);
    }


    public MemberDTO idChk(MemberDTO memberDTO) {
        return sql.selectOne("Member.idChk", memberDTO);
    }


    public MemberDTO findById(Long id) {
        return sql.selectOne("Member.findById", id);
    }

    public void update(MemberDTO memberDTO) {
        sql.update("Member.update", memberDTO);
    }

    public List<MemberFileDTO> findFile(Long memberId) {
        return sql.selectList("Member.findFile", memberId);
    }

    public void saveFile(MemberFileDTO memberFileDTO) {
        sql.insert("Member.saveFile", memberFileDTO);
    }

    public List<MemberDTO> findAll() {
        return sql.selectList("Member.findAll");
    }

    public void delete(Long memberId) {
        sql.delete("Member.fileDelete", memberId);
    }
}
