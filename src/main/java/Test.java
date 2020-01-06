import io.github.ldw5821cn.observer.Observer;
import io.github.ldw5821cn.emitter.Emitter;
import io.github.ldw5821cn.function.Function;
import io.github.ldw5821cn.observable.Observable;
import io.github.ldw5821cn.observable.ObservableOnSubscribe;
import io.github.ldw5821cn.scheduler.Scheduler;

public class Test {
    public static void main(String[] args) {
//        testOnError();
        testOnNext();
    }

    private static void testOnNext() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(Emitter<String> emitter) {
                emitter.onNext("100");
                emitter.onNext("1000");
                emitter.onNext("1000002");
                emitter.onComplete();
            }
        }).ObservableSubscribeOn(Scheduler.io())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String T) {
                        System.out.println("testOnNext apply : " + T + " Thread" + "[" + Thread.currentThread().getName() + "]");
                        return Integer.valueOf(T);
                    }
                })
                .observeOn(Scheduler.mainTest())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("testOnNext onNext: " + integer + " Thread" + "[" + Thread.currentThread().getName() + "]");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("testOnNext onComplete" + " Thread" + "[" + Thread.currentThread().getName() + "]");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("testOnNext onError: " + t.getMessage() + " Thread" + "[" + Thread.currentThread().getName() + "]");

                    }

                    @Override
                    public void onSubscribe() {
                        System.out.println("testOnNext onSubscribe " + " Thread" + "[" + Thread.currentThread().getName() + "]");
                    }
                });
    }

    private static void testOnError() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(Emitter<String> emitter) {
                emitter.onNext("100");
                emitter.onNext("1000");
                emitter.onNext("ssss");
                emitter.onComplete();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String T) {
                return Integer.valueOf(T);
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onNext(Integer integer) {
                System.out.println("testOnError onNext: " + integer + " Thread" + "[" + Thread.currentThread().getName() + "]");
            }

            @Override
            public void onComplete() {
                System.out.println("testOnError onComplete" + " Thread" + "[" + Thread.currentThread().getName() + "]");

            }

            @Override
            public void onError(Throwable t) {
                System.out.println("testOnError onError: " + t.getMessage() + " Thread" + "[" + Thread.currentThread().getName() + "]");
            }

            @Override
            public void onSubscribe() {
                System.out.println("testOnError onSubscribe " + " Thread" + "[" + Thread.currentThread().getName() + "]");
            }
        });
    }
}
