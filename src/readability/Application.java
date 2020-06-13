package readability;

import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Application {
    private final TextStatistics textStatistics;

    Application(TextStatistics textStatistics) {
        this.textStatistics = textStatistics;
    }

    void run() {
        System.out.println(textStatistics);
        System.out.printf("Enter the score you want to calculate (%s, all):%n", ReadabilityScores.getShortNames());

        final var scoreName = new Scanner(System.in).next().toUpperCase();
        final var isSelectedAll = scoreName.equals(ReadabilityScores.ALL);

        final Predicate<ReadabilityScores> isSelected =
                score -> score.name().equals(scoreName) || isSelectedAll;

        Stream.of(ReadabilityScores.values())
                .filter(isSelected)
                .peek(this::printScoreAndAge)
                .mapToInt(rs -> rs.getAge(textStatistics))
                .average()
                .ifPresentOrElse(this::printAverage, this::printErrorMessage);
    }

    private void printScoreAndAge(ReadabilityScores readabilityScore) {
        System.out.println(readabilityScore.getScoreAndAge(textStatistics));
    }

    private void printAverage(double averageAge) {
        System.out.printf("%nThis text should be understood in average by %.2f year olds.", averageAge);
    }

    private void printErrorMessage() {
        System.out.println("Wrong name of Readability Score!");
    }
}
