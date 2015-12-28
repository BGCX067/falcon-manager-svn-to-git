package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Flight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 14:51:36
 */
public class FlightInfo extends AbstractObjectInfo<Flight> {
    private final static DateFormat DATABASE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    
    private int departureAirportId;
    private Date departureDate;
    private int arrivalAirportId;
    private Date arrivalDate;
    private int airplaneId;

    public FlightInfo(int id) {
        super(id);
    }

    public FlightInfo(int id, int departureAirportId, Date departureDate, int arrivalAirportId, Date arrivalDate, int airplaneId) {
        this(id);
        this.departureAirportId = departureAirportId;
        this.departureDate = departureDate;
        this.arrivalAirportId = arrivalAirportId;
        this.arrivalDate = arrivalDate;
        this.airplaneId = airplaneId;
    }

    public FlightInfo(int departureAirportId, Date departureDate, int arrivalAirportId, Date arrivalDate, int airplaneId) {
        this(0, departureAirportId, departureDate, arrivalAirportId, arrivalDate, airplaneId);
    }

    public FlightInfo(Flight object) {
        this(object.id);
        apply(object);
    }

    public int getDepartureAirportId() {
        return departureAirportId;
    }

    public AirportInfo getDepartureAirport() {
        return DatabaseSnapshot.getInstance().getAirports().get(departureAirportId);
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public int getArrivalAirportId() {
        return arrivalAirportId;
    }

    public AirportInfo getArrivalAirport() {
        return DatabaseSnapshot.getInstance().getAirports().get(arrivalAirportId);
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public AbstractObjectInfo getAirplane() {
        return DatabaseSnapshot.getInstance().getAirplanes().get(airplaneId);
    }

    private void setDepartureAirportId(int departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    private void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    private void setArrivalAirportId(int arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    private void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    private void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    @Override
    public void addToDatabase() {
        DatabaseSnapshot.getInstance().addFlight(this);
    }

    @Override
    public void updateInDatabase() {
        DatabaseSnapshot.getInstance().updateFlight(this);
    }

    @Override
    public void removeFromDatabase() {
        DatabaseSnapshot.getInstance().deleteFlight(this.getId());
    }

    @Override
    public void apply(Flight object) {
        setDepartureAirportId(object.departureAirportId);
        setDepartureDate(FlightInfo.parseDate(object.departureDate));
        setArrivalAirportId(object.arrivalAirportId);
        setArrivalDate(FlightInfo.parseDate(object.arrivalDate));
        setAirplaneId(object.airplaneId);
    }

    @Override
    public void applyInfo(Object info) {
        FlightInfo object = (FlightInfo) info;
        setDepartureAirportId(object.departureAirportId);
        setDepartureDate(object.departureDate);
        setArrivalAirportId(object.arrivalAirportId);
        setArrivalDate(object.arrivalDate);
        setAirplaneId(object.airplaneId);
    }

    @Override
    public String toString() {
        return String.format("Flight(id=%d, departureAirportId=%d, departureDate=\"%s\", arrivalAirportId=%d, arrivalDate=\"%s\", airplaneId=%d)", getId(), getDepartureAirportId(), getDepartureDate(), getArrivalAirportId(), getArrivalDate(), getAirplaneId());
    }

    public static String formatDate(Date date) {
        synchronized (DATABASE_DATE_FORMAT) {
            return DATABASE_DATE_FORMAT.format(date);
        }
    }

    public static Date parseDate(String string) {
        try {
            synchronized (DATABASE_DATE_FORMAT) {
                return DATABASE_DATE_FORMAT.parse(string);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}