package com.hollywooddos.jessica;

public class GameMode {

    public enum Mode {
        RANDOM_GAME, CUSTOM_GAME
    }

    // Data field to store the current game mode
    private Mode modeName;

    // Default constructor to set an initial mode
    public GameMode() {
        this.modeName = Mode.RANDOM_GAME; // Default to Random Game if not specified
    }

    // Constructor to set the game mode based on user selection
    public GameMode(Mode modeName) {
        this.modeName = modeName;
    }

    // Getter method for modeName
    public Mode getModeName() {
        return modeName;
    }

    // Setter method for modeName
    public void setModeName(Mode modeName) {
        this.modeName = modeName;
        updateGameModeUI(); 
    }

    // Describe Method for each game mode case
    public String describeMode() {
        switch (modeName) {
        case RANDOM_GAME:
            return "Random Game: The system selects two random actors.";
        case CUSTOM_GAME:
            return "Custom Game: You manually select the starting and end goal actors.";
        default:
            return "Random Game: The system selects two random actors.";
        }
    }

    // Method to update the GUI or game state when the mode changes
    // Simulated method to update the GUI or game state when the mode changes
    private void updateGameModeUI() {
        // Logic to update the GUI or other components based on the new game mode
        System.out.println("Game mode now set to: " + modeName);
        // Implement the change in the GUI: could trigger visibility toggles,
        // enable/disable controls, etc.
    }

}
