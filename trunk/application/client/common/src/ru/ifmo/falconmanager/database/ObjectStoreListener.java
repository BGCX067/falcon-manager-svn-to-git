package ru.ifmo.falconmanager.database;

import java.util.Collection;

/**
 * @author Dmitry Paraschenko
 * @version 19.05.2008 14:33:41
 */
public interface ObjectStoreListener<I> {
    public void updated(Collection<I> set);
}
