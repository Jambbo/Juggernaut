package com.example.system.repository;

import com.example.system.domain.request.Request;
import com.example.system.domain.request.Status;
import com.example.system.domain.user.User;
import com.example.system.web.dto.request.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Page<Request> findByCreatedByContainingIgnoreCaseAndStatus(String createdBy, Status status,Pageable pageable);
    Page<Request> findByUserId(Long userId,Pageable pageable);
    Page<Request> findAllByStatus(Status status, Pageable pageable);
    Request findByIdAndStatus(Long id, Status status);

}
