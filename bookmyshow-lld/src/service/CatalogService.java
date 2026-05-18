package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.City;
import model.Movie;
import model.Show;
import model.Theatre;

public class CatalogService {
    private Map<String, City> cities;
    private Map<String, Theatre> theatres;
    private Map<String, Movie> movies;
    private Map<String, Show> shows;

    public CatalogService() {
        this.cities = new HashMap<String, City>();
        this.theatres = new HashMap<String, Theatre>();
        this.movies = new HashMap<String, Movie>();
        this.shows = new HashMap<String, Show>();
    }

    public void addCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        cities.put(city.getId(), city);
    }

    public void addTheatre(Theatre theatre) {
        if (theatre == null) {
            throw new IllegalArgumentException("Theatre cannot be null");
        }
        if (theatre.getCity() == null || cities.get(theatre.getCity().getId()) == null) {
            throw new IllegalArgumentException("Theatre city must exist");
        }
        theatres.put(theatre.getId(), theatre);
    }

    public void addMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        movies.put(movie.getId(), movie);
    }

    public void addShow(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show cannot be null");
        }
        if (movies.get(show.getMovie().getId()) == null) {
            throw new IllegalArgumentException("Show movie must exist");
        }
        if (theatres.get(show.getTheatre().getId()) == null) {
            throw new IllegalArgumentException("Show theatre must exist");
        }
        shows.put(show.getId(), show);
    }

    public Show getShow(String showId) {
        if (showId == null || showId.length() == 0) {
            throw new IllegalArgumentException("Show id is required");
        }
        return shows.get(showId);
    }

    public List<Show> searchShows(String cityId, String movieId) {
        if (cityId == null || cityId.length() == 0) {
            throw new IllegalArgumentException("City id is required");
        }
        if (movieId == null || movieId.length() == 0) {
            throw new IllegalArgumentException("Movie id is required");
        }

        List<Show> result = new ArrayList<Show>();
        for (Show show : shows.values()) {
            boolean sameCity = show.getTheatre().getCity().getId().equals(cityId);
            boolean sameMovie = show.getMovie().getId().equals(movieId);
            if (sameCity && sameMovie) {
                result.add(show);
            }
        }
        return result;
    }
}
