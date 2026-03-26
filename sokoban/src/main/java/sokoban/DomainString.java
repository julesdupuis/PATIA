package sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DomainString {
    public static String get(String fileName) throws IOException{
        File domainFile = new File(fileName);
        BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream(domainFile)));
        StringBuilder domainString = new StringBuilder();
        while(stream.ready()){
            domainString.append(stream.readLine());
            domainString.append("\n");
        }
        stream.close();
        return domainString.toString();
    }
}
