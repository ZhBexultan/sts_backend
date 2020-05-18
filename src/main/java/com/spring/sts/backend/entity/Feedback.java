package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_feedback")
public class Feedback extends BaseEntity {

    @Column(name = "comment", length = 4096)
    private String comment;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

}
