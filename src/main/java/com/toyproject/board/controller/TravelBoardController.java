package com.toyproject.board.controller;

import com.toyproject.board.dto.*;
import com.toyproject.board.service.MemberService;
import com.toyproject.board.service.TravelBoardService;
import com.toyproject.board.service.TravelReplyService;
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
public class TravelBoardController {
    private final TravelBoardService travelBoardService;
    private final TravelReplyService travelReplyService;
    private final MemberService memberService;

    @GetMapping("/travelBoard/save")
    public String save(HttpSession session, Model model) {
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/travelboard/save";
    }

    @PostMapping("/travelBoard/save")
    public String save(TravelBoardDTO travelBoardDTO) throws IOException {
        travelBoardService.save(travelBoardDTO);
        return "redirect:/travelBoard/list";
    }

    @GetMapping("/travelBoard/{id}")
    public String findById(@PathVariable("id") Long id, HttpSession session ,Model model) {
        travelBoardService.updateHits(id);

        TravelBoardDTO travelBoardDTO = travelBoardService.findById(id);
        model.addAttribute("travelBoard", travelBoardDTO);
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (travelBoardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = travelBoardService.findFile(id);
            model.addAttribute("travelBoardFileList", boardFileDTOList);
        }

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }


        List<TravelReplyDTO> travelReplyDTOS = travelReplyService.findAll(id);
        model.addAttribute("travelReplyList", travelReplyDTOS);
        return "/travelboard/detail";
    }

    @GetMapping("/travelBoard/list")
    public String findAll(Model model, HttpSession session,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "keyword", required = false) String keyword) {
        List<TravelBoardDTO> pagingList;
        if (keyword != null && !keyword.isEmpty()) {
            pagingList = travelBoardService.searchByKeyword(keyword);
        }else {
            pagingList = travelBoardService.pagingList(page);
            PageDTO pageDTO = travelBoardService.pagingParam(page);
            model.addAttribute("travelBoardListPage", pagingList);
            model.addAttribute("paging", pageDTO);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("travelBoardList", pagingList);
        Integer byIdCount = travelBoardService.findByIdCount((String) session.getAttribute("loginId"));
        model.addAttribute("findByIdCount", byIdCount);

        pagingList = travelBoardService.pagingList(page);
        PageDTO pageDTO = travelBoardService.pagingParam(page);
        model.addAttribute("travelBoardListPage", pagingList);
        model.addAttribute("paging", pageDTO);
        model.addAttribute("keyword", keyword);

        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }


        return "/travelboard/list";
    }

    @GetMapping("/travelBoard/update/{id}")
    public String update(@PathVariable("id") Long id, HttpSession session ,Model model) {
        TravelBoardDTO travelBoardDTO = travelBoardService.findById(id);
        model.addAttribute("travelBoard", travelBoardDTO);

        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/travelboard/update";
    }

    @PostMapping("/travelBoard/update/{id}")
    public String update(TravelBoardDTO travelBoardDTO, HttpSession session ,Model model) {
        travelBoardService.update(travelBoardDTO);
        TravelBoardDTO dto = travelBoardService.findById(travelBoardDTO.getId());
        model.addAttribute("travelBoard", dto);

        MemberDTO memdto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", memdto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(memdto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/travelboard/detail";
    }

    @GetMapping("/travelBoard/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        travelReplyService.delete(id);
        travelBoardService.delete(id);
        return "redirect:/travelBoard/list";
    }

    @PostMapping("/travelReply/save")
    public String replySave(TravelReplyDTO travelReplyDTO){
        travelReplyService.save(travelReplyDTO);
        return "redirect:/travelBoard/" + travelReplyDTO.getBno();
    }

    @GetMapping("/travelReply/delete/{id}/{bno}")
    public String replyDelete(@PathVariable("id") Long id, @PathVariable("bno") Long bno) {
        travelReplyService.delete(id);
        return "redirect:/travelBoard/" + bno;
    }

}
