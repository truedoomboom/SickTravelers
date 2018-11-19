import java.util.List;

public class Teammate {
    private String name;
    private String state;
    private List<String> itenary;

    public Teammate(String name, String state, List<String> itenary) {
        this.name = name;
        this.state = state;
        this.itenary = itenary;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public void setState(String updatedState) {
        state = updatedState;
    }

    public String getOffice(int day) {
        return itenary.get(day % itenary.size());
    }

    public boolean isContagious() {
        return state.equals("RECOVERING") || state.equals("SICK");
    }

    public String toString() {
        return name + "(" + state + ")";
    }

}
