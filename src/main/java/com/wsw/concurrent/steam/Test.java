package com.wsw.concurrent.steam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author: wangsongwen
 * @Date: 2021/9/30 9:35
 * @Description: TODO
 */
public class Test {
    public static void main(String[] args) {
        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);

        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        Stream.of("1", 2, 3, 4)
                .limit(2)
                .skip(1)
                .forEach(System.out::println);

        List<String> list = new ArrayList<>();
        list.add("王松文");
        list.add("王松");
        list.add("文");
        list.stream()
                .filter(name -> name.startsWith("王"))
                .filter(name -> name.length() == 3)
                .forEach(System.out::println);
    }
}
