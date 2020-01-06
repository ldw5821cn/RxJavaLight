package io.github.ldw5821cn.observable;

import io.github.ldw5821cn.observer.BasicFuseableObserver;
import io.github.ldw5821cn.observer.Observer;
import io.github.ldw5821cn.function.Function;

public class ObservableMap<T,U> extends AbstractObservableWithUpstream<T,U> {
    final Function<T, U> function;

    ObservableMap(ObservableSource<T> source, Function<T,U> function) {
        super(source);
        this.function = function;
    }
    @Override
    public void subscribeActual(Observer<U> observer) {
        source.subscribe(new MapObserver<>(observer, function));
    }


    static final class MapObserver<T, U> extends BasicFuseableObserver<T, U> {
        final Function<T, U> mapper;
        MapObserver(Observer<U> actual, Function<T, U> mapper){
            super(actual);
            this.mapper = mapper;
        }
        @Override
        public void onNext(T t) {
            U v = mapper.apply(t);
            actual.onNext(v);
        }
    }

}
