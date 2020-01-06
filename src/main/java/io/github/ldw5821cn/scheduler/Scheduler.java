package io.github.ldw5821cn.scheduler;


import java.util.concurrent.*;

public class Scheduler {
    private int type;
    private ThreadFactory factoryIO = r -> new Thread(r,"io");
    private ExecutorService executor = Executors.newSingleThreadExecutor(factoryIO);
    private ThreadFactory factoryMain = r -> new Thread(r,"mainTest");
    private ExecutorService main = Executors.newSingleThreadExecutor(factoryMain);

    public Scheduler(int type){
        this.type = type;
    }

    public static Scheduler io() {
        return new Scheduler(1);
    }

    public static Scheduler mainTest() {
        return new Scheduler(2);
    }

    public void scheduleDirect(Runnable runnable) {
        switch (type) {
            case 1:executor.submit(runnable);
                break;
            case 2:main.submit(runnable);
                break;
        }
    }

    public void stopIO(){
        if(executor ==null)return;
        executor.shutdown();
    }
    public void stopMain(){
        if(main ==null)return;
        main.shutdown();
    }
}
