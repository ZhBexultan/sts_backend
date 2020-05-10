package com.spring.sts.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.sts.backend.entity.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserShortDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String imageUrl;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setImageUrl(imageUrl);
        return user;
    }

    public static UserShortDto fromUser(User user) {
        UserShortDto userDto = new UserShortDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setImageUrl(user.getImageUrl());
        return userDto;
    }

}
