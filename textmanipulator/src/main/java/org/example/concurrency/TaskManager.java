package org.example.concurrency;

public interface TaskManager<T> {

    T resolveTasks(T[][] input);
}
