package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Show;

public class CatalogService {
    private Map<String, Show> shows;

    public CatalogService() {
        this.shows = new HashMap<String, Show>();
    }

    public void addShow(Show show) {
        shows.put(show.getId(), show);
    }

    public Show getShow(String showId) {
        return shows.get(showId);
    }

    public List<Show> searchShows(String cityId, String movieId) {
        List<Show> result = new ArrayList<Show>();
        for (Show show : shows.values()) {
            if (show.getTheatre().getCity().getId().equals(cityId)
                    && show.getMovie().getId().equals(movieId)) {
                result.add(show);
            }
        }
        return result;
    }
}
