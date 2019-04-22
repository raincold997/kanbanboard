package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<KBUser,Long> {
    KBUser findByUserEmail(String email);

    KBUser findByUserName(String name);

    @Query("select u.userName from KBUser u where u.userId = ?1")
    String findNameById(Long userId);
}
