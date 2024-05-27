package com.toyproject.board.controller;

import com.toyproject.board.dto.*;
import com.toyproject.board.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final SportBoardService sportBoardService;
    private final EtcBoardService etcBoardService;
    private final TravelBoardService travelBoardService;
    private final MemberService memberService;

/*    @GetMapping("/")
    public String index() {
        return "index";
    }*/

    @GetMapping("/")
    public String findAll(Model model, HttpSession session) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("freeBoardList", boardDTOList);
        List<SportBoardDTO> sportBoardDTOList = sportBoardService.sportFindAll();
        model.addAttribute("sportBoardList", sportBoardDTOList);
        List<EtcBoardDTO> etcBoardDTOList = etcBoardService.etcFindAll();
        model.addAttribute("etcBoardList", etcBoardDTOList);
        List<TravelBoardDTO> travelBoardDTOList = travelBoardService.travelFindAll();
        model.addAttribute("travelBoardList", travelBoardDTOList);
        System.out.println("id : "+(Long)session.getAttribute("Id")+"");
        MemberDTO dto = memberService.findById((Long)session.getAttribute("Id"));
        model.addAttribute("member", dto);


        if (dto != null && dto.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(dto.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }

        log.info("boardDTOList = " + boardDTOList);
        return "index.html";
    }


}
