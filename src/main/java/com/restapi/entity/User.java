package com.restapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime localDateTime;

//    @Enumerated(EnumType.STRING)
//    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Booking> booking;

   @ManyToMany(fetch=FetchType.EAGER)
    List<Role> roles = new ArrayList<>();

}
