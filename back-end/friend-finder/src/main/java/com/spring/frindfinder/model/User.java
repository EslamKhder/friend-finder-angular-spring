package com.spring.frindfinder.model;


import com.spring.frindfinder.model.enums.Gender;

import java.util.Date;
import java.util.List;

public class User {

    private long id;

    private String firstName;

    private String lastName;

    private String age;

    private String phone;

    private Date date;

    private Gender gender;

    private List<Post> posts;

    private List<Friend> friends;

}
