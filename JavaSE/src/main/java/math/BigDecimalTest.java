package math;

import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalTest {
    
    /**
     * 使用double构造器时不准确
     */
    @Test
    public void test1(){
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }
    
    /**
     * !!!!!使用String构造器 才能产生正确的结果
     */
    @Test
    public void test3(){
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
    }
    
}
