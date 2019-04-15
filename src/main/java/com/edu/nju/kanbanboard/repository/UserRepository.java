package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<KBUser,Long> {
    KBUser findByUserEmail(String email);

    KBUser findByUserName(String name);
}
