package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static calculator.CalculatorConstants.EMPTY_SYMBOL;
import static calculator.CalculatorConstants.SPACES;

public final class СonversionUtil {

    private СonversionUtil() {
    }

    public static Map<String, List<String>> convertMapWithArrayValueToListValue(Map<String, String[]> resourceMap) {
        Map<String, List<String>> stringListMap = new HashMap<>();
        resourceMap.entrySet().stream().forEach(stringEntry -> stringListMap.put(stringEntry.getKey(), Arrays.asList(stringEntry.getValue())));

        return stringListMap;
    }

    public static String deleteSpacesAndConvertListToString(List<String> expression) {
        return String.join(EMPTY_SYMBOL, expression)
                .replaceAll(SPACES, EMPTY_SYMBOL);
    }
}