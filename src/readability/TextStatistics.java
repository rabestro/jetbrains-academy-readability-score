package readability;

import java.util.regex.Pattern;

import static java.lang.Math.max;

public class TextStatistics {
    private static final Pattern WORDS_DELIMITER = Pattern.compile("[^\\p{Alpha}]+");
    private static final Pattern SENTENCES_DELIMITER = Pattern.compile("[!?.]+");
    private static final Pattern PATTERN_SYLLABLE = Pattern.compile("([aiouy]|e(?!$))+");

    private final String text;
    private final long characters;
    private final long words;
    private final long sentences;
    private final long syllables;
    private final long polysyllables;

    public TextStatistics(final String text) {
        this.text = text;
        characters = text.replaceAll("\\s", "").length();
        words = WORDS_DELIMITER.splitAsStream(text).count();
        sentences = SENTENCES_DELIMITER.splitAsStream(text).count();
        syllables = WORDS_DELIMITER.splitAsStream(text).mapToLong(TextStatistics::countSyllables).sum();
        polysyllables = WORDS_DELIMITER.splitAsStream(text).filter(TextStatistics::isPolysyllable).count();
    }

    public long getCharacters() {
        return characters;
    }

    public long getWords() {
        return words;
    }

    public long getSentences() {
        return sentences;
    }

    public long getSyllables() {
        return syllables;
    }

    public long getPolysyllables() {
        return polysyllables;
    }

    public String getText() {
        return text;
    }

    private static long countSyllables(final String word) {
        return max(1, PATTERN_SYLLABLE.matcher(word).results().count());
    }

    private static boolean isPolysyllable(final String word) {
        return countSyllables(word) > 2;
    }

    @Override
    public String toString() {
        return String.format(String.join("%n",
                "The text is: %n%s",
                "Words: %d",
                "Sentences: %d",
                "Characters: %d",
                "Syllables: %d",
                "Polysyllables: %d%n"),
                getText(), getWords(), getSentences(), getCharacters(), getSyllables(), getPolysyllables());
    }
}
