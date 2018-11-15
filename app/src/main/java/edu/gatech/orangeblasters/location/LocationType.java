package edu.gatech.orangeblasters.location;

/**
 * Represents a type of location
 */
public enum LocationType {
    DROP_OFF("Drop Off"),
    STORE("Store"),
    WAREHOUSE("Warehouse");

    private final String fullName;

    LocationType(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Get the full name of this location
     *
     * @return the full name of this location
     */
    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
