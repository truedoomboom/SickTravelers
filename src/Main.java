import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
/*        String[] input = new String[4];
        input[0] = "Wilson SICK PaloAlto DC DC London PaloAlto";
        input[1] = "Yun HEALTHY PaloAlto";
        input[2] = "Ali RECOVERING DC DC London";
        input[3] = "Jasmine HEALTHY London PaloAlto DC";*/

        String[] input = new String[3];
        input[0] = "Wilson SICK PaloAlto PaloAlto DC DC London London";
        input[1] = "Yun HEALTHY PaloAlto DC DC London London PaloAlto";
        input[2] = "Ali RECOVERING London London PaloAlto PaloAlto DC DC";

        System.out.println(daysToRecover(input));
    }

    private static void buildTeammateCollection(String[] input, Set<Teammate> set) {
        for (int i = 0; i < input.length; i++) {
            String[] info = input[i].split("\\s+");
            String name = info[0];
            String state = info[1];

            List<String> itenary = new ArrayList<>();
            for (int j = 2; j < info.length; j++) {
                itenary.add(info[j]);
            }
            set.add(new Teammate(name, state, itenary));
        }
    }

    private static int daysToRecover(String[] input) {
        // Create a collection of teammates from input
        Set<Teammate> set = new HashSet<>();
        buildTeammateCollection(input, set);

        // Map locations to teammates
        Map<String, List<Teammate>> officeToTeammates = new HashMap<>();
        int days = 0;
        // build a map
        boolean isEveryoneHealthy = isEveryoneHealthy(set, officeToTeammates, false, days);

        while (!isEveryoneHealthy && days < 365) {
            printState(set, officeToTeammates, days);

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

                if (contagiousPresent) {
                    for (Teammate teammate : presentTeammates) {
                        updateTeammateState(set, teammate);
                    }
                }
            }

            days++;

            // Rebuild the map and check if everyone is healthy
            officeToTeammates.clear();
            isEveryoneHealthy = isEveryoneHealthy(set, officeToTeammates, true, days);
        }

        printState(set, officeToTeammates, days);
        return days;
    }

    private static void updateTeammateState(Set<Teammate> set, Teammate teammate) {
        // if found contagious people
            // all healthy become sick, all recovering become healthy, all sick become recovering
        // else
            // everyone is healthy no state change
        if (teammate.state.equals("SICK")) {
            set.remove(teammate);
            teammate.state = "RECOVERING";
            set.add(teammate);
        } else if (teammate.state.equals("RECOVERING")) {
            set.remove(teammate);
            teammate.state = "HEALTHY";
            set.add(teammate);
        } else {
            set.remove(teammate);
            teammate.state = "SICK";
            set.add(teammate);
        }
    }

    private static void printState(Set<Teammate> set, Map<String, List<Teammate>> officeToTeammates, int days) {
        System.out.println("At the start of day " + days + ":");
        for (String office : officeToTeammates.keySet()) {
            System.out.println(office + " -> " + officeToTeammates.get(office).toString());
        }
        System.out.println();

        // Print each teammate
        System.out.println("At the start of day " + days + ":");
        for (Teammate teammate : set) {
            System.out.println(teammate.name + ": " + teammate.state);
        }

        System.out.println();
    }

    private static boolean isEveryoneHealthy(Set<Teammate> set, Map<String, List<Teammate>> officeToTeammates, boolean isEveryoneHealthy, int days) {
        for (Teammate teammate : set) {
            if (teammate.isContagious()) {
                isEveryoneHealthy = false;
            }

            List<Teammate> team = new ArrayList<>();
            int loc = days % teammate.itenary.size();

            if (officeToTeammates.containsKey(teammate.itenary.get(loc))) {
                team = officeToTeammates.get(teammate.itenary.get(loc));
            }
            team.add(teammate);
            officeToTeammates.put(teammate.itenary.get(loc), team);
        }
        return isEveryoneHealthy;
    }
}
