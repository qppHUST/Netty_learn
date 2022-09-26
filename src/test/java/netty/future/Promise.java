package netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.ExecutionException;

/**
 * ClassName: Promise
 * PackageName:netty.future
 * Description:
 * date: 2022/4/15 14:06
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class Promise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop next = new NioEventLoopGroup().next();

        DefaultPromise<Integer> pro = new DefaultPromise<>(next);

        new Thread(()->{
            System.out.println("线程"+Thread.currentThread().getName()+"启动了");
            try {
                int i = 1/0;
                Thread.sleep(1000);
                pro.setSuccess(80);
            } catch (Exception e) {
                pro.setFailure(e);
            }
        }).start();
        System.out.println(Thread.currentThread().getName()+"等待结果");
        System.out.println(pro.get());

    }
}
