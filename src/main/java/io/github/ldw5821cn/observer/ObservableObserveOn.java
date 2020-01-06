package io.github.ldw5821cn.observer;

import io.github.ldw5821cn.observable.Observable;
import io.github.ldw5821cn.observable.ObservableSource;
import io.github.ldw5821cn.scheduler.Scheduler;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ObservableObserveOn<T> extends Observable<T> {
    private Scheduler scheduler;
    private ObservableSource<T> source;
    public ObservableObserveOn(ObservableSource<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        ObserverOnObserver observeOn = new ObserverOnObserver(observer);
        source.subscribe(observeOn);
        scheduler.scheduleDirect(observeOn);
    }

   private final class ObserverOnObserver implements Observer<T>, Runnable {
        private final Observer<T> actual;
        private LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>(10);
        private Throwable e;
        private volatile boolean done = false;
        private AtomicBoolean isSubscribed = new AtomicBoolean(false);
        public ObserverOnObserver(Observer<T> actual) {
            this.actual = actual;
        }

        @Override
        public void onNext(T t) {
            if(done)return;
            queue.offer(t);
            schedule();
        }

        @Override
        public void onComplete() {
            if(done)return;
            done = true;
            schedule();
            scheduler.stopMain();
        }

        @Override
        public void onError(Throwable t) {
            if(done)return;
            done = true;
            this.e = t;
            schedule();
        }

        @Override
        public void onSubscribe() {
            if(!isSubscribed.compareAndSet(false,true)){
                return;
            }
            actual.onSubscribe();
        }

        void schedule() {
            scheduler.scheduleDirect(this);

        }

        @Override
        public void run() {
           if(!queue.isEmpty()) {
                T t = queue.poll();
                actual.onNext(t);
            }else {
               if(done) {
                   if (e != null) {
                       actual.onError(e);
                   } else {

                       actual.onComplete();
                   }
               }
           }
        }
    }
}
