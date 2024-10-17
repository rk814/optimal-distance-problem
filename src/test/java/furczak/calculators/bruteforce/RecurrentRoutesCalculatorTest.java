package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;

class RecurrentRoutesCalculatorTest extends CommonRoutesCalculatorTest{
    @Override
    protected RouteCalculator createCalculator() {
        return new RecurrentRoutesCalculator();
    }
}