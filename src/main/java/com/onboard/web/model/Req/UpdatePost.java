package com.onboard.web.model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePost {
    private Long id;
    private String title;
    private String content;
}
