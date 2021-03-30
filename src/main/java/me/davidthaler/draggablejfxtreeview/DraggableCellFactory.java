/*
 * File: DraggableCellFactory.java
 * Copyright (C) 29/03/2021 David Thaler.
 * All rights reserved
 */
package me.davidthaler.draggablejfxtreeview;

import javafx.geometry.Point2D;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.*;
import javafx.util.Callback;

public class DraggableCellFactory implements Callback<TreeView, TreeCell> {

    /**
     * Java format for the Dragboard.
     */
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    /**
     * Styling for indicating the item will drop below.
     */
    private static final String BOTTOM_DROP_LINE_STYLE
            = "-fx-border-insets: 0px 0px 0px %dpx, 0px 0px 0px 0px; -fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 %dpx";
    /**
     * Styling for indicating the item will drop above.
     */
    private static final String TOP_DROP_LINE_STYLE
            = "-fx-border-insets: 0px 0px 0px %dpx, 0px 0px 0px 0px; -fx-border-color: #eea82f; -fx-border-width: 2 0 0 0; -fx-padding: 1 3 3 %dpx";
    /**
     * The TreeItem that is to be dropped into as a child of.
     */
    private TreeItem dropParent;
    /**
     * The TreeCell that is current styled.
     */
    private TreeCell styledCell;
    /**
     * The index inside the dropParent where the item should be placed.
     */
    private int dropIndex;
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
        //Cannot drag blank cell.
        if (treeCell == null) return;

        //Cannot drag root cell.
        if (treeCell.getParent() == null) return;

        //Select this cell to be dragged.
        Dragboard db = treeCell.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        draggedItem = treeCell.getTreeItem();
        content.put(JAVA_FORMAT, draggedItem.getValue());
        db.setContent(content);
        db.setDragView(treeCell.snapshot(null, null));
        event.consume();
    }

    /**
     * Called when the an area is being dragged over.
     * @param event The DragEvent generated.
     * @param treeCell The TreeCell that is being dragged over.
     * @param treeView The TreeView that is being dragged on.
     */
    private void dragOver(final DragEvent event, final TreeCell treeCell, final TreeView treeView) {
        //Nothing valid is being dragged.
        if (draggedItem == null) return;

        TreeItem thisItem = treeCell.getTreeItem();

        // Cannot drop on itself.
        if (draggedItem != null && thisItem != null && thisItem == draggedItem) return;

        // Dropping on blank just sets it to the bottom
        if (thisItem == null) {
            dropParent = treeView.getRoot();
            dropIndex = -1;
            event.acceptTransferModes(TransferMode.MOVE);
            return;
        }

        // Cannot drop onto a child of itself.
        if (childrenContains(draggedItem, thisItem)) return;

        // get the y location of the cursor in relation to the current cell.
        Point2D sceneCoordinates = treeCell.localToScene(0d, 0d);
        double height = treeCell.getHeight();
        double y = event.getSceneY() - sceneCoordinates.getY();

        // Allow the drag over to continue.
        clearDropLocation();
        event.acceptTransferModes(TransferMode.MOVE);

        // Determine where the cell will be dropped.
        dropParent = thisItem.getParent();
        styledCell = treeCell;

        int parentCount = countParents(styledCell.getTreeItem());
        // Remove root node from the count.
        parentCount--;
        int indent = parentCount * 18;
        int negativePadding = 3 - indent;

        if (y < (height * .5d)) {
            dropIndex = dropParent.getChildren().indexOf(thisItem);
            styledCell.setStyle(String.format(TOP_DROP_LINE_STYLE, indent, negativePadding));
        } else {
            dropIndex = dropParent.getChildren().indexOf(thisItem) + 1;
            styledCell.setStyle(String.format(BOTTOM_DROP_LINE_STYLE, indent, negativePadding));
            if (thisItem.getValue() instanceof DraggableObject) {
                DraggableObject dragObj = (DraggableObject) thisItem.getValue();
                if (dragObj.canContainChildren()) {
                    dropParent = thisItem;
                    dropIndex = 0;
                }
            }
        }
    }

    /**
     * Counts the quantity of parents of the TreeItem passed.
     * @param child The child TreeItem.
     * @return Quantity of parents.
     */
    private int countParents(final TreeItem child) {
        int ret = 0;
        TreeItem current = child;
        while (current.getParent() != null) {
            current = current.getParent();
            ret++;
        }
        return ret;
    }

    /**
     * Checks to see if the targetItem contains the searchingFor inside it's recursive children.
     * @param targetItem The target TreeItem to search in.
     * @param searchingFor The TreeItem to search for.
     */
    private boolean childrenContains(final TreeItem targetItem, final TreeItem searchingFor) {
        boolean ret = false;
        for (Object obj : targetItem.getChildren()) {
            if (obj instanceof TreeItem) {
                TreeItem child = (TreeItem) obj;
                if (child == searchingFor) {
                    ret = true;
                    break;
                } else if (child.getChildren().size() != 0) {
                    ret = childrenContains(child, searchingFor);
                    if (ret) break;
                } else {
                    ret = false;
                }
            }
        }
        return ret;
    }

    /**
     * Called when the Drag event ends and is being dropped.
     * @param event The DragEvent generated.
     * @param treeCell The TreeCell that is being dropped onto.
     * @param treeView The TreeView that is being dropped onto.
     */
    private void drop(final DragEvent event, final TreeCell treeCell, final TreeView treeView) {
        //Nothing valid is being dragged.
        if (draggedItem == null) return;

        //Remove dragged item from the old parent.
        draggedItem.getParent().getChildren().remove(draggedItem);

        //Add item to new location.
        if (dropIndex != -1) {
            dropParent.getChildren().add(dropIndex, draggedItem);
        } else {
            dropParent.getChildren().add(draggedItem);
        }

        //Select the new location.
        treeView.getSelectionModel().select(draggedItem);

        //Accept the event.
        event.setDropCompleted(true);
    }

    /**
     * Clears the drop styling.
     */
    private void clearDropLocation() {
        if (styledCell != null) styledCell.setStyle("");
    }

}