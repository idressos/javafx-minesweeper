package gr.ntua.ece.medialab.minesweeper.types;

import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;

import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidValueException;
import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidDescriptionException;

/**
* Rpresents a Minesweeper game scenario.
* @author Ioannis Dressos
*/
public class Scenario {
    
    private int difficultyLevel;
    private int mineCount;
    private int timeLimit;
    private boolean hasSupermine;
    
    /**
    * Creates a scenario using the parameters provided.
    * 
    * @param difficultyLevel the difficulty level of the game
    * @param mineCount the amount of mines in the minefield
    * @param timeLimit seconds available for the player to complete the game
    * @param hasSupermine whether or not a supermine should be included in the minefield
    * @return the scenario
    */
    public Scenario(int difficultyLevel, int mineCount, int timeLimit, boolean hasSupermine) throws InvalidValueException {
        this.difficultyLevel = difficultyLevel;
        this.mineCount = mineCount;
        this.timeLimit = timeLimit;
        this.hasSupermine = hasSupermine;

        validate();
    }
    
    /**
    * Creates a scenario as it is described in a file (based on the req. specification).
    * 
    * @param path the path of the file which includes the scenario description
    * @return the scenario described in the file
    */
    public static Scenario fromFile(String path) throws InvalidDescriptionException, InvalidValueException, IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        
        if(lines.size() < 4) throw new InvalidDescriptionException("Malformed scenario description (missing values)", null);
        
        int difficultyLevel = -1;
        int mineCount = -1;
        int timeLimit = -1;
        boolean hasSupermine = false;

        for(int i = 0; i < lines.size(); i++) {
            int value;

            try {
                value = Integer.parseInt(lines.get(i));
            } catch(NullPointerException | NumberFormatException ex) {
                throw new InvalidValueException("Malformed scenario description (expected integer)", ex);
            }

            if(i == 0) difficultyLevel = value;
            if(i == 1) mineCount = value;
            if(i == 2) timeLimit = value;
            if(i == 3) {
                if(value == 0) hasSupermine = false;
                else if(value == 1) hasSupermine = true;
                else throw new InvalidValueException("Invalid supermine value (can be either 0 or 1)", null);
            }
		}

        return new Scenario(difficultyLevel, mineCount, timeLimit, hasSupermine);
    }
    
    /**
    * Saves the scenario configuration to a file according to the req. specification.
    * 
    * @param path the path to save the file to
    */
    public void toFile(String path) throws IOException {
        String fileContents = difficultyLevel + "\n" + mineCount + "\n" + timeLimit + "\n" + (hasSupermine ? 1 : 0);
        Files.write(Paths.get(path), fileContents.getBytes());
    }
    
    /**
    * Returns the scenario difficulty level (1 or 2).
    * 
    * @return      the difficulty level
    */
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    
    /**
    * Returns grid size (width, height) of minefields generated for this scenario.
    * 
    * @return the grid size
    */
    public int getGridSize() {
        return difficultyLevel == 1 ? 9 : (difficultyLevel == 2 ? 16 : -1);
    }
    
    /**
    * Returns the amount of mines there should be in minefields generated for this scenario.
    * 
    * @return the mine count
    */
    public int getMineCount() {
        return mineCount;
    }
    
    /**
    * Returns the seconds the player will have to complete the game.
    * 
    * @return the game time limit, in seconds
    */
    public int getTimeLimit() {
        return timeLimit;
    }
    
    /**
    * Returns whether the minefields generated for this scenario should include a supermine.
    * 
    * @return true if there should be a supermine, false otherwise
    */
    public boolean hasSupermine() {
        return hasSupermine;
    }
    
    /**
    * Validates the scenario values according to the game rules.
    * 
    * @throws InvalidValueException if the scenario is misconfigured
    */
    public void validate() throws InvalidValueException {
        if(difficultyLevel != 1 && difficultyLevel != 2) throw new InvalidValueException("Invalid difficulty level", null);

        if(difficultyLevel == 1) {
            if(mineCount < 9 || mineCount > 11) throw new InvalidValueException("Invalid mine count", null);
            if(timeLimit < 120 || timeLimit > 180) throw new InvalidValueException("Invalid time limit", null);
            if(hasSupermine) throw new InvalidValueException("Difficulty level 1 scenarios can't have a supermine", null);
        } else if(difficultyLevel == 2) {
            if(mineCount < 35 || mineCount > 45) throw new InvalidValueException("Invalid mine count", null);
            if(timeLimit < 240 || timeLimit > 360) throw new InvalidValueException("Invalid time limit", null);
        }
    }

}