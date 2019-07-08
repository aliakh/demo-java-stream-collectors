package demo.data;

import java.util.Set;

import static demo.data.Area.*;

/**
 * USA Regions (by Bureau of Economic Analysis).
 */
public enum Region {

    New_England(Connecticut, Maine, Massachusetts, New_Hampshire, Rhode_Island, Vermont),
    Mideast(Delaware, District_of_Columbia, Maryland, New_Jersey, New_York, Pennsylvania),
    Great_Lakes(Illinois, Indiana, Michigan, Ohio, Wisconsin),
    Plains(Iowa, Kansas, Minnesota, Missouri, Nebraska, North_Dakota, South_Dakota),
    Southeast(Alabama, Arkansas, Florida, Georgia, Kentucky, Louisiana, Mississippi, North_Carolina, South_Carolina, Tennessee, Virginia, West_Virginia),
    Southwest(Arizona, New_Mexico, Oklahoma, Texas),
    Rocky_Mountain(Colorado, Idaho, Montana, Utah, Wyoming),
    Far_West(Alaska, California, Hawaii, Nevada, Oregon, Washington);

    private final Set<Area> areas;

    Region(Area... areas) {
        this.areas = Set.of(areas);
    }

    public Set<Area> getAreas() {
        return areas;
    }
}
