package com.spring.friendfinder.model;
import com.spring.commonlib.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Post")
public class Post extends BaseEntity {


    @Column(name = "image_path")
    private String image;

    @Column(name = "text")
    private String text;

    @Column(name = "likes")
    private int likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
