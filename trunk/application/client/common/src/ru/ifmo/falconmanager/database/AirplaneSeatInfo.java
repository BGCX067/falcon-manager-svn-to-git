package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.AirplaneSeat;

/**
 * @author Dmitry Paraschenko
 * @version 18.05.2008 10:40:41
 */
public class AirplaneSeatInfo extends AbstractObjectInfo<AirplaneSeat> {
    private int airplaneId;
    private String seat;

    public AirplaneSeatInfo(int id) {
        super(id);
    }

    public AirplaneSeatInfo(int id, int airplaneId, String name) {
        this(id);
        this.airplaneId = airplaneId;
        this.seat = name;
    }

    public AirplaneSeatInfo(int airplaneId, String name) {
        this(0, airplaneId, name);
    }

    public AirplaneSeatInfo(AirplaneSeat object) {
        this(object.id);
        apply(object);
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public AbstractObjectInfo getAirplane() {
        return DatabaseSnapshot.getInstance().getAirplanes().get(airplaneId);
    }

    public String getSeat() {
        return seat;
    }

    private void setSeat(String seat) {
        this.seat = seat;
    }

    private void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    @Override
    public void addToDatabase() {
        DatabaseSnapshot.getInstance().addAirplaneSeat(this);
    }

    @Override
    public void updateInDatabase() {
        DatabaseSnapshot.getInstance().updateAirplaneSeat(this);
    }

    @Override
    public void removeFromDatabase() {
        DatabaseSnapshot.getInstance().deleteAirplaneSeat(this.getId());
    }

    @Override
    public void apply(AirplaneSeat object) {
        setAirplaneId(object.airplaneId);
        setSeat(object.seat);
    }

    @Override
    public void applyInfo(Object info) {
        AirplaneSeatInfo object = (AirplaneSeatInfo) info;
        setAirplaneId(object.airplaneId);
        setSeat(object.seat);
    }

    @Override
    public String toString() {
        return String.format("AirplaneSeat(id=%d, airplaneId=%d, seat=\"%s\")", getId(), getAirplaneId(), getSeat());
    }
}
