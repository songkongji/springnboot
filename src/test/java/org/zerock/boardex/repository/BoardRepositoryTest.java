package org.zerock.boardex.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.boardex.domain.Board;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
// 데이터입력하기
    @Test
    public void testInsert(){
        IntStream.range(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("user" + (i % 10))
                    .build();
            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }
    //select기능
    @Test
    public void testSelect(){
        Long bno = 10L;
        Optional<Board> result = boardRepository.findById(bno);
//    orElseThrow() : if ~ else를 사용해서 error 처리해야 하는데 이 메서드를 사용시 자동으로 error처리해줌, optional에서 제공
        Board board = result.orElseThrow();
        log.info(board);
    }
//    update 기능
    @Test
    public void testUpdate(){
        Long bno = 99L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("update 99", "update content 99");
        boardRepository.save(board);
    }
//    delete 기능
    @Test
    public void testDelete(){
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }
//    기본적인 페이징 처리 기능
    @Test
    public void testPaging(){
//        PageRequest.of() : page-현재 페이지 size- 한페이지에 보여줄 게시글 개수
        Pageable pageable  = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        log.info("총 페이지 수 : " + result.getTotalPages());
        log.info("총 게시글 수: " + result.getTotalElements());
        log.info("현재 페이지 : " + result.getNumber());
        log.info("페이지 크기 : " + result.getSize());
    }

    @Test
    public void testSearch(){
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pagealbe = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pagealbe);
    }

    @Test
    public void testSearch2(){
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());
//        이전 페이지와 다음 페이지
        log.info(result.hasPrevious() + " : " + result.hasNext());
        result.getContent().forEach(board -> log.info(board));
    }
}

