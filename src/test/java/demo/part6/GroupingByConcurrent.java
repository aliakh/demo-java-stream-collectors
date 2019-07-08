package demo.part6;

import demo.data.Area;
import demo.data.City;
import demo.data.USA;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class GroupingByConcurrent {

    @Test
    public void collectors_groupingByConcurrent_classifier() {
        ConcurrentMap<Area, List<City>> citiesPerArea = USA.CITIES
                .parallelStream()
                .collect(groupingByConcurrent(City::getArea));

        System.out.println(citiesPerArea);
    }

    @Test
    public void collectors_groupingByConcurrent_classifier_downstream() {
        ConcurrentMap<Area, Set<City>> citiesPerArea = USA.CITIES
                .parallelStream()
                .collect(groupingByConcurrent(City::getArea, toSet()));

        System.out.println(citiesPerArea);
    }

    @Test
    public void collectors_groupingByConcurrent_classifier_mapFactory_downstream() {
        ConcurrentMap<Area, List<City>> citiesPerArea = USA.CITIES
                .parallelStream()
                .collect(groupingByConcurrent(City::getArea, ConcurrentHashMap::new, toList()));

        System.out.println(citiesPerArea);
    }
}
