package com.chaosserver.merp.gui.swing.specialabilities;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.rules.specialabilities.SpecialAbility;

import java.util.Map;

public class SpecialAbilityPanelFactory {
    /** Singleton self reference. */
    private static SpecialAbilityPanelFactory s_self;

    /** Map of the special abilities to the panel classes */
    protected Map panels;

    /** Protected constructor to force all access through the instance method. */
    public SpecialAbilityPanelFactory() {

    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SpecialAbilityPanelFactory instance() {
        if(s_self == null) {
            try {
                s_self = (SpecialAbilityPanelFactory) JavaBeanLoader.getBean(FileNameGetter.XML_SPECIAL_ABILITY_PANEL_FACTORY);
            } catch (JavaBeanLoaderExceptionX e) {
                throw new LoadErrorRX("Problem loading: '" + FileNameGetter.XML_SPECIAL_ABILITY_PANEL_FACTORY + "', " + e.toString());
            }
        }

        return s_self;
    }

    public void setPanels(Map panels) {
        this.panels = panels;
    }

    public Map getPanels() {
        return this.panels;
    }

    public SpecialAbilityPanel makePanel(MerpCharacter character, SpecialAbility specialAbility) {
        SpecialAbilityPanel specialAbilityPanel = null;
        try {
            String searchString = specialAbility.getClass().getName();
            if(!panels.containsKey(searchString)) {
                CategoryCache.getInstance(this).error("Missing key: " + searchString);
            }
            else {
                String panelClassName = (String) panels.get(searchString);
                Class classInstance = Class.forName(panelClassName);
                specialAbilityPanel = (SpecialAbilityPanel) classInstance.newInstance();
                specialAbilityPanel.setSpecialAbility(specialAbility);

                CategoryCache.getInstance(this).info("Got: " + specialAbilityPanel);
            }
        }
        catch(ClassNotFoundException e) {
            Assertion.shouldNotReach(e.toString());
        }
        catch(InstantiationException e) {
            Assertion.shouldNotReach(e.toString());
        }
        catch(IllegalAccessException e) {
            Assertion.shouldNotReach(e.toString());
        }

        return specialAbilityPanel;
    }
}