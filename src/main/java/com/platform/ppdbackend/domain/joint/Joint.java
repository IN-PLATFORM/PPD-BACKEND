package com.platform.ppdbackend.domain.joint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.platform.ppdbackend.domain.BaseTimeEntity;
import com.platform.ppdbackend.domain.joint.enums.ResultType;
import com.platform.ppdbackend.domain.user.User;
import com.platform.ppdbackend.domain.user.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Joint extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="joint_idx")
    private Long idx;

    private String imgUrl;
    private String detail; // 추가 설명

    @Enumerated(EnumType.STRING)
    private ResultType resultType; // 각 사용자의 결과상태(정상/비정상)을 관리할 Enum class

    // 4가지 특성 1:1매칭
    @OneToOne
    @JoinColumn(name="speed_idx")
    private Speed speed;

    @OneToOne
    @JoinColumn(name="ankles_idx")
    private Ankles ankles;

    @OneToOne
    @JoinColumn(name="knee_idx")
    private Knee knee;

    @OneToOne
    @JoinColumn(name="angle_idx")
    private AngleChange angleChange;

    // Joint : User = N: 1 관계 (사용자는 많은 결과를 가질 수 있다)
    // 연관관계의 주인
    @ManyToOne
    @JoinColumn(name ="user_idx")
    private User user;






}
