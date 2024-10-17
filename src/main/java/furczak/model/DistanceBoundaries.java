package furczak.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DistanceBoundaries {

    private int min;
    private int max;

    int getPerfectDistance() {
        return (min + max) / 2;
    }
}
