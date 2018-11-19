
public class Teammate {
    private String name;
    private String state;
    private String[] travelItinerary;

    public Teammate(String name, String state, String[] travelItinerary) {
        this.name = name;
        this.state = state;
        this.travelItinerary = travelItinerary;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public void setState(String updatedState) {
        if (!updatedState.equals("SICK") && !updatedState.equals("HEALTHY") && !updatedState.equals("RECOVERING")) {
            throw new IllegalArgumentException("Please input a valid state.");
        }
        state = updatedState;
    }

    public String getOfficeLocation(int day) {
        return travelItinerary[day % travelItinerary.length];
    }

    public boolean isContagious() {
        return state.equals("RECOVERING") || state.equals("SICK");
    }

    public String toString() {
        return name + "(" + state + ")";
    }

}
