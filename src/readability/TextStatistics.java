package readability;

import java.util.regex.Pattern;

import static java.lang.Math.max;

public interface TextStatistics {
    Pattern WORDS_DELIMITER = Pattern.compile("\\PL+");
    Pattern SENTENCES_DELIMITER = Pattern.compile("[!?.]+");
    Pattern PATTERN_SYLLABLE = Pattern.compile("([aiouy]|e(?!$))+");

    long getCharacters();

    long getWords();

    long getSentences();

    long getSyllables();

    long getPolysyllables();

    String getText();

    default String getInfo() {
        return String.format(String.join("%n",
                "The text is: %n%s",
                "Words: %d",
                "Sentences: %d",
                "Characters: %d",
                "Syllables: %d",
                "Polysyllables: %d%n"),
                getText(), getWords(), getSentences(), getCharacters(), getSyllables(), getPolysyllables());
    }

    static long countSyllables(final String word) {
        return max(1, PATTERN_SYLLABLE.matcher(word).results().count());
    }

    static boolean isPolysyllable(final String word) {
        return countSyllables(word) > 2;
    }

}
