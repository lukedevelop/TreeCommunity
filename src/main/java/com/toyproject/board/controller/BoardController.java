package com.toyproject.board.controller;

import com.toyproject.board.dto.*;
import com.toyproject.board.service.BoardService;
import com.toyproject.board.service.MemberService;
import com.toyproject.board.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;
    private final ReplyService replyService;

    @GetMapping("/save") // 주소. (게시글 작성 화면을 띄우기 위한 메서드)
    public String save(HttpSession session, Model model) {
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "save"; // return 할 화면 이름(html)
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDto, HttpServletRequest request) throws IOException {
        log.info("boardDto = " + boardDto);
        boardService.save(boardDto, request);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String findAll(Model model, HttpSession session,
                          @RequestParam(value = "page",required = false, defaultValue = "1") int page,
                          @RequestParam(value = "keyword", required = false) String keyword) {

        List<BoardDTO> pagingList;
        if (keyword != null && !keyword.isEmpty()) {
            pagingList = boardService.searchByKeyword(keyword);
        }else {
            pagingList = boardService.pagingList(page);
            PageDTO pageDTO = boardService.pagingParam(page);
            model.addAttribute("boardListPage", pagingList);
            model.addAttribute("paging", pageDTO);
            model.addAttribute("keyword", keyword);

        }
        model.addAttribute("boardList", pagingList);
        Integer byIdCount = boardService.findByIdCount((String) session.getAttribute("loginId"));
        model.addAttribute("findByIdCount", byIdCount);

        pagingList = boardService.pagingList(page);
        PageDTO pageDTO = boardService.pagingParam(page);
        model.addAttribute("boardListPage", pagingList);
        model.addAttribute("paging", pageDTO);
        model.addAttribute("keyword", keyword);

        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

/*        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        Integer byIdCount = boardService.findByIdCount((String) session.getAttribute("loginId"));
        model.addAttribute("findByIdCount", byIdCount);
        log.info("session.loginId : " + session.getAttribute("loginId") + "");

        log.info("boardDTOList = " + boardDTOList);*/
        return "list";
    }

    @GetMapping("/{id}") //id로 게시글 찾겠다.
    public String findById(@PathVariable("id") Long id, HttpSession session ,Model model) {
        //조회수 처리
        boardService.updateHits(id);

        //상세내용 가져오기
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        log.info("boardDTO = " + boardDTO);
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        //첨부파일이 있으면 내용을 가져오는 조건문
        if (boardDTO != null && boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

        List<ReplyDTO> replyDTOS = replyService.findAll(id);
        model.addAttribute("replyList", replyDTOS);

        return "detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, HttpSession session ,Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        model.addAttribute("board", boardDTO);

        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, HttpSession session ,Model model) {
        boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);

        MemberDTO memdto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", memdto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(memdto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        replyService.delete(id);
        boardService.delete(id);
        return "redirect:/list";
    }

    @PostMapping("/reply/save")
    public String replySave(ReplyDTO replyDTO) {
        replyService.save(replyDTO);
        return "redirect:/" + replyDTO.getBno();
    }

    @GetMapping("/reply/delete/{id}/{bno}")
    public String replyDelete(@PathVariable("id") Long id, @PathVariable("bno") Long bno) {
        replyService.delete(id);
        return "redirect:/" + bno;
    }

}
