package ru.ifmo.falconmanager.database.models;

import ru.ifmo.falconmanager.database.AbstractObjectStore;
import ru.ifmo.falconmanager.database.AirplaneInfo;
import ru.ifmo.falconmanager.database.ObjectStoreListener;

import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.SortedSet;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 14:05:26
 */
public class AirplaneTableModel extends AbstractTableModel {
    private final static String[] COLUMN_NAMES = new String[] {"Id", "Name"};

    private AbstractObjectStore<AirplaneInfo> store;

    public AirplaneTableModel(AbstractObjectStore<AirplaneInfo> store) {
        this.store = store;
        store.addObjectStoreListener(new ObjectStoreListener<AirplaneInfo>() {
            public void updated(Collection<AirplaneInfo> objects) {
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
        AirplaneInfo object = store.getObjectAt(rowIndex);
        if (object == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return object.getId();
            case 1:
                return object.getName();
            default:
                return null;
        }
    }

    public void updated(SortedSet<AirplaneInfo> airplaneInfos) {
        fireTableDataChanged();
    }
}