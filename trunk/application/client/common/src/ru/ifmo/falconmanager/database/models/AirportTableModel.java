package ru.ifmo.falconmanager.database.models;

import ru.ifmo.falconmanager.database.AbstractObjectStore;
import ru.ifmo.falconmanager.database.AirportInfo;
import ru.ifmo.falconmanager.database.ObjectStoreListener;

import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.SortedSet;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 14:05:26
 */
public class AirportTableModel extends AbstractTableModel {
    private final static String[] COLUMN_NAMES = new String[] {"Id", "Short name", "Long name"};

    private AbstractObjectStore<AirportInfo> store;

    public AirportTableModel(AbstractObjectStore<AirportInfo> store) {
        this.store = store;
        store.addObjectStoreListener(new ObjectStoreListener<AirportInfo>() {
            public void updated(Collection<AirportInfo> objects) {
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
        AirportInfo object = store.getObjectAt(rowIndex);
        if (object == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return object.getId();
            case 1:
                return object.getShortName();
            case 2:
                return object.getFullName();
            default:
                return null;
        }
    }

    public void updated(SortedSet<AirportInfo> airportInfos) {
        fireTableDataChanged();
    }
}
