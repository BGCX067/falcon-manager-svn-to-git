package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.AirplaneSeat;

/**
 * @author Dmitry Paraschenko
 * @version 18.05.2008 10:56:23
 */
public class AirplaneSeatStore extends ObjectStore<AirplaneSeatInfo> {
    protected AirplaneSeatInfo load(int id) {
        return DatabaseSnapshot.getInstance().loadAirplaneSeat(id);
    }

    public synchronized void applyAll(AirplaneSeat[] objects) {
        clear(false);
        for (AirplaneSeat object : objects) {
            add(new AirplaneSeatInfo(object), false);
        }
        fireUpdatedEvent();
    }
}
