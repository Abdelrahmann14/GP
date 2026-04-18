package com.spring.boot.rest.model.contactInfo;
import com.spring.boot.rest.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class ContactInfo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, length = 1000)
    private String message;
    @Column(nullable = false, length = 25)
    private String subject;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
