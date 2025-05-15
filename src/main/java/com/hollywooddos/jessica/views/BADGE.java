package com.hollywooddos.jessica.views;

public enum BADGE {
    GOLD("/com/hollywooddos/jessica/assets/gold_badge.png"),
    SILVER("/com/hollywooddos/jessica/assets/silver_badge.png"),
    BRONZE("/com/hollywooddos/jessica/assets/bronze_badge.png");

    private final String imagePath;

    private BADGE(String imagePath) {
        this.imagePath = imagePath;
    }

    // Method to get the image path
    public String getImagePath() {
        return imagePath;
    }
}
