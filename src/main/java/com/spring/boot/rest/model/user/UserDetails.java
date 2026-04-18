package com.spring.boot.rest.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 2)
    private String age;
    @Column(nullable = false, length = 12)
    private String phoneNumber;
    @Column(nullable = false, length = 100)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
