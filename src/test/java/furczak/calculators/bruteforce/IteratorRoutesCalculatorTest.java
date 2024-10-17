package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;

class IteratorRoutesCalculatorTest extends CommonRoutesCalculatorTest {
    @Override
    protected RouteCalculator createCalculator() {
        return new IteratorRoutesCalculator();
    }
}