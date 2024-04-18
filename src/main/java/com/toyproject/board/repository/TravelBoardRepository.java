package com.toyproject.board.repository;

import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.dto.EtcBoardDTO;
import com.toyproject.board.dto.TravelBoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class TravelBoardRepository {
    private final SqlSessionTemplate sql;

    public TravelBoardDTO save(TravelBoardDTO travelBoardDTO) {
        sql.insert("TravelBoard.save", travelBoardDTO);
        return travelBoardDTO;
    }
    public List<TravelBoardDTO> travelFindAll() {
        return sql.selectList("TravelBoard.travelFindAll");
    }


    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("TravelBoard.saveFile", boardFileDTO);
    }

    public void updateHits(Long id) {
        sql.update("TravelBoard.updateHits", id);
    }

    public TravelBoardDTO findById(Long id) {
        return sql.selectOne("TravelBoard.findById", id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("TravelBoard.findFile", id);
    }

    public int findByIdCount(String id) {
        return sql.selectOne("TravelBoard.findByIdCount", id);
    }

    public void update(TravelBoardDTO travelBoardDTO) {
        sql.update("TravelBoard.update", travelBoardDTO);
    }

    public void delete(Long id) {
        sql.delete("TravelBoard.delete", id);
    }

    public List<TravelBoardDTO> pagingList(Map<String, Integer> pagingParams) {
        return sql.selectList("TravelBoard.pagingList", pagingParams);
    }

    public int boardCount() {
        return sql.selectOne("TravelBoard.travelBoardCount");
    }

    public List<TravelBoardDTO> searchByKeyword(String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        return sql.selectList("TravelBoard.searchByKeyword", params);
    }
}
