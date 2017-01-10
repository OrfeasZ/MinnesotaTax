package gr.uoi.cse.taxcalc.data;

public enum FamilyStatus {
    MARRIED_JOINTLY("Married Filing Jointly"),
    MARRIED_SEPARATELY("Married Filing Separately"),
    SINGLE("Single"),
    HOUSEHOLD_HEAD("Head of Household");

    public static FamilyStatus getEnum(String name) {
        for (FamilyStatus status : values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }

        throw new IllegalArgumentException();
    }

    private String displayName;

    FamilyStatus(String name) {
        displayName = name;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
