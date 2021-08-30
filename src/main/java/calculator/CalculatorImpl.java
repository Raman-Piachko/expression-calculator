package calculator;

import com.google.code.mathparser.MathParser;
import com.google.code.mathparser.MathParserFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static calculator.CalculatorConstants.EMPTY_SYMBOL;
import static calculator.CalculatorConstants.EXPRESSION;
import static calculator.CalculatorConstants.FIRST_ELEMENT;
import static calculator.CalculatorConstants.JSP_PAGE;
import static calculator.CalculatorConstants.RESULT;
import static utils.СonversionUtil.convertMapWithArrayValueToListValue;
import static utils.СonversionUtil.deleteSpacesAndConvertListToString;

public class CalculatorImpl implements Calculator {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public CalculatorImpl(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void calculate() throws ServletException, IOException {
        Map<String, String[]> parameters = new HashMap<>(req.getParameterMap());
        Map<String, List<String>> stringListMap = convertMapWithArrayValueToListValue(parameters);
        String expression = req.getParameter(EXPRESSION);
        List<String> expressionList = Arrays.asList(expression.split(EMPTY_SYMBOL));

        while (isExpressionWithVariables(stringListMap, expressionList)) {
            convertExpressionWithValue(stringListMap, expressionList);
        }

        expression = deleteSpacesAndConvertListToString(expressionList);

        MathParser mathParser = MathParserFactory.create();
        int result = mathParser.calculate(expression)
                .doubleValue()
                .intValue();

        req.setAttribute(RESULT, result);
        req.getRequestDispatcher(JSP_PAGE).forward(req, resp);
    }

    private void convertExpressionWithValue(Map<String, List<String>> parameters, List<String> expression) {
        for (String item : expression) {
            for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (key.equalsIgnoreCase(item)) {
                    int replaceableIndex = expression.indexOf(item);
                    expression.set(replaceableIndex, value.get(FIRST_ELEMENT));
                }
            }
        }
    }

    private boolean isExpressionWithVariables(Map<String, List<String>> parameters, List<String> expression) {
        for (String s : expression) {
            for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {
                String key = entry.getKey();
                if (s.equalsIgnoreCase(key)) {
                    return true;
                }
            }
        }

        return false;
    }
}