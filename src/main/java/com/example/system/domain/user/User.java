package com.example.system.domain.user;

import com.example.system.domain.request.Request;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.scheduling.config.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    @Transient
    private String passwordConfirmation;
    @Column(name = "authconfirmcode")
    private String authConfirmCode;
    private boolean confirm;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Request> requests;

    public void addRequest(Request request){
        if(requests==null){
            requests = new ArrayList<>();
        }
        requests.add(request);
    }
    public void setAuthConfirmCode() {
        String prefix = "aboba";
        String randomCode = generateRandomCode(5); // Generate 5 random letters or digits
        this.authConfirmCode = prefix + randomCode;
    }

    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }

}
