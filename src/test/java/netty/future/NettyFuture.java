package netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Callable;

/**
 * ClassName: NettyFuture
 * PackageName:netty.future
 * Description:
 * date: 2022/4/14 12:23
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class NettyFuture {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoop next = group.next();
        Future<Integer> submit = next.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("此时的线程是:"+Thread.currentThread().getName());
                Thread.sleep(2000);
                return 20;
            }
        });
        submit.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                System.out.println("监听到了，线程是"+Thread.currentThread().getName());
                Object now = future.getNow();
                System.out.println(now);
            }
        });
        group.shutdownGracefully();
    }
}
