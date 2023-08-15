package com.platform.ppdbackend.repository;


import com.platform.ppdbackend.domain.joint.Joint;
import com.platform.ppdbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JointRepository extends JpaRepository<Joint,Long> {

}
