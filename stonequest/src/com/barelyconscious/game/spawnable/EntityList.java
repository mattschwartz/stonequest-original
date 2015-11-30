/* *****************************************************************************
   * File Name:         EntityList.java
   * Author:            Matt Schwartz
   * Date Created:      10.22.2012
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  ArrayList to keep track of Entities currently in the world.
   ************************************************************************** */

package com.barelyconscious.game.spawnable;

import java.util.ArrayList;

public class EntityList {
    private Entity[] list;
    private int size;
    private int count = 0;
    
    public EntityList() {
        size = 100;
        list = new Entity[size];
    } // constructor
    
    public EntityList(int initSize) {
        size = initSize;
        list = new Entity[size];
    } // constructor
    
    public void add(Entity e) {
        if (count == size) {
            list = extendList();
        } // if
        list[count++] = e;
    } // add
    
    /* Returns the value removed */
    public Entity remove(int index) {
        Entity rem = list[index];
        
        for (int i = index; i < count; i++) {
            list[i] = list[i + 1];
        } // for
        
        count--;
        return rem; 
    } // remove
    
    public Entity remove(Entity e) {
        for (int i = 0; i <= count; i++) {
            if (list[i] == e) {
                return remove(i);
            } // if
        } // for
        
        return null;
    } // remove
    
    public int size() {
        return count;
    } // size
    
    public Entity get(int index) {
        return list[index];
    } // get
    
    public ArrayList getList(int x, int y) {
        ArrayList<Entity> list = new ArrayList();
        
        for (Entity e : this.list) {
//            if ( (e.getXPos() == x) && (e.getYPos() == y) ) {
//                list.add(e);
//            } // if
        } // for
        
        return list;
    } // getList
    
    /* Expensive operation */
    public Entity get(int x, int y) {
        Entity e;
        
        for (int i = 0; i < count; i++) {
            e = list[i];
//            if ((list[i].getXPos() == x) && (list[i].getYPos() == y))
//                return e;
        } // for
        
        return null;
    } // get
    
    private Entity[] extendList() {
        Entity[] newList = new Entity[size * 2];
        System.arraycopy(list, 0, newList, 0, size);
        return newList;
    } // extendList
} // EntityList
