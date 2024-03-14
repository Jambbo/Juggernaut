package com.example.system.service.impl;

import com.example.system.domain.request.Request;
import com.example.system.domain.request.Status;
import com.example.system.domain.user.User;
import com.example.system.repository.RequestRepository;
import com.example.system.repository.UserRepository;
import com.example.system.service.RequestService;
import com.example.system.web.dto.request.RequestDto;
import com.example.system.web.security.JwtEntity;
import com.example.system.web.security.expression.CustomSecurityExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import com.example.system.web.mappers.RequestMapper;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    public Request getRequestById(Long requestId){
        return requestRepository.findById(requestId)
                .orElseThrow(
                        ()->new IllegalStateException("Request not found.")
                );
    }
    @Override
    public Page<RequestDto> findRequestsCreatedByUser(Long userId,String sortBy, String order, int page, int size) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        Page<Request> requests = requestRepository.findByUserId(userId,pageable);

        return requests.map(request -> {
            RequestDto requestDto = new RequestDto();
            requestDto.setId(request.getId());
            requestDto.setTitle(request.getTitle());
            requestDto.setText(request.getText());
            requestDto.setPhoneNumber(request.getPhoneNumber());
            requestDto.setStatus(request.getStatus());
            requestDto.setCreatedAt(request.getCreatedAt());
            return requestDto;
        });
    }
    @Override
    public Page<RequestDto> findAllRequestsSortedByDate(String sortBy, String order, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        Page<Request> requests = requestRepository.findAllByStatus(Status.SENT,pageable);

        return requests.map(request -> {
            RequestDto requestDto = new RequestDto();
            requestDto.setId(request.getId());
            requestDto.setTitle(request.getTitle());
            requestDto.setText(request.getText());
            requestDto.setPhoneNumber(request.getPhoneNumber());
            requestDto.setStatus(request.getStatus());
            requestDto.setCreatedAt(request.getCreatedAt());
            return requestDto;
        });
    }
    @Override
    public Page<RequestDto> findByCreatedByContainingIgnoreCaseAndStatus(String createdBy,Status status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Request> requests =  requestRepository.findByCreatedByContainingIgnoreCaseAndStatus(createdBy, status,pageable);

        return requests.map(request -> {
            RequestDto requestDto = new RequestDto();
            requestDto.setId(request.getId());
            requestDto.setTitle(request.getTitle());
            requestDto.setText(request.getText());
            requestDto.setPhoneNumber(request.getPhoneNumber());
            requestDto.setStatus(request.getStatus());
            requestDto.setCreatedAt(request.getCreatedAt());
            return requestDto;
        });
    }

    @Override
    public void acceptRequest(Long requestId) {
        Request request = getRequestById(requestId);
        request.setStatus(Status.ACCEPTED);
        requestRepository.save(request);
    }
    @Override
    public void rejectRequest(Long requestId) {
        Request request = getRequestById(requestId);
        request.setStatus(Status.REJECTED);
        requestRepository.save(request);
    }

//    @Override
//    public List<Request> getAllByUserId(Long id) {
//        return requestRepository.findAllByUserId(id);
//    }

    @Override
    @Transactional
    public Request update(Request request) {
        Request existing = getRequestById(request.getId());
       if(existing.getStatus()==Status.DRAFT){
           existing.setTitle(request.getTitle());
           existing.setText(request.getText());
           existing.setPhoneNumber(request.getPhoneNumber());
           requestRepository.save(existing);
       }else{
           throw new IllegalStateException("You can not change sent reques5ts.");
       }
       return request;
    }

    @Override
    @Transactional
    public Request sendRequestToOperator(Request request,Long userId) {
        request.setStatus(Status.SENT);
        request.setCreatedAt(LocalDateTime.now());
        User user = userRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("User not found.")
        );
        if(!user.isConfirm()){
            throw new IllegalStateException("User have not verified gmail.");
        }
        request.setCreatedBy(user.getName());
        request.setUser(user);
        requestRepository.save(request);
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
        request.setCreatedBy(user.getName());
        userRepository.save(user);
        int size = user.getRequests().size();
        return user.getRequests().get(size-1);
    }



    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

}
