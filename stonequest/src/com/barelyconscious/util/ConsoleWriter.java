/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         ConsoleWriter.java
 * Author:            Matt Schwartz
 * Date Created:      03.02.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.util;

public class ConsoleWriter {

    public static final int VERBOSE_ALL = 2;
    public static final int VERBOSE_MOST = 1;
    public static final int VERBOSE_LOW = 0;
    public static int verbosity = VERBOSE_ALL;

    public static void writeStr(String str) {
        if (verbosity >= VERBOSE_ALL) {
            System.out.println(" [INF] " + str);
        }
    }

    public static void writeError(String str) {
        if (verbosity >= VERBOSE_LOW) {
            System.out.println(" [ERR] " + str);
        }
    }

}

