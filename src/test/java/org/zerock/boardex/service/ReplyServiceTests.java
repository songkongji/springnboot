package org.zerock.boardex.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardex.dto.ReplyDTO;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    @Autowired
    private ReplyService replyService;
    @Test
    public void testRegister(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("댓글 테스트")
                .replyer("손오공")
                .bno(204L)
                .build();
        log.info(replyService.register(replyDTO));
    }
}
