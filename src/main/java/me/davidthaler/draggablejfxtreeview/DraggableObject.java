/*
 * File: DraggableObject.java
 * Copyright (C) 29/03/2021 David Thaler.
 * All rights reserved
 */
package me.davidthaler.draggablejfxtreeview;

import java.io.Serializable;

/**
 * Draggable Object interface to be used to create TreeItems with for DraggableCellFactory.
 * This is used to supply items such as the text and if it can contain children.
 * If this interface is not used the text of the cell will just be the toString of the object
 * inside the TreeItem.
 */
public interface DraggableObject extends Serializable {

    /**
     * The text to be displayed.
     * @return The String to be displayed.
     */
    String getText();

    /**
     * Can this object hold children inside it on the TreeView?
     * @return True for children, false otherwise.
     */
    boolean canContainChildren();

}
