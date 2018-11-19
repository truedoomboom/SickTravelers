import java.util.*;

public class TravelManager {
    private Set<Teammate> teammates;
    private Map<String, List<Teammate>> officeToTeammates;
    private boolean isEveryoneHealthy;
    private int day;

    public TravelManager(String[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        teammates = new HashSet<>();
        for (String line : input) {
            String[] info = line.split("\\s+");
            if (info.length < 3) {
                throw new IllegalArgumentException("Input MUST specify name, status and at least one office to be at.");
            }

            String name = info[0];
            String state = info[1];

            String[] travelItinerary = new String[info.length - 2];
            for (int j = 2; j < info.length; j++) {
                travelItinerary[j - 2] = info[j];
            }
            teammates.add(new Teammate(name, state, travelItinerary));
        }

        // Initialize the state
        officeToTeammates = new HashMap<>();
        day = 0;

        // Build a map
        buildMap();
    }

    public void printState() {
        System.out.println("At the start of day " + day + ":");
        if (isEveryoneHealthy) {
            System.out.println("Everyone is healthy.");
        }
        for (String office : officeToTeammates.keySet()) {
            System.out.println(office + " -> " + officeToTeammates.get(office).toString());
        }
        System.out.println();

        //Places where everyone is traveling to
        for (Teammate teammate : teammates) {
            System.out.println(teammate.getName() + " will be in " + teammate.getOfficeLocation(day + 1) + " tomorrow.");
        }
        System.out.println();
    }

    public int getDay() {
        return day;
    }

    public boolean isEveryoneHealthy() {
        return isEveryoneHealthy;
    }

    public void nextRound() {
        printState();

        // At each locations check for contagious people
        for (String office : officeToTeammates.keySet()) {
            List<Teammate> presentTeammates = officeToTeammates.get(office);
            boolean contagiousPresent = false;
            for (Teammate teammate : presentTeammates) {
                if (teammate.isContagious()) {
                    contagiousPresent = true;
                    break;
                }
            }

            // If anybody is contagious, change state accordingly
            if (contagiousPresent) {
                for (Teammate teammate : presentTeammates) {
                    updateTeammateState(teammate);
                }
            }
        }

        day++;

        // Build the map for the next day and check if everyone is healthy
        officeToTeammates.clear();
        buildMap();
    }

    private void buildMap() {
        isEveryoneHealthy = true;
        for (Teammate teammate : teammates) {
            if (teammate.isContagious()) {
                isEveryoneHealthy = false;
            }

            List<Teammate> team = new ArrayList<>();
            if (officeToTeammates.containsKey(teammate.getOfficeLocation(day))) {
                team = officeToTeammates.get(teammate.getOfficeLocation(day));
            }
            team.add(teammate);
            officeToTeammates.put(teammate.getOfficeLocation(day), team);
        }
    }

    private void updateTeammateState(Teammate teammate) {
        // all healthy become sick, all recovering become healthy, all sick become recovering
        switch (teammate.getState()) {
            case "SICK":
                teammates.remove(teammate);
                teammate.setState("RECOVERING");
                teammates.add(teammate);
                break;
            case "RECOVERING":
                teammates.remove(teammate);
                teammate.setState("HEALTHY");
                teammates.add(teammate);
                break;
            case "HEALTHY":
                teammates.remove(teammate);
                teammate.setState("SICK");
                teammates.add(teammate);
                break;
            default:
                break;
        }
    }
}
