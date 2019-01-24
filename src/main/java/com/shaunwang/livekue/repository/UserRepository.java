package com.shaunwang.livekue.repository;

import com.shaunwang.livekue.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByIdIn(List<Long> userIds);


    Optional<User> findByStudentNumber(int studentNumber);

    Boolean existsByStudentNumber(int studentNumber);

    Boolean existsByEmail(String email);
}
