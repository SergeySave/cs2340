package edu.gatech.orangeblasters.donation;

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

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
