package org.example.multiThreadNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: Boss
 * PackageName:org.example.multiThreadNIO
 * Description:
 * date: 2022/4/13 11:12
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class Boss {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT,null);
        ssc.bind(new InetSocketAddress(8080));
        //创建固定数量的worker并初始化
        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-"+i);
        }
        AtomicInteger index = new AtomicInteger();
        while (true){
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    System.out.println("connected..."+sc.getRemoteAddress());
                    sc.configureBlocking(false);
                    //关联到worker的selector，类似于任务的分发
                    System.out.println("before..."+sc.getRemoteAddress());
                    workers[index.getAndIncrement() % workers.length].register(sc);
                    System.out.println("after.."+sc.getRemoteAddress());
                }
            }
        }
    }
    static class Worker implements Runnable {
        private Thread thread = new Thread(this);
        private Selector selector;
        private String name;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        private boolean start = false;

        public Worker(String name) {
            this.name = name;
        }

        //初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            if(start == false){
                selector = Selector.open();
                thread.start();
                start = true;
            }
            //线程间通信的方式
            queue.add(()->{
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            //唤醒boss线程的select(),使其不要阻塞，接着读取queue中的任务
            selector.wakeup();
        }


        private  void printBuffer(ByteBuffer buffer){
            String s = new String(buffer.array());
            System.out.println("接收到的信息为:"+s);
        }

        @Override
        public void run() {
            while (true){
                try {
                    System.out.println("qpphust");
                    selector.select();
                    Runnable poll = queue.poll();
                    if(poll != null){
                        poll.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = ((SocketChannel) key.channel());
                            System.out.println("read...."+sc.getRemoteAddress());
                            sc.read(buffer);
                            buffer.flip();
                            printBuffer(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
