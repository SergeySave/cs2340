package edu.gatech.orangeblasters.location;

public enum LocationType {
    DROP_OFF("Drop Off"),
    STORE("Store"),
    WAREHOUSE("Warehouse");

    private final String fullName;

    LocationType(String fullName) {
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
