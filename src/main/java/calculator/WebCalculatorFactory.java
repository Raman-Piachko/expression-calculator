package calculator;

public class WebCalculatorFactory {
    public Calculator createCalculator() {
        return new CalculatorImpl();
    }
}