package furczak.io;

import furczak.validators.DataValidator;
import lombok.Getter;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DataManager {

    private final String SAMPLES_DIR = "samples";
    private final DataValidator dataValidator = new DataValidator();


    public List<Integer> loadData(String fileName) {
        if (!dataValidator.isFileNameValid(fileName)) {
            throw new RuntimeException("Loading failed. Reason: Invalid file name or null data");
        }

//        List<Integer> result;
        File file = new File(SAMPLES_DIR, fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String data = br.readLine();
            return getNumbers(data);
        } catch (IOException | NumberFormatException | NullPointerException e) {
            throw new RuntimeException("Loading failed. Reason: " + e.getMessage());
        }
//        return result;
    }

    public void saveData(List<Integer> data, String fileName) {
        if (!dataValidator.isFileNameValid(fileName) || dataValidator.isNull(data)) {
            throw new RuntimeException("Saving failed. Reason: Invalid file name or null data");
        }

        File file = new File(SAMPLES_DIR, fileName);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter.write(joinNumbers(data));
            bufferedWriter.flush();
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException("Saving failed. Reason: " + e.getMessage());
        }
    }


    private List<Integer> getNumbers(String data) {
        return Arrays.stream(data.split(",")).map(String::trim).map(Integer::parseInt).toList();
    }

    private String joinNumbers(List<Integer> data) {
        return data.stream().map(Object::toString).collect(Collectors.joining(","));
    }
}
