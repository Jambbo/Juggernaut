package com.example.system.service;

import com.example.system.domain.request.Request;
import com.example.system.domain.request.Status;
import com.example.system.domain.user.User;
import com.example.system.web.dto.request.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface RequestService {
    Request getRequestById(Long requestId);
    Request update(Request request);
    Page<RequestDto> findRequestsCreatedBy(String sortBy, String order, int page, int size);
    Page<RequestDto> findAllRequestsSortedByDate(String sortBy, String order, int page, int size);
    Page<RequestDto> findByCreatedByContainingIgnoreCaseAndStatus(String createdBy, Status status, int page, int size);
    void acceptRequest(Long requestId);
    void rejectRequest(Long requestId);

    Request createRequest(Request request, Long userId);
    Request createDraftRequest(Request request, Long userId);

    Request addRequestToUser(Request request, Long userId);
    Request sendRequestToOperator(Request request, Long userId);

    void delete(Long id);
}
