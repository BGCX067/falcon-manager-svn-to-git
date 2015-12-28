package ru.ifmo.falconmanager.database.models;

import ru.ifmo.falconmanager.database.AbstractObjectStore;
import ru.ifmo.falconmanager.database.ObjectStoreListener;
import ru.ifmo.falconmanager.database.TicketInfo;

import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.SortedSet;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 14:14:40
 */
public class TicketTableModel extends AbstractTableModel {
    private final static String[] COLUMN_NAMES = new String[] {"Id", "Flight id", "Seat", "Client name", "Client passport"};

    private AbstractObjectStore<TicketInfo> store;

    public TicketTableModel(AbstractObjectStore<TicketInfo> store) {
        this.store = store;
        store.addObjectStoreListener(new ObjectStoreListener<TicketInfo>() {
            public void updated(Collection<TicketInfo> objects) {
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
        TicketInfo object = store.getObjectAt(rowIndex);
        if (object == null) {
            return null;
        }
        switch (columnIndex) {
            case 0:
                return object.getId();
            case 1:
                return object.getFlightId();
            case 2:
                return object.getSeat().getSeat();
            case 3:
                return object.getClientName();
            case 4:
                return object.getClientPassport();
            default:
                return null;
        }
    }

    public void updated(SortedSet<TicketInfo> ticketInfos) {
        fireTableDataChanged();
    }
}