package stepDefinitions;

import io.cucumber.core.cli.Main;

public class JarRunner {
    public static void main(String[] args) {
        // Pass arguments to Cucumber CLI
        // Glue points to your step definitions package
        // Features points to your .feature files
        String[] cucumberArgs = new String[]{
                "--glue", "stepDefinitions",
                "src/test/resources/features",
                "--plugin", "pretty",
                "--monochrome"
        };

        // Run Cucumber
        Main.main(cucumberArgs);
    }
}