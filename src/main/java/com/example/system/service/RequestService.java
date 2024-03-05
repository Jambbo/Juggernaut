package com.example.system.service;

import com.example.system.domain.request.Request;

import java.util.List;

public interface RequestService {
    Request getById(Long requestId);
    List<Request> getAllByUserId(Long id);
    Request update(Request request);
    Request createRequest(Request request, Long userId);
    Request createDraftRequest(Request request, Long userId);
    void delete(Long id);
}
