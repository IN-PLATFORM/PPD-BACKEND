package com.platform.ppdbackend.domain.joint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.platform.ppdbackend.domain.BaseTimeEntity;
import com.platform.ppdbackend.domain.joint.enums.ResultType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class BackAngle extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="back_idx")
    private Long idx;

    private Float actual_value;
    private Float normal_value;
    private Float abnormal_value;

    @Enumerated(EnumType.STRING)
    private ResultType resultType; // 각 특성의 결과상태(정상/비정상)을 관리할 Enum class

    // 읽기 전용
    @OneToOne(mappedBy = "backAngle") // Joint 엔티티에 있는 angleChange 필드와 매핑되었다.
    private Joint joint;
}
