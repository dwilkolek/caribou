package eu.wilkolek.caribou;

import eu.wilkolek.caribou.execution.PrintlnModelExecutor;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {

        var exec = new PrintlnModelExecutor();

        new Caribou().run(Path.of("./models"), exec);
    }
}