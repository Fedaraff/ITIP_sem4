package org.example;

import org.example.StringProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static {
        try (InputStream input = Main.class.getResourceAsStream("/build-passport.properties")) {
            if (input != null) {
                Properties props = new Properties();
                props.load(input);
                logger.info("=== Build Info ===");
                logger.info("User: {}", props.getProperty("build.user"));
                logger.info("OS: {}", props.getProperty("build.os"));
                logger.info("Java: {}", props.getProperty("build.java.version"));
                logger.info("Build time: {}", props.getProperty("build.datetime"));
                logger.info("Message: {}", props.getProperty("build.message"));
                logger.info("Build number: {}", props.getProperty("build.number"));
                logger.info("Git hash: {}", props.getProperty("build.git.hash"));
            }
        } catch (Exception e) {
            logger.warn("Не удалось прочитать build-passport.properties");
        }
    }

    public static void main(String[] args) {
        logger.info("Program started");

        System.out.print("Enter text: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String reversed = StringProcessor.reverse(input);
        String capitalized = StringProcessor.capitalize(input);

        logger.info("Source line: '{}'", input);
        logger.info("Inverted line: '{}'", reversed);
        logger.info("Capital line: '{}'", capitalized);

        logger.info("Program finish");
        scanner.close();
    }
}