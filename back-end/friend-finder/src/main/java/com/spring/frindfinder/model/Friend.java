package com.spring.frindfinder.model;

import javax.persistence.*;

@Entity(name = "friends")
public class Friend extends BaseEntity {

    @Column(name = "friend_id")
    private int idFriend;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
