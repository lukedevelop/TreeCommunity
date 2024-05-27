package com.toyproject.board.service;

import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.dto.EtcBoardDTO;
import com.toyproject.board.dto.PageDTO;
import com.toyproject.board.dto.SportBoardDTO;
import com.toyproject.board.repository.BoardRepository;
import com.toyproject.board.repository.EtcBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtcBoardService {
    private final EtcBoardRepository etcBoardRepository;
    private final BoardRepository boardRepository;

    public void save(EtcBoardDTO etcBoardDTO) throws IOException {
        if (etcBoardDTO.getBoardFile().get(0).isEmpty()) {
            etcBoardDTO.setFileAttached(0);
            etcBoardRepository.save(etcBoardDTO);
        } else {
            etcBoardDTO.setFileAttached(1);
            EtcBoardDTO saveBoard = etcBoardRepository.save(etcBoardDTO);

            for (MultipartFile boardFile : etcBoardDTO.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename();
                log.info("originalFilename = " + originalFilename);

                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                log.info("storedFileName = " + storedFileName);

                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(saveBoard.getId());

                //String savePath = "C:/Users/whgml/spring_upload_files/" + storedFileName;
                String savePath = "/treecommunity/tomcat/webapps/ROOT/WEB-INF/classes/saveimg/" + storedFileName;
                boardFile.transferTo(new File(savePath));
                etcBoardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<EtcBoardDTO> etcFindAll() {
        return etcBoardRepository.etcFindAll();
    }

    public int findByIdCount(String id) {
        return etcBoardRepository.findByIdCount(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return etcBoardRepository.findFile(id);
    }

    public void updateHits(Long id) {
        etcBoardRepository.updateHits(id);
    }

    public EtcBoardDTO findById(Long id) {
        return etcBoardRepository.findById(id);
    }

    public void update(EtcBoardDTO etcBoardDTO) {
        etcBoardRepository.update(etcBoardDTO);
    }

    public void delete(Long id) {
        etcBoardRepository.delete(id);
    }




    int pageLimit = 5; // 한 페이지당 보여줄 글 갯수
    int blockLimit = 3; // 하단에 보여줄 페이지 번호 갯수
    public List<EtcBoardDTO> pagingList(int page) {

/*        1페이지당 보여지는 글 갯수 3
            1page => 0
            2page => 3
            3page => 6*/

        int pagingStart = (page - 1) * pageLimit;
        Map<String, Integer> pagingParams = new HashMap<>();
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);
        List<EtcBoardDTO> pagingList = etcBoardRepository.pagingList(pagingParams);

        return pagingList;
    }

    public PageDTO pagingParam(int page) {
        // 전체 글 갯수 조회
        int boardCount = etcBoardRepository.boardCount();
        // 전체 페이지 갯수 계산(10/3=3.33333 => 4)
        int maxPage = (int) (Math.ceil((double) boardCount / pageLimit));
        // 시작 페이지 값 계산(1, 4, 7, 10, ~~~~)
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        // 끝 페이지 값 계산(3, 6, 9, 12, ~~~~)
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        return pageDTO;
    }

    public List<EtcBoardDTO> searchByKeyword(String keyword) {
        return etcBoardRepository.searchByKeyword(keyword);
    }

/*    public List<EtcBoardDTO> search(String keyword) {
        return etcBoardRepository.search(keyword);
    }*/
}
