
public class Main {

    public static void main(String[] args) {

        // Success
/*        String[] input = new String[4];
        input[0] = "Wilson SICK PaloAlto DC DC London PaloAlto";
        input[1] = "Yun HEALTHY PaloAlto";
        input[2] = "Ali RECOVERING DC DC London";
        input[3] = "Jasmine HEALTHY London PaloAlto DC";*/

        // Unsuccesfull
        String[] input = new String[3];
        input[0] = "Wilson SICK PaloAlto PaloAlto DC DC London London";
        input[1] = "Yun HEALTHY PaloAlto DC DC London London PaloAlto";
        input[2] = "Ali RECOVERING London London PaloAlto PaloAlto DC DC";

        TravelManager manager = new TravelManager(input);
        while (!manager.isEveryoneHealthy() && manager.getDay() < 365) {
            manager.nextRound();
        }

        manager.printState();
    }
}
