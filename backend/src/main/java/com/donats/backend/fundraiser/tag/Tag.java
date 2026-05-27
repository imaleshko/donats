package com.donats.backend.fundraiser.tag;

public record Tag(String name) {
    public static Tag from(TagEntity tagEntity) {
        return new Tag(
                tagEntity.getName()
        );
    }
}
