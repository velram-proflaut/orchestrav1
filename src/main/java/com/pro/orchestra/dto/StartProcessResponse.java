package com.pro.orchestra.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StartProcessResponse {

    private String processInstanceId;

    private String processDefinitionId;

    private String businessKey;

    private String status;

}