package com.pro.orchestra.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StartProcessRequest {

    private Map<String, Object> variables;

}