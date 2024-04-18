package com.toyproject.board.service;

import com.toyproject.board.dto.*;
import com.toyproject.board.repository.TravelBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TravelBoardService {
    private final TravelBoardRepository travelBoardRepository;

    public void save(TravelBoardDTO travelBoardDTO) throws IOException {
        if (travelBoardDTO.getBoardFile().get(0).isEmpty()) {
            //파일 없다.
            travelBoardDTO.setFileAttached(0);
            travelBoardRepository.save(travelBoardDTO);
        } else {
            //파일 있다.
            travelBoardDTO.setFileAttached(1);
            //게시글 저장 후 id값 활용을 위해 리턴 받음.
            TravelBoardDTO saveBoard = travelBoardRepository.save(travelBoardDTO);
            //파일만 따로 가져오기
//            MultipartFile boardFile = boardDTO.getBoardFile();  : 단일 파일

            //다중파일 가져오기
            for (MultipartFile boardFile : travelBoardDTO.getBoardFile()) {

                //파일 이름 가져오기
                String originalFilename = boardFile.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);
                //저장용 이름 만들기
                System.out.println(System.currentTimeMillis());
                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                System.out.println("storedFileName = " + storedFileName);
                //BoardFileDTO 세팅
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(saveBoard.getId());
                //파일 저장용 폴더에 파일 저장 처리
                String savePath = "C:/Users/whgml/spring_upload_files/" + storedFileName;
                boardFile.transferTo(new File(savePath)); // savePath 경로에 파일이름으로 넘김.
                //board_file_table 저장 처리
                travelBoardRepository.saveFile(boardFileDTO);
            }

        }
    }

    public List<TravelBoardDTO> travelFindAll() {
        return travelBoardRepository.travelFindAll();
    }

    public int findByIdCount(String id) {
        return travelBoardRepository.findByIdCount(id);
    }

    public void updateHits(Long id) {
        travelBoardRepository.updateHits(id);
    }

    public TravelBoardDTO findById(Long id) {
        return travelBoardRepository.findById(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return travelBoardRepository.findFile(id);
    }

    public void update(TravelBoardDTO travelBoardDTO) {
        travelBoardRepository.update(travelBoardDTO);
    }

    public void delete(Long id) {
        travelBoardRepository.delete(id);
    }

    int pageLimit = 5; // 한 페이지당 보여줄 글 갯수
    int blockLimit = 3; // 하단에 보여줄 페이지 번호 갯수
    public List<TravelBoardDTO> pagingList(int page) {
        /*
        1페이지당 보여지는 글 갯수 3
            1page => 0
            2page => 3
            3page => 6
         */
        int pagingStart = (page - 1) * pageLimit;
        Map<String, Integer> pagingParams = new HashMap<>();
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);
        List<TravelBoardDTO> pagingList = travelBoardRepository.pagingList(pagingParams);

        return pagingList;
    }

    public PageDTO pagingParam(int page) {
        // 전체 글 갯수 조회
        int boardCount = travelBoardRepository.boardCount();
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

    public List<TravelBoardDTO> searchByKeyword(String keyword) {
        return travelBoardRepository.searchByKeyword(keyword);
    }
}
