/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         FileHandler.java
 * Author:            Matt Schwartz
 * Date Created:      03.16.2013 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.file;

import com.barelyconscious.game.services.Service;
import java.io.File;

public class FileHandler implements Service {

    public static final FileHandler INSTANCE = new FileHandler();
    public static char delimiter = '/';
    public static String homeDirectory;

    private FileHandler() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Only one instance per runtime.");
        }
    }

    public File getScreenshotDir() {
        File dir = new File(homeDirectory + delimiter + "screenshots");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    @Override
    public void start() {
        System.err.println(" [NOTIFY] Initializing file handler.");
        homeDirectory = System.getProperty("user.home");
        if (System.getProperty("os.name").contains("Windows")) {
            delimiter = '\\';
            homeDirectory += delimiter + "Documents";
        }

        homeDirectory += delimiter + "barelyconscious" + delimiter + "StoneQuest" + delimiter;
    }

    @Override
    public void stop() {
    }

    @Override
    public void restart() {
        start();
        stop();
    }
}
