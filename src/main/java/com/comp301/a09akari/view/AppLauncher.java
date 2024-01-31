package com.comp301.a09akari.view;

import static com.comp301.a09akari.SamplePuzzles.*;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // Model
    PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();
    Puzzle puzzle1 = new PuzzleImpl(PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(PUZZLE_05);
    puzzleLibrary.addPuzzle(puzzle1);
    puzzleLibrary.addPuzzle(puzzle2);
    puzzleLibrary.addPuzzle(puzzle3);
    puzzleLibrary.addPuzzle(puzzle4);
    puzzleLibrary.addPuzzle(puzzle5);
    Model model = new ModelImpl(puzzleLibrary);

    // Controller
    ControllerImpl controller = new ControllerImpl(model);

    // View
    View view = new View(controller);

    // Scene
    Scene scene = new Scene(view.render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);

    // Refresh view when model changes
    model.addObserver(
        (Model m) -> {
          scene.setRoot(view.render());
          stage.sizeToScene();
        });

    // Show the stage
    stage.show();
  }
}
