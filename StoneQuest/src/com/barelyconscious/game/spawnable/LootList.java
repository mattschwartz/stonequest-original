/* *****************************************************************************
   * File Name:         LootList.java
   * Author:            Matt Schwartz
   * Date Created:      02.19.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  ArrayList to keep track of Loot objects currently in the world.
   ************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.item.Item;
import java.util.ArrayList;

public class LootList {
    private Loot[] list;
    private int size;
    private int count = 0;
    
    public LootList() {
        size = 100;
        list = new Loot[size];
    } // constructor
    
    public LootList(int initSize) {
        size = initSize;
        list = new Loot[size];
    } // constructor
    
    public void add(Loot e) {
        if (count == size) {
            list = extendList();
        } // if
        list[count++] = e;
    } // add
    
    /* Returns the value removed */
    public Loot remove(int index) {
        Loot rem = list[index];
        
        for (int i = index; i < count; i++) {
            list[i] = list[i + 1];
        } // for
        
        count--;
        return rem; 
    } // remove
    
    public Loot remove(Loot e) {
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
    
    public Loot get(int index) {
        return list[index];
    } // get
    
    public ArrayList<Loot> getList(int x, int y) {
        ArrayList<Loot> list = new ArrayList<>();
        
        for (Loot e : this.list) {
            if ( e != null && (e.getXPos() == x) && (e.getYPos() == y) ) {
                list.add(e);
            } // if
        } // for
        
        return list;
    } // getList
    
    public ArrayList<Item> getListAsItem(int x, int y) {
        ArrayList<Item> list = new ArrayList<>();
        
        for (Loot e : this.list) {
            if ( e != null && (e.getXPos() == x) && (e.getYPos() == y) ) {
                list.add(e.getItem());
            } // if
        } // for
        
        return list;
    } // getListAsItem
    
    /* Expensive operation */
    public Loot get(int x, int y) {
        Loot e;
        
        for (int i = 0; i < count; i++) {
            e = list[i];
            if ((list[i].getXPos() == x) && (list[i].getYPos() == y))
                return e;
        } // for
        
        return null;
    } // get
    
    private Loot[] extendList() {
        Loot[] newList = new Loot[size * 2];
        System.arraycopy(list, 0, newList, 0, size);
        return newList;
    } // extendList
} // LootList
