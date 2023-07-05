package org.zerock.boardex.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardex.domain.Board;
import org.zerock.boardex.dto.BoardDTO;
import org.zerock.boardex.dto.PageRequestDTO;
import org.zerock.boardex.dto.PageResponseDTO;
import org.zerock.boardex.repository.BoardRepository;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Test  //데이터 등록 작업 테스트
    public void testRegister(){
        log.info(boardService.getClass().getName());
        BoardDTO boardDTO = BoardDTO.builder()
                .title("테스트 예제123")
                .content("테스트 내용123")
                .writer("이순신")
                .build();
        Long bno = boardService.register(boardDTO);
        log.info("번호 : " + bno);
    }
//    데이터 수정 작업
    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("제목 수정 101")
                .content("내용 수정..")
                .build();
        boardService.modify(boardDTO);
    }
//    데이터 삭제 작업
    @Test
    public void testRemove() {
        Long bno = 200L;
        boardService.remove(bno);
    }
//   목록과 검색 기능
    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }
}
