package com.toyproject.board.repository;

import com.toyproject.board.dto.BoardDTO;
import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.dto.SportBoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class SportBoardRepository {
    private final SqlSessionTemplate sql;
    /*public void save(BoardDTO boardDto) {
        sql.insert("Board.save", boardDto); //Board 는 mapper.xml의 namespace 이다. insert의 id: save는 쿼리문을 담고있는 태그
    }*/

    public SportBoardDTO save(SportBoardDTO sportBoardDTO) {
        sql.insert("SportBoard.save", sportBoardDTO);
        return sportBoardDTO;
    }

    public List<SportBoardDTO> sportFindAll() {
        return sql.selectList("SportBoard.sportFindAll");
    }

    public void updateHits(Long id) {
        sql.update("SportBoard.updateHits", id);
    }

    public SportBoardDTO findById(Long id) {
        return sql.selectOne("SportBoard.findById", id);
    }

    public void update(SportBoardDTO sportBoardDTO) {
        sql.update("SportBoard.update", sportBoardDTO);
    }

    public void delete(Long id) {
        sql.delete("SportBoard.delete", id);
    }

    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("SportBoard.saveFile", boardFileDTO);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("SportBoard.findFile", id);
    }

    public Integer findByIdCount(String id) {
        return sql.selectOne("SportBoard.findByIdCount", id);
    }

    public List<SportBoardDTO> pagingList(Map<String, Integer> pagingParams) {
        return sql.selectList("SportBoard.pagingList", pagingParams);
    }

    public int boardCount() {
        return sql.selectOne("SportBoard.sportBoardCount");
    }

    public List<SportBoardDTO> searchByKeyword(String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        return sql.selectList("SportBoard.searchByKeyword", params);
    }
}
