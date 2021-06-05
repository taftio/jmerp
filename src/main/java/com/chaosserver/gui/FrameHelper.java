package com.chaosserver.gui;

import java.awt.Container;
import java.awt.Frame;

/**
 * Helper method for working with frames in the AWT and Swing world.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class FrameHelper {
    /**
     * Finds the top containing frame of the current container.
     * <p>
     * This method is useful when attempting to create a dialog
     * box.  It will work up the container hierarchy until it
     * finds the top level frame.
     * </p>
     *
     * @param container The container to start at
     * @return The top level frame for the container.
     */
    public static Frame getContainingFrame(Container container) {
        Container currContainer = container;

        while(!(currContainer instanceof Frame)) {
            currContainer = currContainer.getParent();
        }

        return (Frame) currContainer;

    }
}