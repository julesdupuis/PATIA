package sokoban;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.google.gson.Gson;

import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.planners.InvalidConfigurationException;
import fr.uga.pddl4j.planners.LogLevel;
import fr.uga.pddl4j.planners.statespace.FF;

public class Agent {
    public static void main(String[] args) {
        if(SokobanMain.testFile == null){
            SokobanMain.testFile = "test0.json";
        }
        try{
            // reads Sokoban levels and encodes them as a PDDL problem
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("config/"+SokobanMain.testFile)));
            JsonGameTest levelFile = new Gson().fromJson(reader, JsonGameTest.class);
            reader.close();
            System.err.println(levelFile.testIn);
            String domainString = DomainString.get("domain.pddl");
            ProblemString ps = new ProblemString();
            String problemString = ps.get(levelFile.testIn);
            // System.err.println(domainString);
            // System.err.println(problemString);

            // integrates a PDDL4J planner
            FF planner = new FF();
            File domainTempFile = tempFileFromString("domain", domainString);
            planner.setDomain(domainTempFile.getAbsolutePath());
            File problemTempFile = tempFileFromString("problem", problemString);
            planner.setProblem(problemTempFile.getAbsolutePath());

            planner.setLogLevel(LogLevel.ERROR);
            planner.setTimeout(600);

            // solves the Sokoban level and executes the solution on the Sokoban interface
            try{
                Plan plan = planner.solve();

                String solution = ps.parseSolution(plan.actions());;
                for (char c : solution.toCharArray()) System.out.println(c);

            }catch(InvalidConfigurationException e){
                e.printStackTrace();
            }

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public class JsonGameTest {
        public Map<Integer, String> title;
        public String testIn;
        public Boolean isTest;
        public Boolean isValidator;
    }

    public static File tempFileFromString(String fileName, String content) throws IOException{
        File tempFile = File.createTempFile(fileName, ".pddl");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));

        try {
            writer.write(content);
        } catch (Throwable var11) {
            try {
                writer.close();
            } catch (Throwable var9) {
                var11.addSuppressed(var9);
            }

            throw var11;
        }

        writer.close();
        return tempFile;
    }
}
