/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        MouseHandler.java
 * Author:           Matt Schwartz
 * Date created:     08.28.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.input;

import com.barelyconscious.game.graphics.gui.Cursors;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    private int mouseX;
    private int mouseY;
    private SortedMap<Integer, List<Interactable>> interactableObjects = new TreeMap<Integer, List<Interactable>>();

    public void addInteractable(Interactable interactableObject, int zLevel) {
        // Should automatically be ordered when objects are added
        if (!interactableObjects.containsKey(zLevel)) {
            interactableObjects.put(zLevel, new ArrayList<Interactable>());
        } // if

        if (!interactableObjects.get(zLevel).contains(interactableObject)) {
            interactableObjects.get(zLevel).add(interactableObject);
        } // if
    } // addInteractable 

    public void removeInteractable(Interactable interactableObject) {
        for (Integer zLevel : interactableObjects.keySet()) {
            if (interactableObjects.get(zLevel).contains(interactableObject)) {
                interactableObjects.get(zLevel).remove(interactableObject);
            } // if
        } // for
    } // removeInteractable
    
    public int getMouseX() {
        return mouseX;
    } // getMouseX
    
    public int getMouseY() {
        return mouseY;
    } // getMouseY

    /**
     * Find the first interactable object, sorted by zLevel and pass the mouse
     * event to that object.
     *
     * @param e the MouseEvent passed from the MouseListener class
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Interactable interactable;

        Cursors.setCursor(Cursors.DEFAULT_CURSOR);

        // Figure out the topmost interactable object to send the event to
        for (Integer zLevel : interactableObjects.keySet()) {
            for (int i = 0; i < interactableObjects.get(zLevel).size(); i++) {
                interactable = interactableObjects.get(zLevel).get(i);
                interactable.setFocus(false);

                if (!interactable.isEnabled()) {
                    continue;
                } // if

                if (interactable.contains(e.getX(), e.getY())) {
                    if (!interactable.isMouseInFocus()) {
                        interactable.mouseEntered();
                    } // if

                    interactable.mouseClicked(e);
                    return;
                } // if
            } // for
        } // for
    } // mouseClicked

    @Override
    public void mousePressed(MouseEvent e) {
        boolean found = false;
        Interactable interactable;

        for (Integer zLevel : interactableObjects.keySet()) {
            for (int i = 0; i < interactableObjects.get(zLevel).size(); i++) {
                interactable = interactableObjects.get(zLevel).get(i);

                if (!interactable.isEnabled()) {
                    continue;
                } // if

                if (interactable.contains(e.getX(), e.getY())) {

                    if (interactable.isMouseInFocus()) {
                        interactable.mouseEntered();
                    } // if

                    interactable.mousePressed(e);
//                    return;
                    found = true;
                } // if
                else if (!found && !interactable.isMouseInFocus()) {
                    interactable.mouseExited();
                } // else if
            } // for
        } // for
    } // mousePressed

    @Override
    public void mouseReleased(MouseEvent e) {
        boolean found = false;
        Interactable interactable;

        for (Integer zLevel : interactableObjects.keySet()) {
            for (int i = 0; i < interactableObjects.get(zLevel).size(); i++) {
                interactable = interactableObjects.get(zLevel).get(i);

                if (!interactable.isEnabled()) {
                    continue;
                } // if

                interactable.mouseReleased(e);

                if (interactable.contains(e.getX(), e.getY())) {
                    if (interactable.isMouseInFocus()) {
                        interactable.mouseEntered();
                    } // if
                    found = true;
                    break;
                } // if
                else if (!interactable.contains(e.getX(), e.getY())) {
                    interactable.mouseExited();
                }
            } // for
            if (found) {
                break;
            }
        } // for
    } // mouseReleased

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean found = false;
        Interactable interactable;
        mouseX = e.getX();
        mouseY = e.getY();

        try {
            // Figure out the topmost interactable object to send the event to
            for (Integer zLevel : interactableObjects.keySet()) {
                for (int i = 0; i < interactableObjects.get(zLevel).size(); i++) {
                    interactable = interactableObjects.get(zLevel).get(i);

                    if (!interactable.isEnabled()) {
                        continue;
                    } // if

                    if (!found && interactable.contains(e.getX(), e.getY())) {
                        
                        if (!interactable.isMouseInFocus()) {
                            interactable.mouseEntered();
                        } // if

                        interactable.mouseMoved(e);

                    found = true;
                    } // if
                    else if (interactable.isMouseInFocus()) {
                        interactable.mouseExited();
                    } // if
                } // for
            } // for
        } catch (ConcurrentModificationException ex) {
        }
    } // mouseMoved

    @Override
    public void mouseDragged(MouseEvent e) {
        boolean found = false;
        Interactable interactable;
        
        // Figure out the topmost interactable object to send the event to
        for (Integer zLevel : interactableObjects.keySet()) {
            for (int i = 0; i < interactableObjects.get(zLevel).size(); i++) {
                interactable = interactableObjects.get(zLevel).get(i);

                if (!interactable.isEnabled()) {
                    continue;
                } // if
                
                if (!found && interactable.contains(e.getX(), e.getY())) {

                    if (!interactable.isMouseInFocus()) {
                        interactable.mouseEntered();
                    } // if

                    interactable.mouseDragged(e);

                    found = true;
                } // if
                else if (interactable.isMouseInFocus()) {
                    interactable.mouseExited();
                } // if
            } // for
        } // for
    } // mouseDragged

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Interactable interactable;

        for (int i = 0; i < interactableObjects.get(Interactable.Z_TEXT_AREA).size(); i++) {
            interactable = interactableObjects.get(Interactable.Z_TEXT_AREA).get(i);

            if (!interactable.isEnabled()) {
                continue;
            } // if
            
            if (interactable.contains(e.getX(), e.getY())) {
                if (e.getWheelRotation() < 0) {
                    interactable.mouseWheelUp();
                } // if

                if (e.getWheelRotation() > 0) {
                    interactable.mouseWheelDown();
                } // if
                return;
            } // if
        } // for
    } // mouseWheelMoved

    // Unused ------------------------------------------------------------------
    @Override
    public void mouseEntered(MouseEvent e) {
    } // mouseEntered

    @Override
    public void mouseExited(MouseEvent e) {
    } // mouseExited
} // MouseHandler
