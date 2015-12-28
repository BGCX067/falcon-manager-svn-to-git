package ru.ifmo.falconmanager.database.models;

import ru.ifmo.falconmanager.database.AbstractObjectStore;
import ru.ifmo.falconmanager.database.AirplaneSeatInfo;
import ru.ifmo.falconmanager.database.ObjectStoreListener;

import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.SortedSet;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 14:05:26
 */
public class AirplaneSeatTableModel extends AbstractTableModel {
    private final static String[] COLUMN_NAMES = new String[] {"Id", "Airplane id", "Seat"};

    private AbstractObjectStore<AirplaneSeatInfo> store;

    public AirplaneSeatTableModel(AbstractObjectStore<AirplaneSeatInfo> store) {
        this.store = store;
        store.addObjectStoreListener(new ObjectStoreListener<AirplaneSeatInfo>() {
            public void updated(Collection<AirplaneSeatInfo> objects) {
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
        AirplaneSeatInfo object = store.getObjectAt(rowIndex);
        if (object == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return object.getId();
            case 1:
                return object.getAirplaneId();
            case 2:
                return object.getSeat();
            default:
                return null;
        }
    }

    public void updated(SortedSet<AirplaneSeatInfo> airplaneSeatInfos) {
        fireTableDataChanged();
    }
}