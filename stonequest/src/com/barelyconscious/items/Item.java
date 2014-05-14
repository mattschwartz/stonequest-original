/* *****************************************************************************
 * Project:           stonequest
 * File Name:         Item.java
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

public class Item implements Comparable {

    private int itemLevel;
    private int stackSize;
    private int sellValue;
    private String name;

    public Item() {
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

} // Item
