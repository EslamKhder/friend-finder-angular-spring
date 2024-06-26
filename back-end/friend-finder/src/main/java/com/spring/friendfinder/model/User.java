package com.spring.friendfinder.model;


import com.spring.commonlib.model.BaseEntity;
import com.spring.commonlib.model.enums.Scope;
import com.spring.friendfinder.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User_System")
public class User extends BaseEntity {

    @Column(name = "user_id", unique = true)
    private Long userId;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private Scope scope;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Friend> friends;

}
