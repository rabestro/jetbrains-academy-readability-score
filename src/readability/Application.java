package readability;

import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Application {
    private final TextStatistics textStatistics;

    Application(TextStatistics text) {
        this.textStatistics = text;
    }

    void run() {
        System.out.println(textStatistics);
        System.out.printf("Enter the score you want to calculate (%s, all):%n",
                Stream.of(ReadabilityScores.values()).map(Enum::toString).collect(joining(", ")));

        final var rsName = new Scanner(System.in).next().toUpperCase();
        final var isAll = rsName.equals(ReadabilityScores.ALL);

        Stream.of(ReadabilityScores.values())
                .filter(rs -> isAll || rs.name().equals(rsName))
                .peek(rs -> System.out.println(rs.getScoreAndAge(textStatistics)))
                .mapToInt(rs -> rs.getAge(textStatistics))
                .average()
                .ifPresentOrElse(this::printAverage, this::printErrorMessage);
    }

    private void printErrorMessage() {
        System.out.println("Wrong name of Readability Score!");
    }

    private void printAverage(double averageAge) {
        System.out.printf("This text should be understood in average by %.2f year olds.", averageAge);
    }
}
