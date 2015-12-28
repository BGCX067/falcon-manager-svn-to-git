package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Airport;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 14:51:36
 */
public class AirportInfo extends AbstractObjectInfo<Airport> {
    private String shortName;
    private String fullName;

    public AirportInfo(int id) {
        super(id);
    }

    public AirportInfo(int id, String shortName, String fullName) {
        this(id);
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public AirportInfo(String shortName, String fullName) {
        this(0, shortName, fullName);
    }

    public AirportInfo(Airport object) {
        this(object.id);
        apply(object);
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    private void setShortName(String shortName) {
        this.shortName = shortName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public void addToDatabase() {
        DatabaseSnapshot.getInstance().addAirport(this);
    }

    @Override
    public void updateInDatabase() {
        DatabaseSnapshot.getInstance().updateAirport(this);
    }

    @Override
    public void removeFromDatabase() {
        DatabaseSnapshot.getInstance().deleteAirport(this.getId());
    }

    @Override
    public void apply(Airport object) {
        setShortName(object.shortName);
        setFullName(object.fullName);
    }

    @Override
    public void applyInfo(Object info) {
        AirportInfo object = (AirportInfo) info;
        setShortName(object.shortName);
        setFullName(object.fullName);
    }

    @Override
    public String toString() {
        return String.format("Airport(id=%d, shortName=\"%s\", fullName=\"%s\")", getId(), getShortName(), getFullName());
    }
}
