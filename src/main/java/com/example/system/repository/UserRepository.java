package com.example.system.repository;

import com.example.system.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByUsername(String username);

    @Query(value = """
        SELECT u.id as id,
        u.name as name,
        u.username as username,
        u.password as password
        FROM users_requests ur
        JOIN users u ON ur.user_id = u.id
        WHERE ur.request_id = :requestId
            """,nativeQuery = true)
    Optional<User> findRequestAuthor(@Param("requestId")Long requestId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Request r WHERE r.id = :requestId AND r.user.id = :userId")
    boolean isRequestOwner(@Param("userId")Long userId, @Param("requestId")Long requestId);
}
