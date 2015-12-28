package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Airplane;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 15:05:24
 */
public class AirplaneInfo extends AbstractObjectInfo<Airplane> {
    private String name;

    public AirplaneInfo(int id) {
        super(id);
    }

    public AirplaneInfo(int id, String name) {
        this(id);
        this.name = name;
    }

    public AirplaneInfo(String name) {
        this(0, name);
    }

    public AirplaneInfo(Airplane object) {
        this(object.id);
        apply(object);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public void addToDatabase() {
        DatabaseSnapshot.getInstance().addAirplane(this);
    }

    @Override
    public void updateInDatabase() {
        DatabaseSnapshot.getInstance().updateAirplane(this);
    }

    @Override
    public void removeFromDatabase() {
        DatabaseSnapshot.getInstance().deleteAirplane(this.getId());
    }

    @Override
    public void apply(Airplane object) {
        setName(object.name);
    }

    public void applyInfo(Object info) {
        AirplaneInfo object = (AirplaneInfo) info;
        setName(object.name);
    }

    @Override
    public String toString() {
        return String.format("Airplane(id=%d, name=\"%s\")", getId(), getName());
    }
}
