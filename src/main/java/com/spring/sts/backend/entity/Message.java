package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sts_message")
public class Message extends BaseEntity {

    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User toUser;
    @Column(name = "send_date")
    private LocalDateTime sendDate;

}
