package com.toyproject.board.dto;

import lombok.Data;

@Data
public class SportReplyDTO {

    private Long id;
    private Long bno;
    private String writer;
    private String content;
    private String replyPass;
    private String createdAt;

}
