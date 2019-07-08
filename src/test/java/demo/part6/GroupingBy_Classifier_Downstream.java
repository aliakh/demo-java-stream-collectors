package demo.part6;

import demo.data.Area;
import demo.data.City;
import demo.data.Region;
import demo.data.USA;
import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static demo.data.Region.Far_West;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

public class GroupingBy_Classifier_Downstream {

    @Test
    public void collectors_groupingBy_averagingInt() {
        Map<Area, Double> averageCityPopulationPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, averagingInt(City::getPopulation)));

        System.out.println(averageCityPopulationPerArea
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toList()));
    }

    @Test
    public void collectors_groupingBy_counting() {
        Map<Area, Long> citiesCountPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, counting()));

        System.out.println(citiesCountPerArea
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toList()));
    }

    @Test
    public void collectors_groupingBy_filtering() {
        Map<Area, List<City>> citiesPerAreaInRegion = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, filtering(city -> city.getRegion().equals(Far_West), toList())));

        System.out.println(citiesPerAreaInRegion);
    }

    @Test
    public void collectors_groupingBy_flatMapping() {
        Map<Area, List<Area>> areasInSameRegion = USA.CITIES.stream()
                .collect(groupingBy(City::getArea,
                        flatMapping(city -> city.getRegion().getAreas().stream(), toList())));

        System.out.println(areasInSameRegion);
    }

    @Test
    public void collectors_groupingBy_groupingBy() {
        Map<Region, Map<Area, List<City>>> regionsToAreasToCities = USA.CITIES.stream()
                .collect(groupingBy(City::getRegion, groupingBy(City::getArea)));

        System.out.println(regionsToAreasToCities);
    }

    @Test
    public void collectors_groupingBy_joining() {
        Map<Character, String> cityNamesPerFirstLetter = USA.CITIES.stream()
                .map(City::getName)
                .collect(groupingBy(s -> s.charAt(0), joining(", ", "[", "]")));

        System.out.println(cityNamesPerFirstLetter);
    }

    @Test
    public void collectors_groupingBy_mapping() {
        Map<Area, List<String>> cityNamesPerRegion = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, mapping(City::getName, toList())));

        System.out.println(cityNamesPerRegion);
    }

    @Test
    public void collectors_groupingBy_maxBy() {
        Map<Area, Optional<City>> cityMaxPopulationPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, maxBy(comparingInt(City::getPopulation))));

        System.out.println(cityMaxPopulationPerArea);
    }

    @Test
    public void collectors_groupingBy_minBy() {
        Map<Area, Optional<City>> cityMinPopulationPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, minBy(comparingInt(City::getPopulation))));

        System.out.println(cityMinPopulationPerArea);
    }

    @Test
    public void collectors_groupingBy_reducing() {
        Map<Character, Optional<String>> cityNamesPerFirstLetter = USA.CITIES.stream()
                .map(City::getName)
                .collect(groupingBy(s -> s.charAt(0), reducing((s1, s2) -> s1 + ',' + s2)));

        System.out.println(cityNamesPerFirstLetter);
    }

    @Test
    public void collectors_groupingBy_summarizingInt() {
        Map<Area, IntSummaryStatistics> statisticsPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, summarizingInt(City::getPopulation)));

        System.out.println(statisticsPerArea);
    }

    @Test
    public void collectors_groupingBy_summingInt() {
        Map<Area, Integer> populationsPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, summingInt(City::getPopulation)));

        System.out.println(populationsPerArea
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toList()));
    }
}
