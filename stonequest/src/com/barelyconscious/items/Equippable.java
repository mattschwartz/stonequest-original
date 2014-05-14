/* *****************************************************************************
   * Project:           stonequest
   * File Name:         Equippable.java
   * Author:            Matt Schwartz
   * Date Created:      05.11.2014 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
   *                    this file.  You are not allowed to take credit for code
   *                    that was not written fully by yourself, or to remove 
   *                    credit from code that was not written fully by yourself.  
   *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.items;

import java.util.ArrayList;
import java.util.List;

public class Equippable extends Item {

    private final List<Affix> itemAffixes;
    
    public Equippable() {
        itemAffixes = new ArrayList<>();
    }

    @Override
    public int compareTo(Object o) {
        return -1;
    }
} // Equippable
