package io.github.ldw5821cn.emitter;

import io.github.ldw5821cn.observer.Observer;

/**
 * 发射器实现类，持有 观察者 observer 对象，向其发送消息
 * @param <T>
 */

public class EmitterCreate<T> implements Emitter<T> {
    Observer<? super T> observer;
    public EmitterCreate(Observer<? super T> observer){
        this.observer = observer;
    }

    @Override
    public void onNext(T T) {
        observer.onNext(T);
    }

    @Override
    public void onComplete() {
        observer.onComplete();
    }

    @Override
    public void onError(Throwable t) {
        observer.onError(t);
    }
}
