package ru.ifmo.falconmanager.database;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 23:24:16
 */
public abstract class FilteredObjectStore<I extends ObjectInfo<?>> extends AbstractObjectStore<I> implements ObjectStoreListener<I> {
    protected ObjectStore<I> store;
    protected List<I> objects;

    public FilteredObjectStore(ObjectStore<I> store) {
        objects = new CopyOnWriteArrayList<I>();
        this.store = store;
        store.addObjectStoreListener(this);
    }

    public Collection<I> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public int size() {
        return objects.size();
    }

    public I getObjectAt(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        return objects.get(index);
    }

    public int getIndexOf(I info) {
        if (info == null) {
            return -1;
        }
        int num = 0;
        for (I object : objects) {
            if (object.getId() == info.getId()) {
                return num;
            }
            num++;
        }
        return -1;
    }

    public void updated() {
        updated(store.getObjects());
    }

    public synchronized void updated(Collection<I> set) {
        objects.clear();
        for (I object : set) {
            if (isAccepted(object)) {
                objects.add(object);
            }
        }
        fireUpdatedEvent();
    }

    protected abstract boolean isAccepted(I object);
}
