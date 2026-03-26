package sokoban;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.google.gson.Gson;

import fr.uga.pddl4j.parser.Parser;

public class Agent {
    public static void main(String[] args) {
        try{
            // reads Sokoban levels and encodes them as a PDDL problem
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("config/test21.json")));
            JsonGameTest levelFile = new Gson().fromJson(reader, JsonGameTest.class);
            reader.close();
            System.err.println(levelFile.testIn);
            String domainString = DomainString.get("domain.pddl");
            String problemString = ProblemString.get(levelFile.testIn);
            // System.err.println(domainString);
            System.err.println(problemString);

            // integrates a PDDL4J planner
            Parser parser = new Parser();
            // parser.parseFromString(domainString, problemString);

            // solves the Sokoban level and executes the solution on the Sokoban interface

            String solution = "DUU";
            for (char c : solution.toCharArray()) System.out.println(c);

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
}
