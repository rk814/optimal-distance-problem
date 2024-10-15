package furczak.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DistanceBoundaries {

    private int min = 30;
    private int max = 50;

    int getPerfectDistance() {
        return (min + max) / 2;
    }
}
