package com.barelyconscious.worlds;

import com.barelyconscious.worlds.engine.graphics.Screen;
import com.barelyconscious.worlds.engine.opengl.OpenGLScreen;
import org.lwjgl.Version;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class GameRunnerOpenGL {

    private final OpenGLScreen screen;

    public GameRunnerOpenGL(final OpenGLScreen screen) {
        this.screen = screen;
    }

    public static void main(String[] args) {
        new GameRunnerOpenGL(new OpenGLScreen()).run();
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
