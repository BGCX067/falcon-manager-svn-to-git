package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Ticket;

/**
 * @author Dmitry Paraschenko
 * @version 18.05.2008 20:26:10
 */
public class TicketInfo extends AbstractObjectInfo<Ticket> {
    private int flightId;
    private int seatId;
    private String clientName;
    private String clientPassport;

    public TicketInfo(int id) {
        super(id);
    }

    public TicketInfo(int id, int flightId, int seatId, String clientName, String clientPassport) {
        this(id);
        this.flightId = flightId;
        this.seatId = seatId;
        this.clientName = clientName;
        this.clientPassport = clientPassport;
    }

    public TicketInfo(int flightId, int seatId, String clientName, String clientPassport) {
        this(0, flightId, seatId, clientName, clientPassport);
    }

    public TicketInfo(Ticket object) {
        this(object.id);
        apply(object);
    }

    public int getFlightId() {
        return flightId;
    }

    public int getSeatId() {
        return seatId;
    }

    public AirplaneSeatInfo getSeat() {
        return DatabaseSnapshot.getInstance().getAirplaneSeats().get(seatId);
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPassport() {
        return clientPassport;
    }

    private void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    private void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    private void setClientName(String clientName) {
        this.clientName = clientName;
    }

    private void setClientPassport(String clientPassport) {
        this.clientPassport = clientPassport;
    }

    @Override
    public void addToDatabase() {
        DatabaseSnapshot.getInstance().addTicket(this);
    }

    @Override
    public void updateInDatabase() {
        DatabaseSnapshot.getInstance().updateTicket(this);
    }

    @Override
    public void removeFromDatabase() {
        DatabaseSnapshot.getInstance().removeTicket(this.getId());
    }

    @Override
    public void apply(Ticket object) {
        setFlightId(object.flightId);
        setSeatId(object.seatId);
        setClientName(object.clientName);
        setClientPassport(object.clientPassport);
    }

    @Override
    public void applyInfo(Object info) {
        TicketInfo object = (TicketInfo) info;
        setFlightId(object.flightId);
        setSeatId(object.seatId);
        setClientName(object.clientName);
        setClientPassport(object.clientPassport);
    }

    @Override
    public String toString() {
        return String.format("Ticket(id=%d, flightId=%d, seatId=%d, clientName=\"%s\", clientPassport=\"%s\")", getId(), getFlightId(), getSeatId(), getClientName(), getClientPassport());
    }
}
