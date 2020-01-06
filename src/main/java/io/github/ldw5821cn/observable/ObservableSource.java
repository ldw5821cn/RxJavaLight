package io.github.ldw5821cn.observable;

import io.github.ldw5821cn.observer.Observer;

public interface ObservableSource<T> {
    void subscribe(Observer<T> observer);
}
