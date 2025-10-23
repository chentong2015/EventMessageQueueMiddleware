import java.util.concurrent.*;

public class ScheduledThreadPoolTask {

    // TODO. 使用ScheduledThreadPool来执行定时的任务，基于timestamps时间戳
    public static void main(String[] args) {
        // 支持定时与周期性任务的线程池
        ExecutorService service = Executors.newScheduledThreadPool(10);
        for (int index = 0; index < 100; index++) {
            // service.execute(new MyTask());
        }

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorServiceWithFactory = Executors.newSingleThreadScheduledExecutor(new MyThreadFactory());
        executorService.scheduleAtFixedRate(new MyTimedThread(), 0, 5, TimeUnit.SECONDS);
    }

    static class MyTimedThread extends Thread {
        @Override
        public void run() {
            System.out.println("invoke task.");
        }
    }

    // TODO. 根据指定的Runnable来创建Thread线程
    static class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            // r – a runnable to be executed by new thread instance
            // return new Thread(r);

            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setName("Thread name");
            t.setDaemon(true);
            return t;
        }
    }
}
