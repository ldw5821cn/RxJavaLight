package io.github.ldw5821cn.observer;


public abstract class BasicFuseableObserver<T,U> implements Observer<T> {
    public final Observer<U> actual;
    public BasicFuseableObserver(Observer<U> actual) {
        this.actual = actual;
    }
    @Override
    public void onSubscribe() {
        actual.onSubscribe();
    }

    @Override
    public void onError(Throwable e) {
        actual.onError(e);
    }

    @Override
    public void onComplete() {
        actual.onComplete();
    }
}
