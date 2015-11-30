/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         ObjectManager.java
 * Author:            Matt Schwartz
 * Date Created:      05.09.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.gameobjects;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObjectManager {

    public static final ObjectManager INSTANCE = new ObjectManager();

    private List<GameObject> gameObjects;

    private ObjectManager() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been instantiated.");
        } // if

        gameObjects = new CopyOnWriteArrayList<GameObject>();
    } // constructor

    public void spawnObject(GameObject object) {
        gameObjects.add(object);
    } // spawnObject

    public void tick(UpdateEvent args) {
        for (GameObject o : gameObjects) {
            if (o.shouldRemove()) {
                gameObjects.remove(o);
            } else {
                o.tick(args);
            }
        } // for
    } // tick

    /**
     * Performs a scene query, testing whether or not object1 can see object2
     * without having its vision obstructed.
     *
     * @param object1
     * @param object2
     * @return Returns true if object1 has unobstructed vision to object2
     */
    public boolean canSee(GameObject object1, GameObject object2) {
        return false;
    } // canSee

    /**
     * Performs a scene query, testing whether or not object1 is within a
     * specified distance from object2, defined by <code>reach</code>.
     *
     * @param object1
     * @param object2
     * @param reach The query distance between objects 1 and 2
     * @return Returns true if object1 can reach object2 within some specified
     * <code>reach</code>
     */
    public boolean canReach(GameObject object1, GameObject object2, double reach) {
        return false;
    } // canReach
} // ObjectManager
