package com.youcode.s3cur1ty.app.core.database.model.entity;

import com.youcode.s3cur1ty.app.core.database.model.wrapper.UserDetail;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class User {
    @Id
    private String sub;

    private String email;

    @Transient
    private UserDetail userDetail;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "sub"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Collection<Role> roles;

}
