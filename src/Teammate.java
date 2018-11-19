import java.util.List;

public class Teammate {
    public String name;
    public String state;
    public List<String> itenary;

    public Teammate(String name, String state, List<String> itenary) {
        this.name = name;
        this.state = state;
        this.itenary = itenary;
    }

    public boolean isContagious() {
        return state.equals("RECOVERING") || state.equals("SICK");
    }

    public String toString() {
        return name;
    }

}
