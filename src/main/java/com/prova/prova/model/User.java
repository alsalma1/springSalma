package com.prova.prova.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String nickname;
    @Column(nullable = false, unique = true, length = 60)
    private String email;
    @Column(nullable = false, unique = true, length = 45)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100)
    private String role;

}