package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Airplane;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 14:55:16
 */
public class AirplaneStore extends ObjectStore<AirplaneInfo> {
    protected AirplaneInfo load(int id) {
        return DatabaseSnapshot.getInstance().loadAirplane(id);
    }

    public synchronized void applyAll(Airplane[] objects) {
        clear(false);
        for (Airplane object : objects) {
            add(new AirplaneInfo(object), false);
        }
        fireUpdatedEvent();
    }
}