package com.example.system.domain.request;

import com.example.system.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Column(name = "phone_number")
    private String phoneNumber;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
