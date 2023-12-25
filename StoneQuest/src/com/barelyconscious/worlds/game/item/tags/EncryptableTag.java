package com.barelyconscious.worlds.game.item.tags;

import com.barelyconscious.worlds.game.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EncryptableTag implements ItemTag {

    ENCRYPTED("Encrypted");

    private final String tagName;
}
