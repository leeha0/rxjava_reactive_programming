package chapter2.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceTest {

    public static void main(String[] args) {
        // create a thread-pool with 10 threads using Executors factory class
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // directly create an executorService
        ExecutorService executorService1 = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

        // assigning tasks to the executorService
        Runnable runnableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println("runnableTask");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(300);
            return "callableTask";
        };

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);


        executorService.execute(runnableTask);
        Future<String> future = executorService.submit(callableTask);
        String result = null;
        try {
//            result = future.get();
            result = future.get(2000, TimeUnit.MILLISECONDS);
            System.out.println(result);

            boolean canceled = future.cancel(true);
            boolean isCancelled = future.isCancelled();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdownNow();
        executorService1.shutdownNow();
//        try {
//            String result = executorService.invokeAny(callableTasks);
//            System.out.println(result);
//            List<Future<String>> futures = executorService.invokeAll(callableTasks);
//            System.out.println(futures);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            executorService.shutdown();
//            executorService1.shutdownNow();
//
//            // recommended shutdown
//            executorService.shutdown();
//            try {
//                if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
//                    executorService.shutdownNow();
//                }
//            } catch (InterruptedException e) {
//                executorService.shutdownNow();
//            }
//        }
    }
}
