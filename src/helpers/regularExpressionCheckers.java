package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regularExpressionCheckers {
    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean isInputValid(String regex, String input) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(input);
        return matcher.find();
    }
}
