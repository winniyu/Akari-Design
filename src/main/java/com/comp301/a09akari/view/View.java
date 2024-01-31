package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class View implements FXComponent {
  private final ControllerImpl controller;

  public View(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();
    layout.getStyleClass().add("layout-main");

    // Title
    Label title = new Label("LIGHT UP !!!");
    title.getStyleClass().add("main-title");
    layout.getChildren().add(title);

    // Basic info
    HBox Detail = new HBox();
    Puzzle puzzle = controller.getActivePuzzle();
    int width = puzzle.getWidth();
    int height = puzzle.getHeight();
    Label detail = new Label(width + " x " + height);
    detail.getStyleClass().add("detail-label");
    Detail.getChildren().add(detail);
    Detail.setAlignment(Pos.CENTER);
    layout.getChildren().add(Detail);

    // Puzzle index
    HBox Index = new HBox();
    int index = controller.getActivePuzzleIndex() + 1;
    int size = controller.getPuzzleLibrarySize();
    Label index_label = new Label("Current Puzzle: " + index + " of " + size);
    index_label.getStyleClass().add("index-label");
    Index.getChildren().add(index_label);
    Index.setAlignment(Pos.CENTER);
    layout.getChildren().add(Index);

    Label blank_row = new Label("");
    blank_row.getStyleClass().add("index-label");
    layout.getChildren().add(blank_row);

    // Puzzle view
    HBox PuzzleView = new HBox();
    PuzzleView.getStyleClass().add("puzzle-hbox");
    PuzzleView puzzleView = new PuzzleView(controller);
    PuzzleView.getChildren().add(puzzleView.render());
    PuzzleView.setAlignment(Pos.CENTER);
    layout.getChildren().add(PuzzleView);

    // Status
    HBox Status = new HBox();
    if (controller.isSolved()) {
      Label status = new Label("Success");
      status.getStyleClass().add("success-label");
      Status.getChildren().add(status);
    } else {
      Label status = new Label("Keep going!");
      status.getStyleClass().add("process-label");
      Status.getChildren().add(status);
    }
    Status.setAlignment(Pos.CENTER);
    layout.getChildren().add(Status);
    layout.setSpacing(5);

    // Controls view
    HBox ControlsView = new HBox();
    ControllerView controlsView = new ControllerView(controller);
    ControlsView.getChildren().add(controlsView.render());
    ControlsView.setAlignment(Pos.CENTER);
    ControlsView.setSpacing(10);
    layout.getChildren().add(ControlsView);

    layout.setAlignment(Pos.CENTER);
    return layout;
  }
}
