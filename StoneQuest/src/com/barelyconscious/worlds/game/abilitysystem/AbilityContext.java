package com.barelyconscious.worlds.game.abilitysystem;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.game.World;
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
    private final World world;
    @Getter
    private final Actor caster;
    @Getter
    private List<Actor> targets;
    private Map<String, Object> context;

    public enum ContextKey {
        CASTER_CONTEXT_KEY,
        TARGETS_CONTEXT_KEY
    }

    public void addTarget(Actor... targets) {
        if (this.targets == null) {
            this.targets = new ArrayList<>();
        }
        for (var target : targets) {
            this.targets.add(target);
        }
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }
}
