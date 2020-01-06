package io.github.ldw5821cn.observable;

/**
 * 数据转换器，可以包裹上游对象和下游对象
 * @param <T>
 * @param <U>
 */
public abstract class AbstractObservableWithUpstream<T,U> extends Observable<U> {
    public ObservableSource<T> source;
    public AbstractObservableWithUpstream(ObservableSource<T> source){
        this.source = source;
    }
}
