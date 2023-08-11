package com.onboard.web.entity;

import de.huxhorn.sulky.ulid.ULID;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "BOARD")
@Getter
public class BoardEntity extends BaseEntity {

    public BoardEntity() {
        this.id = new ULID().nextULID();
    }

    @Id
    @Column(columnDefinition = "CHAR(26)")
    private String id;

    @Column(length = 128)
    private String title;

    @Column(length = 1500)
    private String content;


}
