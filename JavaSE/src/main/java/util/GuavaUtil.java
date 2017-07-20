package util;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class GuavaUtil {
    
    /**
     * 使用Splitter将字符串分隔并转换到List中
     */
    @Test
    public void listTest(){
        List<String> list = Splitter.on(",").splitToList("think,in,java");
        for(String item : list){
            System.out.println(item);
        }
    }
    
    @Test
    public void getMapTest(){
        //guava 创建 HashMap
        Map map = Maps.newHashMap();
    }
}
