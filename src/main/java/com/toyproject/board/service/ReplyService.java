package com.toyproject.board.service;

import com.toyproject.board.dto.ReplyBoardDTO;
import com.toyproject.board.dto.ReplyDTO;
import com.toyproject.board.repository.BoardRepository;
import com.toyproject.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public void save(ReplyDTO replyDTO) {
        replyRepository.save(replyDTO);
    }

    public List<ReplyDTO> findAll(Long bno) {
        return replyRepository.findAll(bno);
    }

   public ReplyDTO findById(Long bno) {
       return replyRepository.findById(bno);
   }

    public void delete(Long id) {
        replyRepository.delete(id);
    }
}
