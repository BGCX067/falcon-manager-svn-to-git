package ru.ifmo.falconmanager.database;

import java.util.Date;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 23:57:49
 */
public class FilteredFlightStore extends FilteredObjectStore<FlightInfo> {
    private int departureAirportId;
    private int arrivalAirportId;
    private Date departureDate;

    public FilteredFlightStore(ObjectStore<FlightInfo> store) {
        super(store);
    }

    public int getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(int departureAirportId) {
        if (this.departureAirportId != departureAirportId) {
            this.departureAirportId = departureAirportId;
            updated();
        }
    }

    public int getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(int arrivalAirportId) {
        if (this.arrivalAirportId != arrivalAirportId) {
            this.arrivalAirportId = arrivalAirportId;
            updated();
        }
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        if (this.departureDate == departureDate) {
            return;
        }
        if (this.departureDate == null || departureDate == null || !this.departureDate.equals(departureDate)) {
            this.departureDate = departureDate;
            updated();
        }
    }
    
    protected boolean isAccepted(FlightInfo object) {
        if (departureAirportId != 0 && object.getDepartureAirportId() != departureAirportId) {
            return false;
        }
        if (arrivalAirportId != 0 && object.getArrivalAirportId() != arrivalAirportId) {
            return false;
        }
        Date date = object.getDepartureDate();
        return departureDate == null || date.getYear() == departureDate.getYear() && date.getMonth() == departureDate.getMonth() && date.getDate() == departureDate.getDate();
    }
}