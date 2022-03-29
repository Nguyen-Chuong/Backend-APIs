package com.capstone_project.hbts.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "Role")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // default in spring security, Role is in format: ROLE_NAME, ex: ROLE_ADMIN
    // only user has role format like this can be processed in spring security
    @Column(name = "role_name")
    private String name;

    @ManyToOne @JoinColumn(name = "user_id")
    private Users users;

    public Role(Users users, String name) {
        this.users = users; this.name = name;
    }

}
