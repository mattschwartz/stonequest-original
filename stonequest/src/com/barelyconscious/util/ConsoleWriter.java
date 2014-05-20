/* *****************************************************************************
 * Project:           stonequest
 * File Name:         ConsoleWriter.java
 * Author:            Matt Schwartz
 * Date Created:      05.19.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.util;

public class ConsoleWriter {

    private static final int VERBOSITY_ERROR = 0;
    private static final int VERBOSITY_ALL = 1;

    private static int verbosity = VERBOSITY_ALL;

    public static void writeError(String msg) {
        System.err.println(" [ERR] " + msg);
    }

    public static void writeInfo(String msg) {
        if (verbosity >= VERBOSITY_ALL) {
            System.err.println(" [INF] " + msg);
        }
    }

} // ConsoleWriter
