/* *****************************************************************************
   * Project:           stonequest
   * File Name:         LoadingMenuWorker.java
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

public class LoadingMenuWorker implements Runnable {

    public String name = "resources";
    public String description = "loads resources";
    
    public LoadingMenuWorker() {
    }
    
    public LoadingMenuWorker(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
    }

} // LoadingMenuWorker
