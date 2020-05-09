package readability;

import java.util.regex.Pattern;

import static java.lang.Math.max;

public class TextStatistics {
    private static final long NOT_CALCULATED = -1;
    private static final Pattern WORDS_DELIMITER = Pattern.compile("[^\\p{Alpha}]+");
    private static final Pattern SENTENCES_DELIMITER = Pattern.compile("[!?.]+");
    private static final Pattern PATTERN_SYLLABLE = Pattern.compile("([aiouy]|e(?!$))+");

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
            words = WORDS_DELIMITER.splitAsStream(text).count();
        }
        return words;
    }

    public long getSentences() {
        if (sentences == NOT_CALCULATED) {
            sentences = SENTENCES_DELIMITER.splitAsStream(text).count();
        }
        return sentences;
    }

    public long getSyllables() {
        if (syllables == NOT_CALCULATED) {
            syllables = WORDS_DELIMITER.splitAsStream(text).mapToLong(TextStatistics::countSyllables).sum();
        }
        return syllables;
    }

    public long getPolysyllables() {
        if (polysyllables == NOT_CALCULATED) {
            polysyllables = WORDS_DELIMITER.splitAsStream(text).filter(TextStatistics::isPolysyllable).count();
        }
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
