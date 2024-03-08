package com.example.system.service;

import com.example.system.domain.request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface RequestService {
    Request getById(Long requestId);
//    List<Request> getAllByUserId(Long id);
    Request update(Request request);
    List<Request> getRequests(int pageNo, int pageSize, String sortBy, String sortOrder);
    Request createRequest(Request request, Long userId);
    Request createDraftRequest(Request request, Long userId);

    Request addRequestToUser(Request request, Long userId);
    void delete(Long id);
}
