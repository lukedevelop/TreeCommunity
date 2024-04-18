package com.toyproject.board.repository;

import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.dto.EtcBoardDTO;
import com.toyproject.board.dto.SportBoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EtcBoardRepository {
    private final SqlSessionTemplate sql;

    public EtcBoardDTO save(EtcBoardDTO etcBoardDTO) {
        sql.insert("EtcBoard.save", etcBoardDTO);
        return etcBoardDTO;
    }

    public List<EtcBoardDTO> etcFindAll() {
        return sql.selectList("EtcBoard.etcFindAll");
    }

    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("EtcBoard.saveFile", boardFileDTO);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("EtcBoard.findFile", id);
    }

    public int findByIdCount(String id) {
        return sql.selectOne("EtcBoard.findByIdCount", id);
    }

    public void updateHits(Long id) {
        sql.update("EtcBoard.updateHits", id);
    }

    public EtcBoardDTO findById(Long id) {
        return sql.selectOne("EtcBoard.findById", id);
    }

    public void update(EtcBoardDTO etcBoardDTO) {
        sql.update("EtcBoard.update", etcBoardDTO);
    }

    public void delete(Long id) {
        sql.delete("EtcBoard.delete", id);
    }


    public List<EtcBoardDTO> pagingList(Map<String, Integer> pagingParams) {
        return sql.selectList("EtcBoard.pagingList", pagingParams);
    }

    public int boardCount() {
        return sql.selectOne("EtcBoard.etcBoardCount");
    }

    public List<EtcBoardDTO> searchByKeyword(String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        return sql.selectList("EtcBoard.searchByKeyword", params);
    }

/*    public List<EtcBoardDTO> search(String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        return sql.selectList("EtcBoard.search", params);
    }*/
}
