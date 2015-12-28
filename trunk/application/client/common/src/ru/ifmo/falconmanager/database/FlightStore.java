package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Flight;

/**
 * @author Dmitry Paraschenko
 * @version 18.05.2008 14:48:22
 */
public class FlightStore extends ObjectStore<FlightInfo> {
    protected FlightInfo load(int id) {
        return DatabaseSnapshot.getInstance().loadFlight(id);
    }

    public synchronized void applyAll(Flight[] objects) {
        clear();
        for (Flight object : objects) {
            add(new FlightInfo(object), false);
        }
        fireUpdatedEvent();
    }
}
