package org.zerock.boardex.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean img;

    public String getLink(){
//        이미지면 썸네일 이름짓던거처럼 아니면 그냥 uuid 더하기
        if(img){
            return "s_" + uuid + "_" + fileName;
        } else {
            return uuid + "_" + fileName;
        }
    }
}
