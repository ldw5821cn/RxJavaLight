package io.github.ldw5821cn.observable;

import io.github.ldw5821cn.function.Function;
import io.github.ldw5821cn.observer.ObservableObserveOn;
import io.github.ldw5821cn.observer.Observer;
import io.github.ldw5821cn.scheduler.Scheduler;

/**
 * 被观察者
 */
public abstract class Observable<T> implements ObservableSource<T> {
    /**
     * map 根据调用者传入的function接口，对消息内容进行转换
     * @param mapper 转换器接口，用于将 T 转换为 R
     * @param <U> 转换后的类型
     * @return 转换后的类型 R
     */
    public <U> Observable<U> map(Function<T, U> mapper){
       return new ObservableMap<T,U>(this, mapper);
    }


    /**
     * 被观察者的线程切换
     * @param scheduler 模拟 RxJava的scheduler
     * @return Observable
     */
    public  Observable<T> ObservableSubscribeOn(Scheduler scheduler){
        return new ObservableSubscribeOn<>(this, scheduler);
    }

    /**
     * 观察者的线程切换
     * @param scheduler 模拟 RxJava的scheduler
     * @return Observable
     */
    public Observable<T> observeOn(Scheduler scheduler){
        return new ObservableObserveOn<>(this, scheduler);
    }

    /**
     * 创建被观察对象，返回 ObservableOnSubscribe 类型的对象
     * @param source ObservableOnSubscribe
     * @param <T> ObservableCreate
     * @return 返回 ObservableOnSubscribe
     */
    public static <T> Observable<T> create(ObservableOnSubscribe<T> source){
        return new ObservableCreate<>(source);
    }

    @Override
    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    /**
     * 订阅方法的真是实现 由子类实现
     * @param observer 观察者
     */
    protected abstract void subscribeActual(Observer<T> observer);
}
