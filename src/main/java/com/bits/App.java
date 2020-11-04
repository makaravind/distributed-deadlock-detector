package com.bits;

import com.bits.models.ParseException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

class App {
    public static void main(String[] args) throws ParseException, URISyntaxException {
        System.out.println("Welcome to Chandy-Misra-Hanns (OR) Algorithm");
        String pathToInputFile = args.length > 0 ? args[0] : "";
        if(pathToInputFile.isBlank()) {
            System.out.println("Input file is not passed through args, No worries! Picking up sample input for demo");
            URL resource = App.class.getClassLoader().getResource("graph.cmh");
            assert resource != null;
            run(Paths.get(resource.toURI()));
        } else {
            System.out.println("Picking input file from path= " + pathToInputFile);
            run(Paths.get(pathToInputFile));
        }
    }

    private static void run(Path pathToInputFile) throws ParseException {
        boolean isPresent = new AppController().isDeadlockPresentOnInputFile(pathToInputFile);
        if(isPresent) {
            System.out.println("Deadlock detected! ^/^");
        } else {
            System.out.println("Deadlock not detected! ^-^");
        }
    }
}
