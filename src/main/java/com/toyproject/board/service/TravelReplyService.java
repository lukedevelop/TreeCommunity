package com.toyproject.board.service;

import com.toyproject.board.dto.SportReplyDTO;
import com.toyproject.board.dto.TravelReplyDTO;
import com.toyproject.board.repository.SportReplyRepository;
import com.toyproject.board.repository.TravelReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelReplyService {

    private final TravelReplyRepository travelReplyRepository;

    public void save(TravelReplyDTO travelReplyDTO) {
        travelReplyRepository.save(travelReplyDTO);
    }

    public List<TravelReplyDTO> findAll(Long bno) {
        return travelReplyRepository.findAll(bno);
    }

   public TravelReplyDTO findById(Long bno) {
       return travelReplyRepository.findById(bno);
   }

    public void delete(Long id) {
        travelReplyRepository.delete(id);
    }
}
