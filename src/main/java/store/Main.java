package store;


import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // to prevent showing disturbing mongodb logs on terminal
        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        Interactive interactive = new Interactive();
        interactive.startStages();
    }
}
