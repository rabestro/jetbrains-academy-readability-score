package readability;

public class TextStatisticsLazy implements TextStatistics {
    private static final long NOT_CALCULATED = -1;

    private final String text;
    private long characters = NOT_CALCULATED;
    private long words = NOT_CALCULATED;
    private long sentences = NOT_CALCULATED;
    private long syllables = NOT_CALCULATED;
    private long polysyllables = NOT_CALCULATED;

    public TextStatisticsLazy(final String text) {
        this.text = text;
    }

    @Override
    public long getCharacters() {
        if (characters == NOT_CALCULATED) {
            characters = text.replaceAll("\\s", "").length();
        }
        return characters;
    }

    @Override
    public long getWords() {
        if (words == NOT_CALCULATED) {
            words = WORDS_DELIMITER
                    .splitAsStream(text)
                    .count();
        }
        return words;
    }

    @Override
    public long getSentences() {
        if (sentences == NOT_CALCULATED) {
            sentences = SENTENCES_DELIMITER
                    .splitAsStream(text)
                    .count();
        }
        return sentences;
    }

    @Override
    public long getSyllables() {
        if (syllables == NOT_CALCULATED) {
            syllables = WORDS_DELIMITER
                    .splitAsStream(text)
                    .mapToLong(TextStatistics::countSyllables)
                    .sum();
        }

        return syllables;
    }

    @Override
    public long getPolysyllables() {
        if (polysyllables == NOT_CALCULATED) {
            polysyllables = WORDS_DELIMITER
                    .splitAsStream(text)
                    .filter(TextStatistics::isPolysyllable)
                    .count();
        }
        return polysyllables;
    }

    @Override
    public String getText() {
        return text;
    }

}
