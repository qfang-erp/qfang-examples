package com.qfang.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author huxianyong
 * @date 2017/9/20
 * @since 1.0
 */
public class Test {
    public static void main(String[] args) {

        double maxValue=1000;
        int count=300;
        double currentMaxValue=0;

        List<Double> list=new ArrayList<>();
        Random random=new Random();
        for (;;) {
            double randomValue=nextValue(random.nextDouble());

            currentMaxValue+=randomValue;
            if(currentMaxValue >maxValue){
                break;
            }
            if(list.size()>=count){
                break;
            }
            list.add(randomValue);
        }

        list.forEach(System.out::println);
        System.out.println(" 总数："+list.size());
    }

    public static  double nextValue(double value){
        if(value<1){
            value=value*10;
        }

        if(value<1){
            value=nextValue(value);
        }
        return value;
    }
}
