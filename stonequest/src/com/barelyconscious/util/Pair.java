/* *****************************************************************************
   * Project:           stonequest
   * File Name:         Pair.java
   * Author:            Matt Schwartz
   * Date Created:      05.20.2014 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
   *                    this file.  You are not allowed to take credit for code
   *                    that was not written fully by yourself, or to remove 
   *                    credit from code that was not written fully by yourself.  
   *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.util;

public class Pair<T, U> {
    public T first;
    public U second;
    
    public Pair() {
    }
    
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return this + ": [first: " + first + ", second: " + second + "]";
    }
    
} // Pair
