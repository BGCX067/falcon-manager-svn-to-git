package ru.ifmo.falconmanager.database;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 14:45:33
 */
public abstract class ObjectStore<I extends ObjectInfo<?>> extends AbstractObjectStore<I> {
    private volatile int size;
    private SortedSet<I> set;

    protected ObjectStore() {
        size = 0;
        set = new ConcurrentSkipListSet<I>();
    }

    public void clear() {
        clear(true);
    }

    protected void clear(boolean fire) {
        size = 0;
        set.clear();
        if (fire) {
            fireUpdatedEvent();
        }
    }

    public void remove(int id) {
        remove(id, true);
    }
    
    protected void remove(int id, boolean fire) {
        for (I object : set) {
            if (object.getId() == id) {
                if (set.remove(object)) {
                    size--;
                    fireUpdatedEvent();
                    return;
                }
            }
        }
    }

    public void add(I object) {
        add(object, true);
    }

    protected void add(I object, boolean fire) {
        if (set.add(object)) {
            size++;
        } else {
            I info = get(object.getId());
            info.applyInfo(object);
        }
        if (fire) {
            fireUpdatedEvent();
        }
    }

    public void addAll(I[] objects) {
        for (I object : objects) {
            add(object, false);
        }
        fireUpdatedEvent();
    }

    protected abstract I load(int id);

    public I get(int id) {
        for (I object : set) {
            if (object.getId() == id) {
                return object;
            }
        }
        I info = load(id);
        if (info != null) {
            add(info);
        }
        return info;
    }

    public I getObjectAt(int index) {
        int num = 0;
        for (I object : set) {
            if (num == index) {
                return object;
            }
            num++;
        }
        return null;
    }

    public int getIndexOf(I info) {
        if (info == null) {
            return 0;
        }
        int num = 0;
        for (I object : set) {
            if (object.getId() == info.getId()) {
                return num;
            }
            num++;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public Collection<I> getObjects() {
        return Collections.unmodifiableSortedSet(set);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (I info : getObjects()) {
            sb.append(info).append('\n');
        }
        return sb.toString();
    }
}
