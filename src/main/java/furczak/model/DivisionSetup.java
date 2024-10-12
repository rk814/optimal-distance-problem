package furczak.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DivisionSetup {

    private int minDistance = 30;
    private int maxDistance = 50;

    int getPerfectDistance() {
        return (minDistance + maxDistance) / 2;
    }
}
