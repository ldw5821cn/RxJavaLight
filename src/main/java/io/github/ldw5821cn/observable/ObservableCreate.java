package io.github.ldw5821cn.observable;

import io.github.ldw5821cn.emitter.EmitterCreate;
import io.github.ldw5821cn.observer.Observer;

/**
 * 适配器，通过extends抽象Observable，持有ObservableOnSubscribe，
 * 来完成两者的协同工作
 * * @param <T>
 */
public final class ObservableCreate<T> extends Observable<T> {
    ObservableOnSubscribe<T> source;
    public ObservableCreate(ObservableOnSubscribe<T> source){
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
       EmitterCreate<T> ec = new EmitterCreate<>(observer);
       observer.onSubscribe();
       try {
           source.subscribe(ec);
       }catch (Throwable t){
           ec.onError(t);
       }
    }
}
