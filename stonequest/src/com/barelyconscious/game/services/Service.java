/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         Service.java
 * Author:            Matt Schwartz
 * Date Created:      01.22.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  A Service is a vital class that runs continuously and
 *                    allows for some functionality to the game or as an 
 *                    interface between the player and the game.
 ************************************************************************** */
package com.barelyconscious.game.services;

public interface Service {

    /**
     * Starts the service. Should be called only once at the start of the
     * application unless the service needs to be restarted.
     */
    public void start();

    /**
     * Stops the service. Should be called to shut down the service, such as
     * when the player wishes to terminate the application or when the service
     * needs to be restarted.
     */
    public void stop();

    /**
     * Restarts the service. Should be called only if the service is behaving
     * incorrectly and as an alternative to restarting the entire application.
     * Also useful for changing settings without having to restart the
     * application.
     */
    public void restart();
} // Service
