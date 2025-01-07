package org.bd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Like {

    private final int id;
    private final int postId;
    private final String userId;

}
