package com.hollywooddos.jessica;

import java.util.Date;

public class Badge {
    private final int badgeID;
    private final String rankOfBadge; // Gold is for one conneciton, Silver is for two connections, Bronze is for
                                      // three connections
    private final Date dateEarned;

    public Badge(int badgeID, String rankOfBadge, Date dateEarned) {
        this.badgeID = badgeID;
        this.rankOfBadge = rankOfBadge;
        this.dateEarned = dateEarned;
    }

    public int getBadgeID() {
        return badgeID;
    }

    public String getRankOfBadge() {
        return rankOfBadge;
    }

    public Date getDateEarned() {
        return dateEarned;
    }

    @Override
    public String toString() {
        return rankOfBadge + " (" + dateEarned + ")";
    }
}
