package readability;

import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.String.join;

public class TextStatistics {
    final long characters;
    final long words;
    final long sentences;
    final long syllables;
    final long polysyllables;
    final String text;

    public TextStatistics(final String text) {
        this.text = text;
        characters = text.replaceAll("\\s", "").length();
        words = text.split(" ").length;
        sentences = text.split("[!?.]+").length;
        syllables = getWordsStream().mapToInt(TextStatistics::countSyllables).sum();
        polysyllables = getWordsStream().filter(TextStatistics::isPolysyllable).count();
    }

    static int countSyllables(final String word) {
        return max(1, word.toLowerCase()
                .replaceAll("e$", "")
                .replaceAll("[aeiouy]{2}", "a")
                .replaceAll("[^aeiouy]", "")
                .length());
    }

    static boolean isPolysyllable(final String word) {
        return countSyllables(word) > 2;
    }

    Stream<String> getWordsStream() {
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
                text, words, sentences, characters, syllables, polysyllables);
    }
}
