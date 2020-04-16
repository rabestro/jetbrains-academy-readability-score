package readability;

import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.String.join;

public class Application {
    private final TextStatistics text;

    Application(TextStatistics text) {
        this.text = text;
    }

    void run() {
        showTextStatistics();

        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        final var rsName = new Scanner(System.in).next().toUpperCase();
        final var isAll = rsName.equals(ReadabilityScores.ALL);

        Stream.of(ReadabilityScores.values())
                .filter(rs -> isAll || rs.name().equals(rsName))
                .forEach(rs -> rs.printScore(text));

        Stream.of(ReadabilityScores.values())
                .filter(rs -> isAll || rs.name().equals(rsName))
                .mapToInt(rs -> rs.getAge(text))
                .average()
                .ifPresentOrElse(
                        averageAge -> System.out.printf(
                                "This text should be understood in average by %.2f year olds.", averageAge),
                        () -> System.out.println("Wrong name of Readability Score!"));
    }

    void showTextStatistics() {
        System.out.printf(join("%n",
                "The text is: %n%s",
                "Words: %d",
                "Sentences: %d",
                "Characters: %d",
                "Syllables: %d",
                "Polysyllables: %d%n"),
                text.text, text.words, text.sentences, text.characters, text.syllables, text.polysyllables);
    }
}
