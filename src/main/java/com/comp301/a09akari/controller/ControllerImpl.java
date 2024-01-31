package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

public class ControllerImpl implements AlternateMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int newIndex = model.getActivePuzzleIndex() + 1;
    if (newIndex >= model.getPuzzleLibrarySize()) {
      newIndex = 0;
    }
    model.setActivePuzzleIndex(newIndex);
  }

  @Override
  public void clickPrevPuzzle() {
    int newIndex = model.getActivePuzzleIndex() - 1;
    if (newIndex < 0) {
      newIndex = 0;
    }
    model.setActivePuzzleIndex(newIndex);
  }

  @Override
  public void clickRandPuzzle() {
    int newIndex = (int) (Math.random() * model.getPuzzleLibrarySize());
    while (newIndex == getActivePuzzleIndex() || newIndex >= model.getPuzzleLibrarySize()) {
      newIndex = (int) (Math.random() * model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(newIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (!model.isLamp(r, c)) {
      model.addLamp(r, c);
    } else {
      model.removeLamp(r, c);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  public int getPuzzleLibrarySize() {
    return model.getPuzzleLibrarySize();
  }

  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }
}
