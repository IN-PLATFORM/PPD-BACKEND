package com.platform.ppdbackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JointRequestDto {

    private Integer result;
    private List<Float> normalValues;
    private List<Float> abnormalValues;
    private List<Float> actualValues;
    private List<Integer> resultTypes;

}
