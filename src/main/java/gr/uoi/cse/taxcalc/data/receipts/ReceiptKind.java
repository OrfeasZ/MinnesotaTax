package gr.uoi.cse.taxcalc.data.receipts;

public enum ReceiptKind {
    BASIC("Basic"),
    ENTERTAINMENT("Entertainment"),
    TRAVEL("Travel"),
    HEALTH("Health"),
    OTHER("Other");

    public static ReceiptKind getEnum(String name) {
        for (ReceiptKind kind : values()) {
            if (kind.toString().equalsIgnoreCase(name)) {
                return kind;
            }
        }

        throw new IllegalArgumentException();
    }

    private String displayName;

    ReceiptKind(String name) {
        displayName = name;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
