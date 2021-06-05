package com.chaosserver.merp.gui.swing.image;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.lang.ref.SoftReference;

import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.logging.CategoryCache;

/**
 * A canvas that will display the MerpLogo image.
 * <p>
 * This object a singleton with a soft reference to the image itself.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class MerpLogoCanvas extends Canvas {
    /** Singleton self reference */
    private static SoftReference self;

    /** Reference to the image held */
    protected Image m_merpImage;

    /** Protected constructor to force all access through the instance method */
    protected MerpLogoCanvas() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        m_merpImage = toolkit.createImage(FileNameGetter.getResourceURL(FileNameGetter.IMAGE_MERP_LOGO));
    }

    /**
     * Paints the image of the Merp Logo on itself
     *
     * @param g The context to draw in
     */
    public void paint(Graphics g) {
        g.drawImage(m_merpImage, 0, 0, this);
    }

    /**
     * Returns the preferred size of the image
     * <p>
     * BUGBUG: Hardcoded.
     * </p>
     *
     * @return The size of the image
     */
    public Dimension getPreferredSize() {
        //return new Dimension(m_merpImage.getWidth(this), m_merpImage.getHeight(this));
        return new Dimension(265, 75);
    }


    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static MerpLogoCanvas getInstance() {
        if(self == null || self.get() == null) {
            CategoryCache.getInstance(MerpLogoCanvas.class).info("Building from file");
            self = new SoftReference(new MerpLogoCanvas());
        }

        return (MerpLogoCanvas) self.get();

    }
}