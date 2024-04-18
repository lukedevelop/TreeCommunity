package com.toyproject.board.service;

import com.toyproject.board.dto.EtcReplyDTO;
import com.toyproject.board.dto.SportReplyDTO;
import com.toyproject.board.repository.EtcReplyRepository;
import com.toyproject.board.repository.SportReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtcReplyService {

    private final EtcReplyRepository etcReplyRepository;

    public void save(EtcReplyDTO etcReplyDTO) {
        etcReplyRepository.save(etcReplyDTO);
    }

    public List<EtcReplyDTO> findAll(Long bno) {
        return etcReplyRepository.findAll(bno);
    }

   public EtcReplyDTO findById(Long bno) {
       return etcReplyRepository.findById(bno);
   }

    public void delete(Long id) {
        etcReplyRepository.delete(id);
    }
}
