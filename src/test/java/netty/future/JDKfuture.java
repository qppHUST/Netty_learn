package netty.future;

import java.util.concurrent.*;

/**
 * ClassName: JDKfuture
 * PackageName:netty.future
 * Description:
 * date: 2022/4/14 12:13
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class JDKfuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> submit = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(2000);
                return 50;
            }
        });
        Integer integer = submit.get();
        System.out.println(integer);
    }
}
