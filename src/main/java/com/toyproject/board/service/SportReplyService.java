package com.toyproject.board.service;

import com.toyproject.board.dto.ReplyDTO;
import com.toyproject.board.dto.SportReplyDTO;
import com.toyproject.board.repository.ReplyRepository;
import com.toyproject.board.repository.SportReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportReplyService {

    private final SportReplyRepository sportReplyRepository;

    public void save(SportReplyDTO sportReplyDTO) {
        sportReplyRepository.save(sportReplyDTO);
    }

    public List<SportReplyDTO> findAll(Long bno) {
        return sportReplyRepository.findAll(bno);
    }

   public SportReplyDTO findById(Long bno) {
       return sportReplyRepository.findById(bno);
   }

    public void delete(Long id) {
        sportReplyRepository.delete(id);
    }
}
