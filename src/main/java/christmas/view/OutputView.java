package christmas.view;

import static christmas.view.constants.OuputMessage.START_EVENT_PLANNER_MESSAGE;

public class OutputView {
    public static void startEventPlanner(){
        print(START_EVENT_PLANNER_MESSAGE);
    }

    private static void print(String message) {
        System.out.println(message);
    }
}
