package com.toyproject.board.controller;

import com.toyproject.board.dto.*;
import com.toyproject.board.service.MemberService;
import com.toyproject.board.service.SportBoardService;
import com.toyproject.board.service.SportReplyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SportBoardController {
    private final SportBoardService sportBoardService;
    private final SportReplyService sportReplyService;
    private final MemberService memberService;

    @GetMapping("/sportBoard/save")
    public String save(HttpSession session, Model model) {
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/sportboard/save";
    }

    @PostMapping("/sportBoard/save")
    public String save(SportBoardDTO sportBoardDTO) throws IOException {
        sportBoardService.save(sportBoardDTO);
        return "redirect:/sportBoard/list";
    }

    @GetMapping("/sportBoard/{id}")
    public String findById(@PathVariable("id") Long id, HttpSession session ,Model model) {

        sportBoardService.updateHits(id);

        SportBoardDTO sportBoardDTO = sportBoardService.findById(id);
        model.addAttribute("sportBoard", sportBoardDTO);

        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (sportBoardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = sportBoardService.findFile(id);
            model.addAttribute("sportBoardFileList", boardFileDTOList);
        }

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }


        List<SportReplyDTO> sportReplyDTOS = sportReplyService.findAll(id);
        model.addAttribute("sportReplyList", sportReplyDTOS);
        return "/sportboard/detail";
    }

    @GetMapping("/sportBoard/list")
    public String findAll(Model model, HttpSession session,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "keyword", required = false) String keyword) {

        List<SportBoardDTO> pagingList;

        if (keyword != null && !keyword.isEmpty()) {
            pagingList = sportBoardService.searchByKeyword(keyword);
        }else{
            pagingList = sportBoardService.pagingList(page);
            PageDTO pageDTO = sportBoardService.pagingParam(page);
            model.addAttribute("sportBoardListPage", pagingList);
            model.addAttribute("paging", pageDTO);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("sportBoardList", pagingList);
        Integer byIdCount = sportBoardService.findByIdCount((String) session.getAttribute("loginId"));
        model.addAttribute("findByIdCount", byIdCount);

        pagingList = sportBoardService.pagingList(page);
        PageDTO pageDTO = sportBoardService.pagingParam(page);
        model.addAttribute("sportBoardListPage", pagingList);
        model.addAttribute("paging", pageDTO);
        model.addAttribute("keyword", keyword);

        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

        return "/sportboard/list";
    }

    @GetMapping("/sportBoard/update/{id}")
    public String update(@PathVariable("id") Long id, HttpSession session ,Model model) {
        SportBoardDTO sportBoardDTO = sportBoardService.findById(id);
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

        model.addAttribute("sportBoard", sportBoardDTO);
        return "/sportboard/update";
    }

    @PostMapping("/sportBoard/update/{id}")
    public String update(SportBoardDTO sportBoardDTO, HttpSession session ,Model model) {
        sportBoardService.update(sportBoardDTO);
        SportBoardDTO dto = sportBoardService.findById(sportBoardDTO.getId());
        model.addAttribute("sportBoard", dto);

        MemberDTO memdto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", memdto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/sportboard/detail";
    }

    @GetMapping("/sportBoard/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        sportReplyService.delete(id);
        sportBoardService.delete(id);
        return "redirect:/sportBoard/list";
    }

    @PostMapping("/sportReply/save")
    public String replySave(SportReplyDTO sportReplyDTO){
        sportReplyService.save(sportReplyDTO);
        return "redirect:/sportBoard/" + sportReplyDTO.getBno();
    }

    @GetMapping("/sportReply/delete/{id}/{bno}")
    public String replyDelete(@PathVariable("id") Long id, @PathVariable("bno") Long bno) {
        sportReplyService.delete(id);
        return "redirect:/sportBoard/" + bno;
    }
}
