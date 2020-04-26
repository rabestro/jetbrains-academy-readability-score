package readability;

import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

public class Application {
    private final TextStatistics text;

    Application(TextStatistics text) {
        this.text = text;
    }

    void run() {
        printTextStatistics();

        System.out.printf("Enter the score you want to calculate (%s, all):%n",
                Stream.of(ReadabilityScores.values()).map(Enum::toString).collect(joining(", ")));

        final var rsName = new Scanner(System.in).next().toUpperCase();
        final var isAll = rsName.equals(ReadabilityScores.ALL);

        Stream.of(ReadabilityScores.values())
                .filter(rs -> isAll || rs.name().equals(rsName))
                .peek(rs -> System.out.println(rs.getScoreAndAge(text)))
                .mapToInt(rs -> rs.getAge(text))
                .average()
                .ifPresentOrElse(this::printAverage, this::printErrorMessage);
    }

    void printErrorMessage() {
        System.out.println("Wrong name of Readability Score!");
    }

    void printAverage(double averageAge) {
        System.out.printf("This text should be understood in average by %.2f year olds.", averageAge);
    }

    void printTextStatistics() {
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
