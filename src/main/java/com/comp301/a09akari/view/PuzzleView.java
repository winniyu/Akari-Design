package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class PuzzleView extends Node implements FXComponent {
  private final ControllerImpl controller;

  public PuzzleView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {

    GridPane layout = new GridPane();
    layout.getStyleClass().add("game-board");

    Puzzle activePuzzle = controller.getActivePuzzle();
    for (int i = 0; i < activePuzzle.getHeight(); i++) {
      for (int j = 0; j < activePuzzle.getWidth(); j++) {
        if (activePuzzle.getCellType(i, j) == CellType.CLUE) {
          int clue = activePuzzle.getClue(i, j);
          Label clue_label = new Label(Integer.toString(clue));
          if (controller.isClueSatisfied(i, j)) {
            clue_label.getStyleClass().add("satisfied-clue");
          } else {
            clue_label.getStyleClass().add("clue");
          }
          StackPane clue_label_container = new StackPane(clue_label);
          clue_label_container.getStyleClass().add("clue-label-container");
          layout.add(clue_label_container, i, j);
        } else if (activePuzzle.getCellType(i, j) == CellType.WALL) {
          Label wall = new Label(" ");
          layout.getStyleClass().add("wall");
          layout.add(wall, i, j);
        } else {
          if (controller.isLamp(i, j)) {
            Button lamp = new Button();
            int temp_r = i;
            int temp_c = j;
            lamp.setOnAction(
                (ActionEvent actionEvent) -> {
                  controller.clickCell(temp_r, temp_c);
                });
            if (controller.isLampIllegal(i, j)) {
              lamp.getStyleClass().add("illegal-button");
            } else {
              lamp.getStyleClass().add("lamp-button");
            }
            layout.add(lamp, i, j);
          } else if (controller.isLit(i, j)) {
            Button lit = new Button();
            lit.getStyleClass().add("lit-button");
            int temp_r = i;
            int temp_c = j;
            lit.setOnAction(
                (ActionEvent actionEvent) -> {
                  controller.clickCell(temp_r, temp_c);
                });
            layout.add(lit, i, j);
          } else {
            Button empty = new Button();
            empty.getStyleClass().add("empty-button");
            int temp_r = i;
            int temp_c = j;
            empty.setOnAction(
                (ActionEvent actionEvent) -> {
                  controller.clickCell(temp_r, temp_c);
                });
            layout.add(empty, i, j);
          }
        }
      }
    }
    return layout;
  }
}
