package furczak.io;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {

    public List<Integer> loadData(String fileName) {
        List<Integer> result = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
            String data = br.readLine();
            result = getNumbers(data);
        } catch (IOException | NumberFormatException e) {
            System.out.printf("Loading failed. Reason: %s%n", e.getMessage());
        }
        return result;
    }

    public void saveData(List<Integer> data, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            bufferedWriter.write(joinNumbers(data));
        } catch (IOException e) {
            System.out.printf("Saving failed. Reason: %s%n", e.getMessage());
        }
    }

    private List<Integer> getNumbers(String data) {
        return Arrays.stream(data.split(",")).map(String::trim).map(Integer::parseInt).toList();
    }

    private String joinNumbers(List<Integer> data) {
        return data.stream().map(Object::toString).collect(Collectors.joining(","));
    }
}
