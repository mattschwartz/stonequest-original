/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         StringHelper.java
   * Author:            Matt Schwartz
   * Date Created:      12.03.2013 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.util;

import java.util.ArrayList;
import java.util.List;

public class StringHelper {
    private static List<Character> vowels = new ArrayList<Character>() {{
        add('a');
        add('e');
        add('i');
        add('o');
        add('u');
    }};
    
    public static String aOrAn(String str) {
        if (vowels.contains(str.charAt(0))) {
            return "an " + str;
        } // if
        
        return "a " + str;
    } // aOrAn
} // StringHelper
