package com.toyproject.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.dto.MemberDTO;
import com.toyproject.board.dto.MemberFileDTO;
import com.toyproject.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String saveForm() {
        return "/member/save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        int saveResult = memberService.save(memberDTO); //저장된 정보를 int 타입으로 리턴 받고
        log.info("MemberDTO = " + memberDTO);
        if (saveResult > 0) { //저장이 되었으면 가입성공
            return "/member/login";
        } else { //그렇지 않으면 가입 실패
            return "/member/save";
        }
    }

    //개인정보 페이지
    @GetMapping("/member/info/{memberId}")
    public String infoForm(@PathVariable("memberId") Long memberId, Model model) {

        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member", memberDTO);
        //첨부파일이 있으면 내용을 가져오는 조건문
        if (memberDTO != null && memberDTO.getFileAttached() == 1) {
            List<MemberFileDTO> memberFileDTOList = memberService.findFile(memberDTO.getId());
            model.addAttribute("memberFileList", memberFileDTOList);
        }
        return "/member/info";
    }
    //개인정보수정
    @PostMapping("/member/info/{memberId}")
    public String updateMemberInfo(MemberDTO memberDTO, Model model) throws IOException {
        memberService.update(memberDTO);
        MemberDTO dto = memberService.findById(memberDTO.getId());
        model.addAttribute("member", dto);
        return "redirect:/";
    }

    @GetMapping("/member/delete/{memberId}")
    public String delete (@PathVariable("memberId") Long memberId, MemberDTO memberDTO){
        memberService.delete(memberId);
        return "redirect:/member/info/" + memberId;
    }


    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {

        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            MemberDTO dto = memberService.loginSession(memberDTO);
            session.setAttribute("loginId", dto.getMemberId());
            session.setAttribute("Id", dto.getId());
            return "redirect:/";

        } else {
            return "/member/login";
        }
    }

    @GetMapping("/member/logout")
    public String logOut(HttpSession session) {
        session.setAttribute("loginId", null);
        return "redirect:/";
    }

    @RequestMapping("/idcheck.do")
    public String  idCheck(@RequestBody String memberId){
        MemberDTO map = new MemberDTO();
        map.setMemberId(memberId.substring(0,memberId.length()-1));
        if(memberService.idChk(map)){
            return "/member/save :: #true";
        }else
        {
            return "/member/save :: #false";
        }

    }


}
