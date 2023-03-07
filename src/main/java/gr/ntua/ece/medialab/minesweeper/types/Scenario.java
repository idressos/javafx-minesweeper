package gr.ntua.ece.medialab.minesweeper.types;

import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;

import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidValueException;
import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidDescriptionException;

public class Scenario {
    
    private int difficultyLevel;
    private int mineCount;
    private int timeLimit;
    private boolean hasSupermine;
    
    public Scenario(int difficultyLevel, int mineCount, int timeLimit, boolean hasSupermine) throws InvalidValueException {
        this.difficultyLevel = difficultyLevel;
        this.mineCount = mineCount;
        this.timeLimit = timeLimit;
        this.hasSupermine = hasSupermine;

        validate();
    }
    
    public static Scenario fromFile(String path) throws InvalidDescriptionException, InvalidValueException, IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        
        if(lines.size() < 4) throw new InvalidDescriptionException("malformed scenario description (missing values)", null);
        
        int difficultyLevel = -1;
        int mineCount = -1;
        int timeLimit = -1;
        boolean hasSupermine = false;

        for(int i = 0; i < lines.size(); i++) {
            int value;

            try {
                value = Integer.parseInt(lines.get(i));
            } catch(NullPointerException | NumberFormatException ex) {
                throw new InvalidValueException("malformed scenario description (expected integer)", ex);
            }

            if(i == 0) difficultyLevel = value;
            if(i == 1) mineCount = value;
            if(i == 2) timeLimit = value;
            if(i == 3) {
                if(value == 0) hasSupermine = false;
                else if(value == 1) hasSupermine = true;
                else throw new InvalidValueException("invalid supermine value", null);
            }
		}

        return new Scenario(difficultyLevel, mineCount, timeLimit, hasSupermine);
    }
    
    public void toFile(String path) throws IOException {
        String fileContents = difficultyLevel + "\n" + mineCount + "\n" + timeLimit + "\n" + (hasSupermine ? 1 : 0);
        Files.write(Paths.get(path), fileContents.getBytes());
    }
    
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public int getGridSize() {
        return difficultyLevel == 1 ? 9 : (difficultyLevel == 2 ? 16 : -1);
    }
    
    public int getMineCount() {
        return mineCount;
    }
    
    public int getTimeLimit() {
        return timeLimit;
    }
    
    public boolean hasSupermine() {
        return hasSupermine;
    }
    
    public void validate() throws InvalidValueException {
        if(difficultyLevel != 1 && difficultyLevel != 2) throw new InvalidValueException("invalid difficulty level", null);

        if(difficultyLevel == 1) {
            if(mineCount < 9 || mineCount > 11) throw new InvalidValueException("invalid mine count", null);
            if(timeLimit < 120 || timeLimit > 180) throw new InvalidValueException("invalid time limit", null);
            if(hasSupermine) throw new InvalidValueException("difficulty level 1 can't have supermine", null);
        } else if(difficultyLevel == 2) {
            if(mineCount < 35 || mineCount > 45) throw new InvalidValueException("invalid mine count", null);
            if(timeLimit < 240 || timeLimit > 360) throw new InvalidValueException("invalid time limit", null);
        }
    }

}
