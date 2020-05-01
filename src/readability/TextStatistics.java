package readability;

import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.String.join;

public class TextStatistics {
    public static final long NOT_CALCULATED = -1;
    private final String text;
    private long characters = NOT_CALCULATED;
    private long words = NOT_CALCULATED;
    private long sentences = NOT_CALCULATED;
    private long syllables = NOT_CALCULATED;
    private long polysyllables = NOT_CALCULATED;

    public TextStatistics(final String text) {
        this.text = text;
    }

    public long getCharacters() {
        if (characters == NOT_CALCULATED) {
            characters = text.replaceAll("\\s", "").length();
        }
        return characters;
    }

    public long getWords() {
        return words == NOT_CALCULATED ? (words = text.split(" ").length) : words;
    }

    public long getSentences() {
        return sentences == NOT_CALCULATED
                ? (sentences = text.split("[!?.]+").length)
                : sentences;
    }

    public long getSyllables() {
        if (syllables == NOT_CALCULATED) {
            syllables = getWordsStream().mapToInt(TextStatistics::countSyllables).sum();
        }
        return syllables;
    }

    public long getPolysyllables() {
        if (polysyllables == NOT_CALCULATED) {
            polysyllables = getWordsStream().filter(TextStatistics::isPolysyllable).count();
        }
        return polysyllables;
    }

    public String getText() {
        return text;
    }

    public static int countSyllables(final String word) {
        return max(1, word.toLowerCase()
                .replaceAll("e$", "")
                .replaceAll("[aeiouy]{2}", "a")
                .replaceAll("[^aeiouy]", "")
                .length());
    }

    public static boolean isPolysyllable(final String word) {
        return countSyllables(word) > 2;
    }

    public Stream<String> getWordsStream() {
        return Stream.of(text.split("[^\\p{Alpha}]+"));
    }

    @Override
    public String toString() {
        return String.format(join("%n",
                "The text is: %n%s",
                "Words: %d",
                "Sentences: %d",
                "Characters: %d",
                "Syllables: %d",
                "Polysyllables: %d%n"),
                getText(), getWords(), getSentences(), getCharacters(), getSyllables(), getPolysyllables());
    }
}
