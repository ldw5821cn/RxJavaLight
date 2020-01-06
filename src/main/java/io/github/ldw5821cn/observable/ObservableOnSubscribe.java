package io.github.ldw5821cn.observable;

import io.github.ldw5821cn.emitter.Emitter;

public interface ObservableOnSubscribe<T> {
    void subscribe(Emitter<T> emitter);
}
