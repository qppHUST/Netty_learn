package org.example;

import static org.junit.Assert.assertTrue;

import com.google.common.base.Optional;
import io.netty.channel.Channel;
import org.junit.Test;
import sun.misc.Unsafe;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {

        Integer invalidInput = null;
        Optional<Integer> a = Optional.of(invalidInput);
        Optional<Integer> b = Optional.of(new Integer(10));
        System.out.println(sum(a, b));
        Unsafe unsafe = Unsafe.getUnsafe();
    }

    public Integer sum (Optional < Integer > a, Optional < Integer > b){
        return a.get() + b.get();
    }
}
