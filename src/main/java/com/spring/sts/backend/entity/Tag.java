package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_tag")
public class Tag extends BaseEntity{

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "sts_blog_tags",
            joinColumns = { @JoinColumn(name = "blog_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    Set<Blog> blogs = new HashSet<>();

}
