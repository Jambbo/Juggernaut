package com.example.system.repository;

import com.example.system.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = """
                SELECT * FROM requests r
                JOIN users_requests ur ON ur.request_id = r.id
                WHERE ur.user_id = :userId
            """, nativeQuery = true)
    List<Request> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
        INSERT INTO users_requests(user_id, request_id)
        VALUES(:userId,:requestId)
    """, nativeQuery = true)
    void sendRequest(@Param("userId") Long userId, @Param("requestId") Long requestId);


}
