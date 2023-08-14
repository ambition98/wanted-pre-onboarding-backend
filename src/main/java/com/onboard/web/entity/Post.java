package com.onboard.web.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "POST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String title;

    @Column(length = 1500)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account writer;
}
