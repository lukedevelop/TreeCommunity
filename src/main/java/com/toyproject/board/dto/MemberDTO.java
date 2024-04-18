package com.toyproject.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String memberName;
    private String memberId;
    private String memberPass;
    private String nickName;
    private String memberEmail;


    private int fileAttached;
    private List<MultipartFile> memberFile; //게시글을 작성할때 파일 자체를 담기위한 필드

}
