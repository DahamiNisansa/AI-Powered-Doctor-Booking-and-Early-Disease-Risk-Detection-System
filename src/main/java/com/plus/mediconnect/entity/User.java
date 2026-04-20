package com.plus.mediconnect.entity;

import com.plus.mediconnect.utils.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;
    private String imgPathProfile ;
    private  String imgPathCover  ;
    private  String cvDocumentPath  ;
    private String registerDate ;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

}
