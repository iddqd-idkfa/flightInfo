package flightInfo;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.TimeZone;

/**
 * Flight class for packaging flight data from json
 */
public class Flight {

    /* member variables */
    private String uuid;
    private String from;
    private String to;
    private ZonedDateTime departure;
    private ZonedDateTime arrival;

    /* getters and setters for member variables - start */
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ZonedDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(ZonedDateTime departure) {
        this.departure = departure;
    }

    public ZonedDateTime getArrival() {
        return arrival;
    }

    public void setArrival(ZonedDateTime arrival) {
        this.arrival = arrival;
    }
    /* getters and setters - end */

    /**
     * Sorter/Comparator based on uuid
     */
    public static class IdSorter implements Comparator<Flight>
    {
        private boolean ascending;
        IdSorter(boolean ascending) {
            this.ascending = ascending;
        }

        @Override
        public int compare(Flight o1, Flight o2) {
            if(!ascending)
                return o2.uuid.compareTo(o1.uuid);
            return o1.uuid.compareTo(o2.uuid);
        }
    }

    /**
     * Sorter/Comparator based on flight 'from' info
     */
    public static class FromSorter implements Comparator<Flight>
    {
        private boolean ascending;
        FromSorter(boolean ascending) {
            this.ascending = ascending;
        }

        @Override
        public int compare(Flight o1, Flight o2) {
            if(!ascending)
                return o2.from.compareTo(o1.from);
            return o1.from.compareTo(o2.from);
        }
    }

    /**
     * Sorter/Comparator based on flight 'to' info
     */
    public static class ToSorter implements Comparator<Flight>
    {
        private boolean ascending;
        public ToSorter(boolean ascending) {
            this.ascending = ascending;
        }

        @Override
        public int compare(Flight o1, Flight o2) {
            if(!ascending)
                return o2.to.compareTo(o1.to);
            return o1.to.compareTo(o2.to);
        }
    }

    /**
     * Sorter/Comparator based on flight arrival time
     */
    public static class ArrivalSorter implements Comparator<Flight>
    {
        private boolean ascending;
        public ArrivalSorter(boolean ascending) {
            this.ascending = ascending;
        }

        @Override
        public int compare(Flight o1, Flight o2) {
            if(!ascending)
                return o2.arrival.compareTo(o1.arrival);
            return o1.arrival.compareTo(o2.arrival);
        }
    }

    /**
     * Sorter/Comparator based on flight departure time
     */
    public static class DepartureSorter implements Comparator<Flight>
    {
        private boolean ascending;
        public DepartureSorter(boolean ascending) {
            this.ascending = ascending;
        }

        @Override
        public int compare(Flight o1, Flight o2) {
            if(!ascending)
                return o2.departure.compareTo(o1.departure);
            return o1.departure.compareTo(o2.departure);
        }
    }

    /**
     * dummy constructor
     */
    public Flight() {
        this.uuid = "dummy";
    }

    /**
     * Flight constructor for /business endpoint
     * @param id String
     * @param from String
     * @param to String
     * @param departure String
     * @param arrival String
     */
    Flight(String id, String from, String to, String departure, String arrival) {
        this.uuid = id.trim();
        this.from = from.trim();
        this.to = to.trim();
        this.departure = ZonedDateTime.parse(departure);
        this.arrival = ZonedDateTime.parse(arrival);
    }

    /**
     * Flight constructor for /cheap endpoint
     * @param id long
     * @param from String
     * @param to String
     * @param departure long
     * @param arrival long
     */
    Flight(long id, String from, String to, long departure, long arrival) {
        this.uuid = String.valueOf(id).trim();
        this.from = from.trim();
        this.to = to.trim();
        this.departure = ZonedDateTime.ofInstant(Instant.ofEpochMilli(departure),
                TimeZone.getTimeZone("Z").toZoneId());
        this.arrival = ZonedDateTime.ofInstant(Instant.ofEpochMilli(arrival),
                TimeZone.getTimeZone("Z").toZoneId());
    }

}
