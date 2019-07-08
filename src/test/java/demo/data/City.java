package demo.data;

public class City {

    private final String name;
    private final Area area;
    private final Region region;
    private final int population;

    public static City of(String name, Area state, int population) {
        return new City(name, state, population);
    }

    private City(String name, Area area, int population) {
        this.name = name;
        this.area = area;
        this.region = area.getRegion();
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    public Region getRegion() {
        return region;
    }

    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return name;
    }
}
