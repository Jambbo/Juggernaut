package com.example.system.service.impl;

import com.example.system.domain.request.Request;
import com.example.system.domain.request.Status;
import com.example.system.repository.RequestRepository;
import com.example.system.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    @Override
    public Request getById(Long requestId){
        return requestRepository.findById(requestId)
                .orElseThrow(
                        ()->new IllegalStateException("Request not found.")
                );
    }

    @Override
    public List<Request> getAllByUserId(Long id) {
        return requestRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public Request update(Request request) {
        Request existing = getById(request.getId());
       if(existing.getStatus()==Status.DRAFT){
           existing.setTitle(request.getTitle());
           existing.setText(request.getText());
           existing.setPhoneNumber(request.getPhoneNumber());
           requestRepository.save(existing);
       }else{
           throw new IllegalStateException("You can not change sent requests.");
       }
       return request;
    }

    @Override
    @Transactional
    public Request createRequest(Request request, Long userId) {
        if(request.getStatus()!=null){
            request.setStatus(Status.SENT);
        }
        requestRepository.save(request);
        return request;
    }

    @Override
    @Transactional
    public Request createDraftRequest(Request request, Long userId) {
        if(request.getStatus()!=null){
            request.setStatus(Status.DRAFT);
        }
        requestRepository.save(request);
        return request;
    }


    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

}
