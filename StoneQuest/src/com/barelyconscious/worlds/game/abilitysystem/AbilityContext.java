package com.barelyconscious.worlds.game.abilitysystem;

import com.barelyconscious.worlds.entity.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class AbilityContext {

    @Getter
    private final Actor caster;
    @Getter
    private final List<Actor> targets;
    private final Map<String, Object> context;

    public enum ContextKey {
        CASTER_CONTEXT_KEY,
        TARGETS_CONTEXT_KEY
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }
}
