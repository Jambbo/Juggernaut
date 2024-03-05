package com.example.system.service.impl;

import com.example.system.domain.request.Request;
import com.example.system.domain.request.Status;
import com.example.system.domain.user.User;
import com.example.system.repository.RequestRepository;
import com.example.system.repository.UserRepository;
import com.example.system.service.RequestService;
import com.example.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
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
    @Transactional
    public Request addRequestToUser(Request request,Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        user.addRequest(request);
        userRepository.save(user);
        int size = user.getRequests().size();
        return user.getRequests().get(size-1);
    }



    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

}
