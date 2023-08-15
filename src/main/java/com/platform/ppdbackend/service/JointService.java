package com.platform.ppdbackend.service;


import com.platform.ppdbackend.domain.dto.JointDto;
import com.platform.ppdbackend.domain.dto.JointRequestDto;
import com.platform.ppdbackend.domain.joint.*;
import com.platform.ppdbackend.domain.joint.enums.ResultType;
import com.platform.ppdbackend.domain.user.User;
import com.platform.ppdbackend.repository.JointRepository;
import com.platform.ppdbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JointService {

    private final JointRepository jointRepository;
    private final UserRepository userRepository;


    public ResultType findResultType(Integer value){
        if(value == 0) return ResultType.NORMAL;
        else return ResultType.ABNORMAL;
    }
    public Integer findResultTypeInt(ResultType value){
        if(Objects.equals(value, ResultType.NORMAL)) return 0;
        else return 1;
    }


    public JointDto saveNewData(String email, JointRequestDto jointRequestDto) {
        Joint joint = new Joint();
        // 최종 결과 값 저장
        joint.setResultType(findResultType(jointRequestDto.getResult()));

        //speed 저장
        Speed speed = new Speed(jointRequestDto.getActualValues().get(0), jointRequestDto.getNormalValues().get(0), jointRequestDto.getAbnormalValues().get(0),findResultType(jointRequestDto.getResultTypes().get(0)));
        joint.setSpeed(speed);
        //Ankles 저장
        Ankles ankles = new Ankles(jointRequestDto.getActualValues().get(1), jointRequestDto.getNormalValues().get(1), jointRequestDto.getAbnormalValues().get(1),findResultType(jointRequestDto.getResultTypes().get(1)));
        joint.setAnkles(ankles);
        //knee 저장
        Knee knee = new Knee(jointRequestDto.getActualValues().get(2), jointRequestDto.getNormalValues().get(2), jointRequestDto.getAbnormalValues().get(2),findResultType(jointRequestDto.getResultTypes().get(2)));
        joint.setKnee(knee);
        //angleChange 저장
        AngleChange angleChange = new AngleChange(jointRequestDto.getActualValues().get(3), jointRequestDto.getNormalValues().get(3), jointRequestDto.getAbnormalValues().get(3),findResultType(jointRequestDto.getResultTypes().get(3)));
        joint.setAngleChange(angleChange);
        //backAngle 저장
        BackAngle backAngle = new BackAngle(jointRequestDto.getActualValues().get(4), jointRequestDto.getNormalValues().get(4), jointRequestDto.getAbnormalValues().get(4),findResultType(jointRequestDto.getResultTypes().get(4)));
        joint.setBackAngle(backAngle);
        //armAngle 저장
        ArmAngle armAngle = new ArmAngle(jointRequestDto.getActualValues().get(5), jointRequestDto.getNormalValues().get(5), jointRequestDto.getAbnormalValues().get(5),findResultType(jointRequestDto.getResultTypes().get(5)));
        joint.setArmAngle(armAngle);

        // 사용자 저장
        Optional<User> user = userRepository.findByEmail(email);
        joint.setUser(user.get());

        // Joint 데이터 저장
        Joint result = jointRepository.save(joint);

        return mapJointToDto(result);

    }

    public JointDto mapJointToDto(Joint joint){
        JointDto jointDto = new JointDto();

        //idx
        jointDto.setIdx(joint.getIdx());
        // 정상 / 비정상
        jointDto.setResult(findResultTypeInt(joint.getResultType()));


        // actual value
        jointDto.setActualValues(Arrays.asList(
                joint.getSpeed().getActual_value(),
                joint.getAnkles().getActual_value(),
                joint.getKnee().getActual_value(),
                joint.getAngleChange().getActual_value(),
                joint.getBackAngle().getActual_value(),
                joint.getArmAngle().getActual_value()
        ));

        // normal value
        jointDto.setNormalValues(Arrays.asList(
                joint.getSpeed().getNormal_value(),
                joint.getAnkles().getNormal_value(),
                joint.getKnee().getNormal_value(),
                joint.getAngleChange().getNormal_value(),
                joint.getBackAngle().getNormal_value(),
                joint.getArmAngle().getNormal_value()
        ));


        // abnormal value
        jointDto.setAbnormalValues(Arrays.asList(
                joint.getSpeed().getAbnormal_value(),
                joint.getAnkles().getAbnormal_value(),
                joint.getKnee().getAbnormal_value(),
                joint.getAngleChange().getAbnormal_value(),
                joint.getBackAngle().getAbnormal_value(),
                joint.getArmAngle().getAbnormal_value()
        ));

        // Result types value
        jointDto.setResultTypes(Arrays.asList(
                findResultTypeInt(joint.getSpeed().getResultType()),
                findResultTypeInt(joint.getAnkles().getResultType()),
                findResultTypeInt(joint.getKnee().getResultType()),
                findResultTypeInt(joint.getAngleChange().getResultType()),
                findResultTypeInt(joint.getBackAngle().getResultType()),
                findResultTypeInt(joint.getArmAngle().getResultType())
        ));

        return jointDto;

    }

    @Transactional
    public boolean deleteData(Long idx) {
        Optional<Joint> joint = jointRepository.findById(idx);
        if (joint.isPresent()) {
            jointRepository.delete(joint.get());
            return true;
        }
        return false;
    }

    public List<JointDto> getUserDataList(String email) {
        // 사용자 entity
        Optional<User> user = userRepository.findByEmail(email);

        // joint 데이터
        List<Joint> joints = user.get().getJoints();
        List<JointDto> jointDtos = joints.stream().map(this::mapJointToDto).collect(Collectors.toList());

        return jointDtos;
    }
}
