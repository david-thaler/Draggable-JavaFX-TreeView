/*
 * File: DraggableCellFactory.java
 * Copyright (C) 29/03/2021 David Thaler.
 * All rights reserved
 */
package me.davidthaler.draggablejfxtreeview;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.List;

public class DraggableCellFactory implements Callback<TreeView, TreeCell> {

    /**
     * Java format for the Dragboard.
     */
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    /**
     * Styling for indicating the item will drop below.
     */
    private static final String BOTTOM_DROP_LINE_STYLE
            = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3";
    /**
     * Styling for indicating the item will drop above.
     */
    private static final String TOP_DROP_LINE_STYLE
            = "-fx-border-color: #eea82f; -fx-border-width: 2 0 0 0; -fx-padding: 1 3 3 3";
    /**
     * The TreeCell that is the currently being targeted to be dropped on.
     */
    private TreeCell dropTarget;
    /**
     * The TreeItem currently being dragged.
     */
    private TreeItem draggedItem;

    /**
     * {@inheritDoc}
     */
    public TreeCell call(final TreeView treeView) {
        TreeCell cell = new TextFieldTreeCell() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void updateItem(final Object item, final boolean empty) {
                super.updateItem(item, empty);
                if (item == null) return;

                if (!empty && item != null) {
                    if (item instanceof DraggableObject) {
                        DraggableObject obj = (DraggableObject) item;
                        setText(obj.getText());
                    } else {
                        setText(item.toString());
                    }
                    setGraphic(getTreeItem().getGraphic());
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        };
        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());
        return cell;
    }

    /**
     * Called when an item from the tree is dragged.
     * @param event The MouseEvent generated.
     * @param treeCell The TreeCell that is being dragged.
     * @param treeView The TreeView this is being dragged on.
     */
    private void dragDetected(final MouseEvent event, final TreeCell treeCell, final TreeView treeView) {

    }

    /**
     * Called when the an area is being dragged over.
     * @param event The DragEvent generated.
     * @param treeCell The TreeCell that is being dragged over.
     * @param treeView The TreeView that is being dragged on.
     */
    private void dragOver(final DragEvent event, final TreeCell treeCell, final TreeView treeView) {

    }

    /**
     * Called when the Drag event ends and is being dropped.
     * @param event The DragEvent generated.
     * @param treeCell The TreeCell that is being dropped onto.
     * @param treeView The TreeView that is being dropped onto.
     */
    private void drop(final DragEvent event, final TreeCell treeCell, final TreeView treeView) {

    }

    /**
     * Clears the drop styling.
     */
    private void clearDropLocation() {
        if (dropTarget != null) dropTarget.setStyle("");
    }

}