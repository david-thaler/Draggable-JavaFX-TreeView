/*
 * File: ExampleApp.java
 * Copyright (C) 30/03/2021 David Thaler.
 * All rights reserved
 */
package me.davidthaler.draggablejfxtreeview.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import me.davidthaler.draggablejfxtreeview.DraggableCellFactory;
import me.davidthaler.draggablejfxtreeview.DraggableChild;
import me.davidthaler.draggablejfxtreeview.DraggableFolder;

/**
 * Example JavaFX App to display the Draggable JavaFX TreeView.
 */
public class ExampleApp extends Application {

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Building TreeView instance.
        TreeView treeView = new TreeView();
        treeView.setCellFactory(new DraggableCellFactory());
        // Root TreeItem node.
        TreeItem root = new TreeItem("Root Node");
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        // Folder icon image
        Image folderImage = new Image(getClass().getResourceAsStream("material_io_folder.png"));
        ImageView folderImageView1 = new ImageView(folderImage);
        folderImageView1.setFitHeight(18.0);
        folderImageView1.setFitWidth(18.0);
        ImageView folderImageView2 = new ImageView(folderImage);
        folderImageView2.setFitHeight(18.0);
        folderImageView2.setFitWidth(18.0);
        ImageView folderImageView3 = new ImageView(folderImage);
        folderImageView3.setFitHeight(18.0);
        folderImageView3.setFitWidth(18.0);
        ImageView folderImageView4 = new ImageView(folderImage);
        folderImageView4.setFitHeight(18.0);
        folderImageView4.setFitWidth(18.0);
        ImageView folderImageView5 = new ImageView(folderImage);
        folderImageView5.setFitHeight(18.0);
        folderImageView5.setFitWidth(18.0);

        // Create TreeItem objects to be displayed.
        TreeItem folder1 = new TreeItem(new DraggableFolder("Folder 1"), folderImageView1);
        TreeItem folder2 = new TreeItem(new DraggableFolder("Folder 2"), folderImageView2);
        TreeItem folder3 = new TreeItem(new DraggableFolder("Folder 3"), folderImageView3);
        TreeItem folder4 = new TreeItem(new DraggableFolder("Folder 4"), folderImageView4);
        TreeItem folder5 = new TreeItem(new DraggableFolder("Folder 5"), folderImageView5);
        TreeItem child1 = new TreeItem(new DraggableChild("Child Item 1"));
        TreeItem child2 = new TreeItem(new DraggableChild("Child Item 2"));
        TreeItem child3 = new TreeItem(new DraggableChild("Child Item 3"));
        TreeItem child4 = new TreeItem(new DraggableChild("Child Item 4"));
        TreeItem child5 = new TreeItem(new DraggableChild("Child Item 5"));
        TreeItem child6 = new TreeItem(new DraggableChild("Child Item 6"));
        TreeItem child7 = new TreeItem(new DraggableChild("Child Item 7"));
        TreeItem child8 = new TreeItem(new DraggableChild("Child Item 8"));
        TreeItem child9 = new TreeItem(new DraggableChild("Child Item 9"));
        TreeItem child10 = new TreeItem(new DraggableChild("Child Item 10"));
        TreeItem child11 = new TreeItem(new DraggableChild("Child Item 11"));
        TreeItem child12 = new TreeItem(new DraggableChild("Child Item 12"));
        TreeItem child13 = new TreeItem(new DraggableChild("Child Item 13"));
        TreeItem child14 = new TreeItem(new DraggableChild("Child Item 14"));
        TreeItem child15 = new TreeItem(new DraggableChild("Child Item 15"));

        // Add folder TreeItems to the TreeView.
        root.getChildren().addAll(folder1, folder2, folder3, folder4, folder5);

        // Add Child Objects to the Folders.
        folder1.getChildren().addAll(child1, child2, child3);
        folder2.getChildren().addAll(child4, child5, child6);
        folder3.getChildren().addAll(child7, child8, child9);
        folder4.getChildren().addAll(child10, child11, child12);
        folder5.getChildren().addAll(child13, child14, child15);

        // Set JavaFX Scene and display.
        stage.setScene(new Scene(treeView));
        stage.setWidth(500);
        stage.setHeight(720);
        stage.setTitle("Draggable JavaFX TreeView Example");
        stage.show();
    }

}
