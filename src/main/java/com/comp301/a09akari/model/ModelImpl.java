package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private final List<ModelObserver> modelObserverList;
  private int activeIndex;
  private int[][] lampLocation;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    this.activeIndex = 0;
    this.lampLocation = new int[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    this.modelObserverList = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r >= lampLocation.length || c >= lampLocation[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    Puzzle activePuzzle = getActivePuzzle();
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    lampLocation[r][c] = 1;
    notifyAllObserver();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r >= lampLocation.length || c >= lampLocation[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    Puzzle activePuzzle = getActivePuzzle();
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    lampLocation[r][c] = 0;
    notifyAllObserver();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r >= lampLocation.length || c >= lampLocation[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    Puzzle activePuzzle = getActivePuzzle();
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (lampLocation[r][c] == 1) {
      return true;
    }
    for (int i = r - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[i][c] == 1) {
        return true;
      }
    }
    for (int i = r + 1; i < activePuzzle.getHeight(); i++) {
      if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[i][c] == 1) {
        return true;
      }
    }
    for (int j = c - 1; j >= 0; j--) {
      if (activePuzzle.getCellType(r, j) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[r][j] == 1) {
        return true;
      }
    }
    for (int j = c + 1; j < activePuzzle.getWidth(); j++) {
      if (activePuzzle.getCellType(r, j) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[r][j] == 1) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= lampLocation.length || c >= lampLocation[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    Puzzle activePuzzle = getActivePuzzle();
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return lampLocation[r][c] == 1;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= lampLocation.length || c >= lampLocation[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (!(lampLocation[r][c] == 1)) {
      throw new IllegalArgumentException();
    }
    lampLocation[r][c] = 0;
    boolean checker = isLit(r, c);
    lampLocation[r][c] = 1;
    return checker;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(activeIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return activeIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= library.size() || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    activeIndex = index;
    Puzzle activePuzzle = library.getPuzzle(activeIndex);
    lampLocation = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
    notifyAllObserver();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);
    lampLocation = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
    notifyAllObserver();
  }

  @Override
  public boolean isSolved() {
    Puzzle activePuzzle = getActivePuzzle();
    boolean checker = true;
    for (int i = 0; i < activePuzzle.getHeight(); i++) {
      for (int j = 0; j < activePuzzle.getWidth(); j++) {
        if (activePuzzle.getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            checker = false;
            break;
          }
        } else if (activePuzzle.getCellType(i, j) == CellType.CORRIDOR) {
          if (lampLocation[i][j] == 0) {
            if (!isLit(i, j)) {
              checker = false;
              break;
            }
          } else {
            if (isLampIllegal(i, j)) {
              checker = false;
              break;
            }
          }
        }
      }
    }

    return checker;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r >= lampLocation.length || c >= lampLocation[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    Puzzle activePuzzle = getActivePuzzle();
    if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int count = 0;
    if (r - 1 >= 0) {
      if (lampLocation[r - 1][c] == 1) {
        count++;
      }
    }
    if (r + 1 < getActivePuzzle().getHeight()) {
      if (lampLocation[r + 1][c] == 1) {
        count++;
      }
    }
    if (c - 1 >= 0) {
      if (lampLocation[r][c - 1] == 1) {
        count++;
      }
    }
    if (c + 1 < getActivePuzzle().getWidth()) {
      if (lampLocation[r][c + 1] == 1) {
        count++;
      }
    }
    return (count == activePuzzle.getClue(r, c));
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    this.modelObserverList.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    this.modelObserverList.remove(observer);
  }

  private void notifyAllObserver() {
    for (ModelObserver modelObserver : modelObserverList) {
      modelObserver.update(this);
    }
  }
}
