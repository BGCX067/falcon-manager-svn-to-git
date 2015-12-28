package ru.ifmo.falconmanager.database;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 23:26:13
 */
public abstract class AbstractObjectStore<I extends ObjectInfo<?>> {
    protected Set<ObjectStoreListener<I>> listeners;

    protected AbstractObjectStore() {
        listeners = new CopyOnWriteArraySet<ObjectStoreListener<I>>();
    }

    public void fireUpdatedEvent() {
        Collection<I> objects = getObjects();
        for (ObjectStoreListener<I> listener : listeners) {
            listener.updated(objects);
        }   
    }

    public void addObjectStoreListener(ObjectStoreListener<I> listener) {
        listeners.add(listener);
    }

    public void removeObjectStoreListener(ObjectStoreListener<I> listener) {
        listeners.remove(listener);
    }

    public abstract Collection<I> getObjects();

    public abstract int size();

    public abstract I getObjectAt(int rowIndex);

    public abstract int getIndexOf(I selectedObject);
}
