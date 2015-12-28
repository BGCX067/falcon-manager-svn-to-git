package ru.ifmo.falconmanager.database;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 15:14:44
 */
public abstract class AbstractObjectInfo<O> implements ObjectInfo<O> {
    private final int id;

    public AbstractObjectInfo(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(ObjectInfo<O> that) {
        return this.getId() - that.getId();
    }
}
