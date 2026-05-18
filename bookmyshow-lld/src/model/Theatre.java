package model;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private City city;
    private List<Screen> screens;

    public Theatre(String id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.screens = new ArrayList<Screen>();
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return screens;
    }
}
