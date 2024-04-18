package com.toyproject.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyBoardDTO {
    private Long id;
    private String replyMemberId;
    private String comments;
    private String replyPass;
    private String createdAt;
    private String groupNum;
}
