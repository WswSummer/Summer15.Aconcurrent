package com.wsw.concurrent.datastructure;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author WangSongWen
 * @Date: Created in 10:07 2021/3/9
 * @Description:
 * list: 可重复
 * set: 不重复
 */
public class DataStructure {
    public void mapTest() {
        HashMap<String, Object> hashMap = new HashMap<>(); // 无序 线程不安全
        TreeMap<String, Object> treeMap = new TreeMap<>(); // 排序
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(); // 有序 线程不安全
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>(); // 线程安全
    }

    public void listTest() {
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
    }

    public void setTest() {
        HashSet<String> hashSet = new HashSet<>(); // 哈希表
        TreeSet<String> treeSet = new TreeSet<>(); // 二叉树 排序
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(); // 有序
    }

}
