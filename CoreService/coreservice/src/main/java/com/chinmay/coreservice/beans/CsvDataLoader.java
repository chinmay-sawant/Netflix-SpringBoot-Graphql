package com.chinmay.coreservice.beans;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List; // Use jakarta.annotation for Spring Boot 3+

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.chinmay.coreservice.models.Show;
import com.chinmay.coreservice.repository.ShowRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import jakarta.annotation.PostConstruct;

@Component
public class CsvDataLoader {

    private final ShowRepository showRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM d, yyyy", java.util.Locale.US); // Adjust if your date format is different

    public CsvDataLoader(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @PostConstruct // This method will run after the bean is initialized
    public void loadCsvData() {
        if (showRepository.count() == 0) { // Only load if the table is empty
            try {
                // Get the CSV file from the classpath
                ClassPathResource resource = new ClassPathResource("netflix_titles.csv");
                InputStreamReader reader = new InputStreamReader(resource.getInputStream());

                // Use OpenCSV to read the file
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withSkipLines(1) // Skip the header row
                        .build();

                List<String[]> records = csvReader.readAll();

                for (String[] record : records) {
                    // Ensure the record has the expected number of columns
                    if (record.length >= 12) {
                        Show show = new Show();
                        // Map CSV columns to Entity fields
                        show.setShowId(record[0]);
                        show.setType(record[1]);
                        show.setTitle(record[2]);
                        show.setDirector(record[3].isEmpty() ? null : record[3]); // Handle empty strings for optional fields
                        show.setCast(record[4].isEmpty() ? null : record[4]);
                        show.setCountry(record[5].isEmpty() ? null : record[5]);

                        // Parse the date
                        try {
                            show.setDateAdded(record[6].isEmpty() ? null : LocalDate.parse(record[6].trim(), DATE_FORMATTER));
                        } catch (DateTimeParseException e) {
                            System.err.println("Error parsing date '" + record[6] + "' for show_id " + record[0] + ": " + e.getMessage());
                            show.setDateAdded(null); // Set to null or handle error as needed
                        }

                        // Parse the release year
                        try {
                            show.setReleaseYear(record[7].isEmpty() ? null : Integer.parseInt(record[7]));
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing year '" + record[7] + "' for show_id " + record[0] + ": " + e.getMessage());
                            show.setReleaseYear(null); // Set to null or handle error
                        }


                        show.setRating(record[8].isEmpty() ? null : record[8]);
                        show.setDuration(record[9].isEmpty() ? null : record[9]);
                        show.setListedIn(record[10].isEmpty() ? null : record[10]);
                        show.setDescription(record[11].isEmpty() ? null : record[11]);

                        showRepository.save(show);
                    } else {
                         System.err.println("Skipping row due to insufficient columns: " + String.join(",", record));
                    }
                }
                System.out.println("CSV data loaded successfully! Total records: " + showRepository.count());

            } catch (IOException | CsvException e) {
                e.printStackTrace();
                System.err.println("Error loading CSV data: " + e.getMessage());
            }
        } else {
             System.out.println("Database already contains data. Skipping CSV load.");
        }
    }
}
