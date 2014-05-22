/* *****************************************************************************
 * Project:           stonequest
 * File Name:         TorchDoodadObject.java
 * Author:            Matt Schwartz
 * Date Created:      05.21.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gameobjects;

import com.barelyconscious.doodads.Doodad;

public class TorchDoodadObject extends DoodadObject {

    private LightObject lightSource;
    private boolean glowOut = false;

    public TorchDoodadObject(Doodad doodad, float x, float y) {
        super(doodad, "sprites/doodads/torch.png", x, y);
        frameTime = 360;
    }

    @Override
    public void spawnObject() {
        super.spawnObject();
        lightSource = LightManager.getInstance().addLight(x, y, 250);
    }

    @Override
    public void update(UpdateEvent args) {
        super.update(args);
        
        float delta;
        
        if (glowOut) {
            delta = lightSource.getRadius() + args.delta * 0.5f;
            lightSource.setRadius(delta);
            
            if (lightSource.getRadius() > 260) {
                glowOut = false;
            }
        } else {
            delta = lightSource.getRadius() - args.delta * 0.5f;
            lightSource.setRadius(delta);
            
            if (lightSource.getRadius() < 240) {
                glowOut = true;
            }
            
        }

        lightSource.setX(renderX + itemAnimation.getWidth() / 2);
        lightSource.setY(renderY + itemAnimation.getHeight() / 2);

        itemAnimation.update(args.delta);
    }

    @Override
    public void render(UpdateEvent args) {
        itemAnimation.draw(renderX, renderY);
    }

} // TorchDoodadObject
