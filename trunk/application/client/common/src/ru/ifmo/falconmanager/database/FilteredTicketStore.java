package ru.ifmo.falconmanager.database;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 23:57:49
 */
public class FilteredTicketStore extends FilteredObjectStore<TicketInfo> {
    private int flightId;

    public FilteredTicketStore(ObjectStore<TicketInfo> store) {
        super(store);
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        if (this.flightId != flightId) {
            this.flightId = flightId;
            updated();
        }
    }

    protected boolean isAccepted(TicketInfo object) {
        return object.getFlightId() == flightId;
    }
}
