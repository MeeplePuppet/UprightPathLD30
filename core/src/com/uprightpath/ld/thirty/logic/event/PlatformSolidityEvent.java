package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.*;

/**
 * Created by Geo on 8/24/2014.
 */
public class PlatformSolidityEvent implements GlobalEvent, WorldEvent {

    private boolean solidity;
    private Platform platform;

    public PlatformSolidityEvent() {
    }

    public PlatformSolidityEvent(Platform platform, boolean solidity) {
        this.platform = platform;
        this.solidity = solidity;
    }

    public void setSolidity(boolean solidity) {
        this.solidity = solidity;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public void trigger(World world) {
        triggerEvent(world);
    }

    private void triggerEvent(World world) {
        if (solidity) {
            world.addPlatform(platform);
            if (platform.getRenderer() != null) {
                world.addRenderable(platform);
            }
        } else {
            world.removePlatform(platform);
            if (platform.getRenderer() != null) {
                world.removeRenderable(platform);
            }
        }
    }

    @Override
    public void trigger(World world, Agent agent) {
        triggerEvent(world);
    }
}
