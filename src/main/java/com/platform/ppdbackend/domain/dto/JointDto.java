package com.platform.ppdbackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JointDto {
    private Long idx;
    private Integer result;
    private List<Float> normalValues;
    private List<Float> abnormalValues;
    private List<Float> actualValues;
    private List<Integer> resultTypes;

}
