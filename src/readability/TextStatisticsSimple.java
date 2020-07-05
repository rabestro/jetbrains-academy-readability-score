package readability;

public class TextStatisticsSimple implements TextStatistics {
    private final String text;
    private final long characters;
    private final long words;
    private final long sentences;
    private final long syllables;
    private final long polysyllables;

    public TextStatisticsSimple(final String text) {
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
}
