package readability;

import java.util.function.ToDoubleFunction;

import static java.lang.Math.*;

public enum ReadabilityScores {
    ARI("Automated Readability Index",
            text -> 4.71 * text.characters / text.words + 0.5 * text.words / text.sentences - 21.43),

    FK("Flesch–Kincaid readability tests",
            text -> 0.39 * text.words / text.sentences + 11.8 * text.syllables / text.words - 15.59),

    SMOG("Simple Measure of Gobbledygook",
            text -> 1.043 * sqrt(text.polysyllables * 30. / text.sentences) + 3.1291),

    CL("Coleman–Liau index",
            text -> {
                final double l = 100. * text.characters / text.words;
                final double s = 100. * text.sentences / text.words;
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

    void printScore(final TextStatistics text) {
        final double score = formula.applyAsDouble(text);
        System.out.printf("%s: %.2f (about %d year olds).%n", fullName, score, calculateAge(score));
    }

    int getAge(final TextStatistics text) {
        return calculateAge(formula.applyAsDouble(text));
    }
}
