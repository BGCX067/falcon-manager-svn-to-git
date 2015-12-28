package ru.ifmo.falconmanager.database.models;

import ru.ifmo.falconmanager.database.AbstractObjectStore;
import ru.ifmo.falconmanager.database.FlightInfo;
import ru.ifmo.falconmanager.database.ObjectStoreListener;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.SortedSet;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 14:14:40
 */
public class FlightTableModel extends AbstractTableModel {
    private final static String[] COLUMN_NAMES = new String[] {"Id", "Dep. airport", "Dep. date", "Arr. airport", "Arr. date", "Airplane id"};
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private AbstractObjectStore<FlightInfo> store;

    public FlightTableModel(AbstractObjectStore<FlightInfo> store) {
        this.store = store;
        store.addObjectStoreListener(new ObjectStoreListener<FlightInfo>() {
            public void updated(Collection<FlightInfo> objects) {
                fireTableDataChanged();
            }
        });
    }

    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    public int getRowCount() {
        return store.size();
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        FlightInfo object = store.getObjectAt(rowIndex);
        if (object == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return object.getId();
            case 1:
                return object.getDepartureAirport().getShortName();
            case 2:
                synchronized (DATE_FORMAT) {
                    return DATE_FORMAT.format(object.getDepartureDate());
                }
            case 3:
                return object.getArrivalAirport().getShortName();
            case 4:
                synchronized (DATE_FORMAT) {
                    return DATE_FORMAT.format(object.getArrivalDate());
                }
            case 5:
                return object.getAirplaneId();
            default:
                return null;
        }
    }

    public void updated(SortedSet<FlightInfo> flightInfos) {
        fireTableDataChanged();
    }
}
