package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
class ImageBase extends BaseEntity {

    @Column(name = "firebase_id", length = 8000)
    private String firebaseId;

    @Column(name = "url", nullable = false, length = 512)
    private String url;

}
