package ru.ifmo.falconmanager.database;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 14:50:24
 */
public interface ObjectInfo<O> extends Comparable<ObjectInfo<O>> {
    public int getId();

    public void addToDatabase();

    public void updateInDatabase();

    public void removeFromDatabase();

    public void apply(O object);

    public void applyInfo(Object info);
}
