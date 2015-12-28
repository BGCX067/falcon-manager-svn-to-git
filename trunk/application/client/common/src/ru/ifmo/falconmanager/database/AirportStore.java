package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Airport;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 14:55:16
 */
public class AirportStore extends ObjectStore<AirportInfo> {
    protected AirportInfo load(int id) {
        return DatabaseSnapshot.getInstance().loadAirport(id);
    }

    public synchronized void applyAll(Airport[] objects) {
        clear(false);
        for (Airport object : objects) {
            add(new AirportInfo(object), false);
        }
        fireUpdatedEvent();
    }
}
