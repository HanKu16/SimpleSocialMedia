package org.bd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post {

    private final int postId;
    private final String content;
    private final LocalDateTime creationDate;
    private final int userId;

}


