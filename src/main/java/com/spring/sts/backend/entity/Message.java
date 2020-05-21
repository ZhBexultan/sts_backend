package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sts_message")
public class Message extends BaseEntity {

    @Column(name = "message")
    private String message;
    @Column(name = "from_login")
    private String fromLogin;

}
