package com.toyproject.board.service;

import com.toyproject.board.dto.MemberFileDTO;
import com.toyproject.board.dto.BoardFileDTO;
import com.toyproject.board.dto.MemberDTO;
import com.toyproject.board.dto.SportBoardDTO;
import com.toyproject.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public int save(MemberDTO memberDTO) {
        return memberRepository.save(memberDTO);
    }

    public boolean login(MemberDTO memberDTO) {
        MemberDTO loginMember = memberRepository.login(memberDTO);
        if (loginMember != null) {
            return true;
        } else {
            return false;
        }
    }

    public MemberDTO loginSession(MemberDTO memberDTO) {
        return memberRepository.login(memberDTO);
    }

    public boolean idChk(MemberDTO memberDTO) {
        MemberDTO memberId = memberRepository.idChk(memberDTO);
        if (memberId != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<MemberDTO> findAll(){
        return memberRepository.findAll();
    }

    public MemberDTO findById(Long id) {
        return memberRepository.findById(id);
    }


    // 파일 저장 및 회원 정보 업데이트
    public void update(MemberDTO memberDTO) throws IOException {
        if (memberDTO.getMemberFile().get(0).isEmpty()) {
            //파일 없다.
            memberDTO.setFileAttached(0);
            memberRepository.update(memberDTO);
        } else {
            //파일 있다.
            memberDTO.setFileAttached(1);
            //게시글 저장 후 id값 활용을 위해 리턴 받음.
//            MemberDTO savemember = memberRepository.save(memberDTO);
            //파일만 따로 가져오기
//            MultipartFile boardFile = memberDTO.getMemberFile();  //: 단일 파일

            //다중파일 가져오기
            for (MultipartFile memberFile : memberDTO.getMemberFile()) {

                //파일 이름 가져오기
                String originalFilename = memberFile.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);
                //저장용 이름 만들기
                System.out.println(System.currentTimeMillis());
                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                System.out.println("storedFileName = " + storedFileName);
                //BoardFileDTO 세팅
                MemberFileDTO memberFileDTO = new MemberFileDTO();
                memberFileDTO.setOriginalFileName(originalFilename);
                memberFileDTO.setStoredFileName(storedFileName);
                memberFileDTO.setMemid(memberDTO.getId());
                //파일 저장용 폴더에 파일 저장 처리
                String savePath = "C:/Users/whgml/spring_upload_files/" + storedFileName;
                memberFile.transferTo(new File(savePath)); // savePath 경로에 파일이름으로 넘김.
                //board_file_table 저장 처리
                memberRepository.saveFile(memberFileDTO);
                memberRepository.update(memberDTO);
            }

        }
    }


  public void saveFile(MemberFileDTO memberFileDTO) {
      memberRepository.saveFile(memberFileDTO);
  }

    public List<MemberFileDTO> findFile(Long memberId) {
        return memberRepository.findFile(memberId);
    }

    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }
}
