package com.toyproject.board.controller;

import com.toyproject.board.dto.*;
import com.toyproject.board.service.EtcBoardService;
import com.toyproject.board.service.EtcReplyService;
import com.toyproject.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EtcBoardController {
    private final EtcBoardService etcBoardService;
    private final EtcReplyService etcReplyService;
    private final MemberService memberService;

    @GetMapping("/etcBoard/save")
    public String save(HttpSession session, Model model) {
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/etcboard/save";
    }

    @PostMapping("/etcBoard/save")
    public String save(EtcBoardDTO etcBoardDTO) throws IOException {
        etcBoardService.save(etcBoardDTO);
        return "redirect:/etcBoard/list";
    }

    @GetMapping("/etcBoard/{id}")
    public String findById(@PathVariable("id") Long id, HttpSession session ,Model model) {

        etcBoardService.updateHits(id);

        EtcBoardDTO etcBoardDTO = etcBoardService.findById(id);
        model.addAttribute("etcBoard", etcBoardDTO);
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);


        if (etcBoardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = etcBoardService.findFile(id);
            model.addAttribute("etcBoardFileList", boardFileDTOList);
        }
        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

        List<EtcReplyDTO> etcReplyDTOS = etcReplyService.findAll(id);
        model.addAttribute("etcReplyList", etcReplyDTOS);
        return "/etcboard/detail";
    }



    @GetMapping("/etcBoard/list")
    public String findAll(Model model, HttpSession session,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "keyword", required = false) String keyword) {

        List<EtcBoardDTO> pagingList;
        if (keyword != null && !keyword.isEmpty()) {
            pagingList = etcBoardService.searchByKeyword(keyword);
        }else {
//            pagingList = etcBoardService.etcFindAll();
            pagingList = etcBoardService.pagingList(page);
            PageDTO pageDTO = etcBoardService.pagingParam(page);
            model.addAttribute("etcBoardListPage", pagingList);
            model.addAttribute("paging", pageDTO);
            model.addAttribute("keyword", keyword); // 검색어를 다시 화면으로 전달
        }

        model.addAttribute("etcBoardList", pagingList);
        Integer byIdCount = etcBoardService.findByIdCount((String) session.getAttribute("loginId"));
        model.addAttribute("findByIdCount", byIdCount);

        pagingList = etcBoardService.pagingList(page);
        PageDTO pageDTO = etcBoardService.pagingParam(page);
        model.addAttribute("etcBoardListPage", pagingList);
        model.addAttribute("paging", pageDTO);
        model.addAttribute("keyword", keyword); // 검색어를 다시 화면으로 전달

        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

        return "/etcboard/list";
    }

    @GetMapping("/etcBoard/update/{id}")
    public String update(@PathVariable("id") Long id, HttpSession session ,Model model) {
        EtcBoardDTO etcBoardDTO = etcBoardService.findById(id);
        model.addAttribute("etcBoard", etcBoardDTO);

        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

        return "/etcboard/update";
    }

    @PostMapping("/etcBoard/update/{id}")
    public String update(EtcBoardDTO etcBoardDTO, HttpSession session ,Model model) {
        etcBoardService.update(etcBoardDTO);
        EtcBoardDTO dto = etcBoardService.findById(etcBoardDTO.getId());
        model.addAttribute("etcBoard", dto);

        MemberDTO memdto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", memdto);

        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/etcboard/detail";
    }

    @GetMapping("/etcBoard/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        etcReplyService.delete(id);
        etcBoardService.delete(id);
        return "redirect:/etcBoard/list";
    }

    @PostMapping("/etcReply/save")
    public String replySave(EtcReplyDTO EtcReplyDTO){
        etcReplyService.save(EtcReplyDTO);
        return "redirect:/etcBoard/" + EtcReplyDTO.getBno();
    }

    @GetMapping("/etcReply/delete/{id}/{bno}")
    public String replyDelete(@PathVariable("id") Long id, @PathVariable("bno") Long bno) {
        etcReplyService.delete(id);
        return "redirect:/etcBoard/" + bno;
    }
}
