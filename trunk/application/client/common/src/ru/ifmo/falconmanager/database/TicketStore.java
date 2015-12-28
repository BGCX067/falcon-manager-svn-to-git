package ru.ifmo.falconmanager.database;

import ru.ifmo.falconmanager.idl.Ticket;

/**
 * @author Dmitry Paraschenko
 * @version 18.05.2008 20:33:28
 */
public class TicketStore extends ObjectStore<TicketInfo> {
    protected TicketInfo load(int id) {
        return DatabaseSnapshot.getInstance().loadTicket(id);
    }

    public synchronized void applyAll(Ticket[] objects) {
        clear(false);
        for (Ticket object : objects) {
            add(new TicketInfo(object), false);
        }
        fireUpdatedEvent();
    }
}
