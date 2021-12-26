package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<CountryIndicators> getCountryIndicatorsFromCSV() throws IOException {
        var CountryIndicatorsArray = new ArrayList<CountryIndicators>();

        try {
            var dataStrings = Files.readAllLines(Paths.get("file.csv"));
            dataStrings.remove(0);
            dataStrings.forEach(dataString -> CountryIndicatorsArray.add(new CountryIndicators(dataString.split(","))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CountryIndicatorsArray;
    }
}