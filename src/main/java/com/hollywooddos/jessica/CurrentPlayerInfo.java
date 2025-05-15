package com.hollywooddos.jessica;

public class CurrentPlayerInfo {

    private static Player currentPlayer;

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void clearCurrentPlayer() {
        currentPlayer = null;
    }

    public static boolean isPlayerLoggedIn() {
        return currentPlayer != null;
    }

    public static String getCurrentPlayerName() {
        return currentPlayer != null ? currentPlayer.getUsername() : null;
    }

    public static int getCurrentPlayerID() {
        return currentPlayer != null ? currentPlayer.getPlayerID() : -1;
    }

    public static int getCurrentPlayerScore() {
        return currentPlayer != null ? currentPlayer.getUserScore() : -1;
    }

}
