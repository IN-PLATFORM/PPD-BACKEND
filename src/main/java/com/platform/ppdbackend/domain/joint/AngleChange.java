package com.platform.ppdbackend.domain.joint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.platform.ppdbackend.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class AngleChange extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="angle_idx")
    private Long idx;

    private String graphUrl;
    private String detail; // 추가 설명

    private Float actual_value;
    private Float normal_value;
    private Float abnormal_value;

    // 읽기 전용
    @OneToOne(mappedBy = "angleChange") // Joint 엔티티에 있는 angleChange 필드와 매핑되었다.
    private Joint joint;
}
