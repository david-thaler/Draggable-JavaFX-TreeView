/*
 * File: DraggableChild.java
 * Copyright (C) 29/03/2021 David Thaler.
 * All rights reserved
 */
package me.davidthaler.draggablejfxtreeview;

/**
 * Example/Basic draggable child object. This is used to just display text and
 * prevent any items being added as children.
 */
public class DraggableChild implements DraggableObject {

    /** The text that will be displyaed. */
    private String text;

    /** Blank constructor. */
    public DraggableChild() {}

    /**
     * Constructor that allows direct assignment of the text.
     * @param displayText The text to be displayed.
     */
    public DraggableChild(final String displayText) {
        text = displayText;
    }

    /**
     * Sets the text to be displayed on the TreeView cell.
     * @param displayText The text to be displayed.
     */
    public void setText(final String displayText) {
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
        return false;
    }

}
