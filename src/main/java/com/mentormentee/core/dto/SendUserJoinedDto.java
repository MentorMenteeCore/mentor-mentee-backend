package com.mentormentee.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 구독하고 상대가 입장했다는 사실을 알릴떄 사용하는 dto.
 */
@Data
@AllArgsConstructor
public class SendUserJoinedDto {

    private String userId;
    private String message;

}
