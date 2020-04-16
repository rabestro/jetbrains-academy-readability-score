package readability;

import java.util.stream.Stream;

import static java.lang.Math.max;

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

    /** Returns number of syllables in the word
     *
     * @param word
     *
     * To count the number of syllables you should use letters a, e, i, o, u, y as vowels.
     * @see <a href="https://simple.wikipedia.org/wiki/Vowel>Wikipedia</a>
     * You can see here examples and difficulties with determining vowels in a word with 100% accuracy.
     *
     *     So, let's use the following 4 rules:
     *
     *     1. Count the number of vowels in the word.
     *     2. Do not count double-vowels (for example, "rain" has 2 vowels but is only 1 syllable)
     *     3. If the last letter in the word is 'e' do not count it as a vowel (for example, "side" is 1 syllable)
     *     4. If at the end it turns out that the word contains 0 vowels, then consider this word as 1-syllable.
     */
    static int countSyllables(final String word) {
        return max(1, word.replaceAll("e$", "")
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
}
