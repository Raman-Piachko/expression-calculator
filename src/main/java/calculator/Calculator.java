package calculator;

import javax.servlet.ServletException;
import java.io.IOException;

public interface Calculator {
    void calculate() throws ServletException, IOException;
}