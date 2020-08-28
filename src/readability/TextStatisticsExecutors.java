package readability;

import java.util.concurrent.*;

public class TextStatisticsExecutors implements TextStatistics {
    private final String text;
    private final Future<Long> characters;
    private final Future<Long> words;
    private final Future<Long> sentences;
    private final Future<Long> syllables;
    private final Future<Long> polysyllables;

    public TextStatisticsExecutors(final String text) {
        this.text = text;
        final var executor = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        characters = executor.submit(() -> 
                (long) text.replaceAll("\\s", "").length());
        words = executor.submit(() -> 
                WORDS_DELIMITER.splitAsStream(text).count());
        sentences = executor.submit(() -> 
                SENTENCES_DELIMITER.splitAsStream(text).count());
        syllables = executor.submit(() -> 
                WORDS_DELIMITER.splitAsStream(text).mapToLong(TextStatistics::countSyllables).sum());
        polysyllables = executor.submit(() -> 
                WORDS_DELIMITER.splitAsStream(text).filter(TextStatistics::isPolysyllable).count());
        
        executor.shutdown();
    }

    @Override
    public long getCharacters() {
        try {
            return characters.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public long getWords() {
        try {
            return words.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public long getSentences() {
        try {
            return sentences.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public long getSyllables() {
        try {
            return syllables.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public long getPolysyllables() {
        try {
            return polysyllables.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getText() {
        return text;
    }
}
