package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControllerView implements FXComponent {
  private final ControllerImpl controller;

  public ControllerView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.getStyleClass().add("button-box");

    Button nextButton = new Button("Next");
    nextButton.getStyleClass().add("all-button");
    layout.getChildren().add(nextButton);
    nextButton.setOnAction(
        (ActionEvent actionEvent) -> {
          controller.clickNextPuzzle();
        });

    Button prevButton = new Button("Previous");
    prevButton.getStyleClass().add("all-button");
    layout.getChildren().add(prevButton);
    prevButton.setOnAction(
        (ActionEvent actionEvent) -> {
          controller.clickPrevPuzzle();
        });

    Button randButton = new Button("Random");
    randButton.getStyleClass().add("all-button");
    layout.getChildren().add(randButton);
    randButton.setOnAction(
        (ActionEvent actionEvent) -> {
          controller.clickRandPuzzle();
        });

    Button resetButton = new Button("Reset");
    resetButton.getStyleClass().add("all-button");
    layout.getChildren().add(resetButton);
    resetButton.setOnAction(
        (ActionEvent actionEvent) -> {
          controller.clickResetPuzzle();
        });

    return layout;
  }
}
