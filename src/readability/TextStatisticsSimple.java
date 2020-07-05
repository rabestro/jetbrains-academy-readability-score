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
}
