package industries.lemon.pokeinfo.utils;

public class Slugger {
    public static String unslug(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("-");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return result.toString().trim();
    }
}
