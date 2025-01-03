package org.mine.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {
    public static final Logger logger= LogManager.getLogger("executionLogger");
    public static String readJsonFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            //By logging the error, you capture the context and details of what went wrong,
            // which can be invaluable when diagnosing issues later.
            //When you want to record an error for diagnostic purposes without necessarily stopping the execution flow immediately.
            logger.error("IOException occured while reading data from json" + filePath, e);
            //Throwing an exception allows you to propagate the error up the call stack. This is useful
            // if you want to handle this error at a higher level in your application or if you want to signal that something went wrong.
            //When you want to indicate a failure that should be handled by higher-level logic or when you want to stop further processing due to a critical issue.
            throw new RuntimeException("Failed to read JSON file" + filePath, e);
        }
    }
}
