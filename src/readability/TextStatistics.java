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

    static TextStatistics from(final String text) {
        final var characters = text.replaceAll("\\s", "").length();
        final var words = WORDS_DELIMITER.splitAsStream(text).count();
        final var sentences = SENTENCES_DELIMITER.splitAsStream(text).count();
        final var syllables = WORDS_DELIMITER.splitAsStream(text)
                .mapToLong(TextStatistics::countSyllables)
                .sum();
        final var polysyllables = WORDS_DELIMITER.splitAsStream(text)
                .filter(TextStatistics::isPolysyllable)
                .count();

        return new TextStatistics() {
            @Override
            public long getCharacters() {
                return characters;
            }

            @Override
            public long getWords() {
                return words;
            }

            @Override
            public long getSentences() {
                return sentences;
            }

            @Override
            public long getSyllables() {
                return syllables;
            }

            @Override
            public long getPolysyllables() {
                return polysyllables;
            }

            @Override
            public String getText() {
                return text;
            }
        };
    }
}
