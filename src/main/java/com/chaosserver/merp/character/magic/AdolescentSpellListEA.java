package com.chaosserver.merp.character.magic;

import com.chaosserver.merp.character.attribute.ExtendedAttributeBase;

/**
 * Extended attributes that holds the result of a character's roll for
 * their adolescent spell list.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class AdolescentSpellListEA extends ExtendedAttributeBase {
    /** Did this character successfully roll for an adolescent spell list. */
    protected boolean adolescentSpellList;

    /**
     * Default constructor.
     */
    public AdolescentSpellListEA() {
    }

    /**
     * Main constructor to fully build the EA.
     *
     * @param name The name of the EA
     * @param adolescentSpellList Does this character have the list
     * @param expiration Level of expiration
     */
    public AdolescentSpellListEA(String name, boolean adolescentSpellList, int expiration) {
        setName(name);
        setAdolescentSpellList(adolescentSpellList);
        setExpiration(expiration);
    }

    /**
     * Setter for AdolescentSpellList.
     *
     * @param adolescentSpellList Value of adolescentSpellList
     * @see #hasAdolescentSpellList
     */
    public void setAdolescentSpellList(boolean adolescentSpellList) {
        this.adolescentSpellList = adolescentSpellList;
    }

    /**
     * Getter for AdolescentSpellList.
     *
     * @return Value of adolescentSpellList
     * @see #setAdolescentSpellList
     */
     public boolean hasAdolescentSpellList() {
        return this.adolescentSpellList;
    }

}