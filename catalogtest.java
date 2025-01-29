package com.example.demo;import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class CatalogApplication {

    public static void main(String[] args) {
        // Correct file names
    	String testCase1File = "src/main/resources/testcase.json";
    	String testCase2File = "src/main/resources/testcase2.json";


        // Process Test Case 1
        System.out.println("Processing Test Case 1:");
        String testCase1Json = readJsonFromFile(testCase1File);
        if (testCase1Json != null) {
            processJson(testCase1Json);
        }

        // Process Test Case 2
        System.out.println("\nProcessing Test Case 2:");
        String testCase2Json = readJsonFromFile(testCase2File);
        if (testCase2Json != null) {
            processJson(testCase2Json);
        }
    }

    // Method to read JSON from a file
    public static String readJsonFromFile(String fileName) {
        try {
            FileReader reader = new FileReader(new File(fileName));
            StringBuilder stringBuilder = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                stringBuilder.append((char) ch);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + fileName);
            e.printStackTrace();
            return null;
        }
    }

    // Method to process the JSON and convert values to decimal
    public static void processJson(String json) {
        // Use Gson to parse the JSON string
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        // Loop through the entries in the JSON
        for (String key : jsonObject.keySet()) {
            if (key.equals("keys")) {
                continue; // Skip the 'keys' part as it's not a number
            }

            JsonObject item = jsonObject.getAsJsonObject(key);
            int base = item.get("base").getAsInt();
            String value = item.get("value").getAsString();

            // Convert the value from the given base to decimal
            BigInteger decimalValue = convertToDecimal(value, base);
            System.out.println("Key: " + key + ", Base: " + base + ", Decimal: " + decimalValue);
        }
    }

    // Method to convert a value from a given base to decimal
    public static BigInteger convertToDecimal(String input, int base) {
        try {
            // Convert the input string to BigInteger using the given base
            return new BigInteger(input, base);
        } catch (NumberFormatException e) {
            // Handle invalid number format
            throw new IllegalArgumentException("Invalid base " + base + " number: " + input, e);
        }
    }
}