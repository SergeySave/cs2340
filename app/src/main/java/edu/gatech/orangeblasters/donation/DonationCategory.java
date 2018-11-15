package edu.gatech.orangeblasters.donation;

/**
 * Represents a donation type
 */
public enum DonationCategory {

    CLOTHING("Clothing"),
    HAT("Store"),
    KITCHEN("Kitchen"),
    ELECTRONICS("Electronics"),
    HOUSEHOLD("Household"),
    OTHER("Other");

    private final String fullName;

    DonationCategory(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Get the full name of this donation type
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
