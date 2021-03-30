/*
 * File: DraggableFolder.java
 * Copyright (C) 29/03/2021 David Thaler.
 * All rights reserved
 */
package me.davidthaler.draggablejfxtreeview;

/**
 * Example/Basic draggable folder object. This is used to display text and hold children objects.
 */
public class DraggableFolder implements DraggableObject {

    /** Text to be displayed on the TreeView cell. */
    private String text;

    /** Blank constructor. */
    public DraggableFolder() {}

    /**
     * Constructor that allows direct assignment of the text.
     * @param displayText The text to be displayed.
     */
    public DraggableFolder(final String displayText) {
        text = displayText;
    }

    /**
     * Sets the text to be displayed on the TreeView cell.
     * @param displayText The text to be displayed.
     */
    public void setDisplayText(final String displayText) {
        text = displayText;
    }

    /**
     * {@inheritDoc}
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getText();
    }


    /**
     * {@inheritDoc}
     */
    public boolean canContainChildren() {
        return true;
    }

}
