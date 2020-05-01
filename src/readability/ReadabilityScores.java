package readability;

import java.util.function.ToDoubleFunction;

import static java.lang.Math.*;

public enum ReadabilityScores {
    ARI("Automated Readability Index",
            text -> 4.71 * text.getCharacters() / text.getWords()
                    + 0.5 * text.getWords() / text.getSentences() - 21.43),

    FK("Flesch–Kincaid readability tests",
            text -> 0.39 * text.getWords() / text.getSentences()
                    + 11.8 * text.getSyllables() / text.getWords() - 15.59),

    SMOG("Simple Measure of Gobbledygook",
            text -> 1.043 * sqrt(text.getPolysyllables() * 30. / text.getSentences()) + 3.1291),

    CL("Coleman–Liau index",
            text -> {
                final double l = 100. * text.getCharacters() / text.getWords();
                final double s = 100. * text.getSentences() / text.getWords();
                return 0.0588 * l - 0.296 * s - 15.8;
            });

    public static final String ALL = "ALL";

    private final String fullName;
    private final ToDoubleFunction<TextStatistics> formula;

    ReadabilityScores(final String fullName, final ToDoubleFunction<TextStatistics> formula) {
        this.fullName = fullName;
        this.formula = formula;
    }

    static int calculateAge(final double score) {
        final int level = min(14, max(1, (int) ceil(score))) - 1;
        return new int[]{6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 25}[level];
    }

    String getScoreAndAge(final TextStatistics text) {
        final double score = formula.applyAsDouble(text);
        return String.format("%s: %.2f (about %d year olds).%n", fullName, score, calculateAge(score));
    }

    int getAge(final TextStatistics text) {
        return calculateAge(formula.applyAsDouble(text));
    }
}
