package com.barelyconscious.worlds;

import com.barelyconscious.worlds.engine.opengl.GLScreen;
import org.lwjgl.Version;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class GameRunnerOpenGL {

    private final GLScreen screen;

    public GameRunnerOpenGL(final GLScreen screen) {
        this.screen = screen;
    }

    public static void main(String[] args) {
        new GameRunnerOpenGL(new GLScreen()).run();
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();

        System.out.println("JVM arguments:" + arguments);

        this.screen.initOpenGL();
        this.screen.loop();

        this.screen.freeOpenGL();
    }
}
