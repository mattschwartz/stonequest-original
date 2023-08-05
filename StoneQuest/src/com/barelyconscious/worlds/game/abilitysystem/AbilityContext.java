package com.barelyconscious.worlds.game.abilitysystem;

import com.barelyconscious.worlds.entity.Actor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class AbilityContext {

    @Getter
    private final Actor caster;
    @Getter
    private List<Actor> targets = new ArrayList<>();
    private Map<String, Object> context = new HashMap<>();

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
