package readability;

import java.util.regex.Pattern;

import static java.lang.Math.max;

public class TextStatistics {
    private static final long NOT_CALCULATED = -1;
    private static final Pattern SPLIT_WORDS = Pattern.compile("[^\\p{Alpha}]+");
    private static final Pattern SPLIT_SENTENCES = Pattern.compile("[!?.]+");
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
        if (words == NOT_CALCULATED) {
            words = SPLIT_WORDS.splitAsStream(text).count();
        }
        return words;
    }

    public long getSentences() {
        if (sentences == NOT_CALCULATED) {
            sentences = SPLIT_SENTENCES.splitAsStream(text).count();
        }
        return sentences;
    }

    public long getSyllables() {
        if (syllables == NOT_CALCULATED) {
            syllables = SPLIT_WORDS.splitAsStream(text).mapToInt(TextStatistics::countSyllables).sum();
        }
        return syllables;
    }

    public long getPolysyllables() {
        if (polysyllables == NOT_CALCULATED) {
            polysyllables = SPLIT_WORDS.splitAsStream(text).filter(TextStatistics::isPolysyllable).count();
        }
        return polysyllables;
    }

    public String getText() {
        return text;
    }

    private static int countSyllables(final String word) {
        return max(1, word.toLowerCase()
                .replaceAll("e$", "")
                .replaceAll("[aeiouy]{2}", "a")
                .replaceAll("[^aeiouy]", "")
                .length());
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
