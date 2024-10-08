
package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private Map<String, String> nameCode = new HashMap<>();
    private Map<String, String> codeName = new HashMap<>();

    // Define constants for the indices of each field after splitting
    private static final int COUNTRY_NAME_INDEX = 0;
    private static final int ALPHA3_CODE_INDEX = 2;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     *
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] tokens = line.split("\\t|\\s{2,}");

                String countryName = tokens[COUNTRY_NAME_INDEX].trim();
                String alpha3Code = tokens[ALPHA3_CODE_INDEX].trim().toUpperCase();

                // Populate the maps
                nameCode.put(countryName, alpha3Code);
                codeName.put(alpha3Code, countryName);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     *
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        if (code == null) {
            return null;
        }
        return codeName.get(code.toUpperCase());
    }

    /**
     * Returns the code of the country for the given country name.
     *
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        if (country == null) {
            return null;
        }
        return nameCode.get(country.trim());
    }

    /**
     * Returns how many countries are included in this code converter.
     *
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return nameCode.size();
    }
}