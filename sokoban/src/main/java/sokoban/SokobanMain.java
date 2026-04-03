package sokoban;

import com.codingame.gameengine.runner.SoloGameRunner;

public class SokobanMain {
    public static final String testFile = "test0.json";

    public static void main(String[] args) {
        SoloGameRunner gameRunner = new SoloGameRunner();
        gameRunner.setAgent(Agent.class);
        gameRunner.setTestCase(testFile);

        gameRunner.start(4200);
    }
}
