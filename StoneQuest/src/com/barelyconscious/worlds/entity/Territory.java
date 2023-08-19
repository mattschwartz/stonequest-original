package com.barelyconscious.worlds.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Territory extends Actor{

    private final List<Building> buildings = new ArrayList<>();
}
