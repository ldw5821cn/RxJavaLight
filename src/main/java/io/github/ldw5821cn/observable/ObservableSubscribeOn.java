package io.github.ldw5821cn.observable;

import io.github.ldw5821cn.observer.Observer;
import io.github.ldw5821cn.scheduler.Scheduler;

public class ObservableSubscribeOn<T> extends Observable<T> {
    private Scheduler scheduler;
    private ObservableSource<T> source;

    ObservableSubscribeOn(ObservableSource<T> source,Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;

    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        SubscribeOnObserver<T> subscribe = new SubscribeOnObserver<>(observer);
        observer.onSubscribe();
        scheduler.scheduleDirect(new SubscribeTask(subscribe));
    }


    private static final class SubscribeOnObserver<T> implements  Observer<T>{
        private final Observer<T> actual;

        public SubscribeOnObserver(Observer<T> actual) {
            this.actual = actual;
        }

        @Override
        public void onNext(T t) {
            actual.onNext(t);
        }

        @Override
        public void onError(Throwable e) {
            actual.onError(e);
        }

        @Override
        public void onSubscribe() {
            actual.onSubscribe();
        }

        @Override
        public void onComplete() {
            actual.onComplete();
        }
    }
    private final class SubscribeTask implements Runnable {
        private final SubscribeOnObserver<T> parent;

        SubscribeTask(SubscribeOnObserver<T> parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            source.subscribe(parent);
            scheduler.stopIO();
        }
    }
}
