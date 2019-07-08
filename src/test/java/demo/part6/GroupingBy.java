package demo.part6;

import demo.data.Area;
import demo.data.City;
import demo.data.USA;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class GroupingBy {

    @Test
    public void collectors_groupingBy_classifier() {
        Map<Area, List<City>> citiesPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea));

        System.out.println(citiesPerArea);
    }

    @Test
    public void collectors_groupingBy_classifier_downstream() {
        Map<Area, Set<City>> citiesPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, toSet()));

        System.out.println(citiesPerArea);
    }

    @Test
    public void collectors_groupingBy_classifier_mapFactory_downstream() {
        EnumMap<Area, List<City>> citiesPerArea = USA.CITIES.stream()
                .collect(groupingBy(City::getArea, () -> new EnumMap<>(Area.class), toList()));

        System.out.println(citiesPerArea);
    }
}

