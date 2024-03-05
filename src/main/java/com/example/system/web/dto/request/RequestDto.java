//package com.example.system.web.dto.request;
//
//import com.example.system.domain.request.Status;
//import com.example.system.domain.user.User;
//import com.example.system.web.dto.validation.OnCreate;
//import com.example.system.web.dto.validation.OnUpdate;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import org.hibernate.validator.constraints.Length;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDateTime;
//@Data
//public class RequestDto {
//
//    @NotNull(message = "id must be not null.", groups = OnUpdate.class)
//    private Long id;
//
//    @NotNull(message = "title must be not mull", groups = {OnUpdate.class, OnCreate.class})
//    @Length(max = 255, message = "Title length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
//    private String title;
//
//    @Length(max = 255, message = "Text length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
//    private String text;
//
//    private Status status;
//
//    @Length(max = 255, message = "Phone number must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
//    public String phoneNumber;
//
////    private User user;
//
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime createdAt;
//}
