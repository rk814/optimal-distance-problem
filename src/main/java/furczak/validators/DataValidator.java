package furczak.validators;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class DataValidator {
    public boolean isFileNameValid(String name) {
        return name!=null && name.contains(".");
    }
}