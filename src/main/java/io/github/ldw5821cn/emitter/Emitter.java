package io.github.ldw5821cn.emitter;

/**
 * 发射器接口
 */
public interface Emitter<T> {
    void onNext(T T);
    void onComplete();
    void onError(Throwable t);
}
