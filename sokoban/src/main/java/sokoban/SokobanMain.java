package sokoban;

import com.codingame.gameengine.runner.SoloGameRunner;

public class SokobanMain {
    public static String testFile = null;

    public static void main(String[] args) {
        testFile = "test0.json";

        SoloGameRunner gameRunner = new SoloGameRunner();
        gameRunner.setAgent(Agent.class);
        gameRunner.setTestCase(testFile);

        gameRunner.start();
    }
}
