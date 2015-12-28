package ru.ifmo.falconmanager.database;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 23:37:19
 */
public class FilteredAirplaneSeatStore extends FilteredObjectStore<AirplaneSeatInfo> {
    private int airplaneId;

    public FilteredAirplaneSeatStore(ObjectStore<AirplaneSeatInfo> store) {
        super(store);
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(int airplaneId) {
        if (this.airplaneId != airplaneId) {
            this.airplaneId = airplaneId;
            updated();
        }
    }

    protected boolean isAccepted(AirplaneSeatInfo object) {
        return object.getAirplaneId() == airplaneId;
    }
}
