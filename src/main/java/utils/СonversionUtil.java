package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static calculator.CalculatorConstants.EMPTY_SYMBOL;
import static calculator.CalculatorConstants.SPACES;

public class СonversionUtil {
    public static Map<String, List<String>> convertMapWithArrayValueToListValue(Map<String, String[]> resourceMap) {
        Map<String, List<String>> stringListMap = new HashMap<>();
        resourceMap.forEach((key, value) -> stringListMap.put(key, Arrays.asList(value)));
        return stringListMap;
    }

    public static String deleteSpacesAndConvertListToString(List<String> expression) {
        return String.join(EMPTY_SYMBOL, expression)
                .replaceAll(SPACES, EMPTY_SYMBOL);
    }
}