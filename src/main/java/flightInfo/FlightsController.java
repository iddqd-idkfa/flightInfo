package flightInfo;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for collecting and returning flight data from two endpoints
 */
@Controller
public class FlightsController {

    private final int recordPerPage = 10;
    private static List<Flight> flights;
    private final String businessEndpoint = "https://obscure-caverns-79008.herokuapp.com/business";
    private final String economyEndpoint = "https://obscure-caverns-79008.herokuapp.com/cheap";
    private static String prevSort;

    static {
        flights = new ArrayList<Flight>();
        prevSort = "";
    }

    /**
     * @param rd Reader
     * @return String
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * @param url String
     * @return JSONArray
     * @throws IOException
     * @throws JSONException
     */
    private static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * @param page Integer
     * @param sort String
     * @return
     */
    @GetMapping("/flights")
    @ResponseBody
    public Result flights(@RequestParam(name = "page", required = false, defaultValue = "-1") Integer page,
                          @RequestParam(name = "sort", required = false, defaultValue = "") String sort) {
        boolean ascending;
        Comparator<Flight> comparator = null;
        if (page <0) {
            prevSort="";
        }

        try {
            if (sort != null && !sort.isEmpty()) {
                if (sort.charAt(0) == '-') {
                    ascending = false;
                } else {
                    ascending = true;
                }
                String sortField = sort.substring(1);

                switch (sortField) {
                    case "id":
                        comparator = new Flight.IdSorter(ascending);
                        break;
                    case "from":
                        comparator = new Flight.FromSorter(ascending);
                        break;
                    case "to":
                        comparator = new Flight.ToSorter(ascending);
                        break;
                    case "arrival":
                        comparator = new Flight.ArrivalSorter(ascending);
                        break;
                    case "departure":
                        comparator = new Flight.DepartureSorter(ascending);
                        break;
                    default:
                        break;
                }
                System.out.println(sort);
                System.out.println(comparator);
                //if we already sorted in this order, avoid re-sorting
                if (!sort.equals(prevSort) && comparator != null) {
                    Collections.sort(flights, comparator);
                    //flag for remembering the last sort operation
                    prevSort = sort;
                    System.out.println("field or order direction changed... Re-sorting");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (page > -1) {
            List<Flight> flightPage = new ArrayList<Flight>();
            for (int i = page * recordPerPage; i < flights.size(); i++) {
                if (i >= (page + 1) * recordPerPage)
                    break;
                flightPage.add(flights.get(i));
            }
            return new Result(flightPage, (int) Math.ceil(((double) flights.size()) / recordPerPage));
        }
        flights.clear();
        try {
            JSONArray json = readJsonFromUrl(businessEndpoint);

            for (int i = 0; i < json.length(); i++) {
                JSONObject o = (JSONObject) json.getJSONObject(i);
                flights.add(new Flight(o.getString("uuid"),
                        o.getString("flight").split("->")[0],
                        o.getString("flight").split("->")[1],
                        o.getString("departure"),
                        o.getString("arrival")));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        List<Flight> flightPage = new ArrayList<Flight>();
        try {
            JSONArray json = readJsonFromUrl(economyEndpoint);

            for (int i = 0; i < json.length(); i++) {
                JSONObject o = (JSONObject) json.getJSONObject(i);
                Flight f = new Flight(o.getLong("id"),
                        o.getString("departure"),
                        o.getString("arrival"),
                        o.getLong("departureTime"),
                        o.getLong("arrivalTime"));
                flights.add(f);
            }
            if (comparator != null)
                Collections.sort(flights, comparator);

            for (int i = 0; i < recordPerPage; i++) {
                flightPage.add(flights.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new Result(flightPage, (int) Math.ceil(((double) flights.size()) / recordPerPage));
    }

}
