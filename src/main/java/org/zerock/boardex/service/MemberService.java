package org.zerock.boardex.service;

import org.zerock.boardex.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception{}
    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}