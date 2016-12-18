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
//    private Loot[] list;
//    private int size;
//    private int count = 0;
//    
//    public LootList() {
//        size = 100;
//        list = new Loot[size];
//    }
//    
//    public LootList(int initSize) {
//        size = initSize;
//        list = new Loot[size];
//    }
//    
//    public void add(Loot e) {
//        if (count == size) {
//            list = extendList();
//        }
//        list[count++] = e;
//    }
//    
//    /* Returns the value removed */
//    public Loot remove(int index) {
//        Loot rem = list[index];
//        
//        for (int i = index; i < count; i++) {
//            list[i] = list[i + 1];
//        }
//        
//        count--;
//        return rem; 
//    }
//    
//    public Loot remove(Loot e) {
//        for (int i = 0; i <= count; i++) {
//            if (list[i] == e) {
//                return remove(i);
//            }
//        }
//        
//        return null;
//    }
//    
//    public int size() {
//        return count;
//    }
//    
//    public Loot get(int index) {
//        return list[index];
//    }
//    
//    public ArrayList getList(int x, int y) {
//        ArrayList<Loot> list = new ArrayList();
//        
//        for (Loot e : this.list) {
//            if ( e != null && (e.getXPos() == x) && (e.getYPos() == y) ) {
//                list.add(e);
//            }
//        }
//        
//        return list;
//    }
//    
//    public ArrayList getListAsItem(int x, int y) {
//        ArrayList<Item> list = new ArrayList();
//        
//        for (Loot e : this.list) {
//            if ( e != null && (e.getXPos() == x) && (e.getYPos() == y) ) {
//                list.add(e.getItem());
//            }
//        }
//        
//        return list;
//    }
//    
//    /* Expensive operation */
//    public Loot get(int x, int y) {
//        Loot e;
//        
//        for (int i = 0; i < count; i++) {
//            e = list[i];
//            if ((list[i].getXPos() == x) && (list[i].getYPos() == y))
//                return e;
//        }
//        
//        return null;
//    }
//    
//    private Loot[] extendList() {
//        Loot[] newList = new Loot[size * 2];
//        System.arraycopy(list, 0, newList, 0, size);
//        return newList;
//    }
}
