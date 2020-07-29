package com.liao;

import com.google.common.collect.Lists;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * java lambda stream 学习
 * 1.不需要参数，返回值为5
 * () -> 5
 * <p>
 * 2.接收一个参数（数字类型），返回其2倍的值
 * x -> 2 * x
 * <p>
 * 3. 接受2个参数（数字），并返回他们的差值
 * (x, y) -> x - y
 * <p>
 * 4.接收2个int型整数，返回他们的和
 * (int x, int y) -> x + y
 *
 * @author liaohongyang
 * @date 2020/7/29 17:30
 **/
public class LambdaAndStreamLearningTest {
    /**
     * 通过匿名内部类实现线程
     *
     * @author liaohongyang
     * @date 2020/7/29 15:44
     **/
    @Test
    public void anonymousInternalClassesThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类实现线程");
            }
        });
    }

    /**
     * 通过lambda实现线程
     *
     * @author liaohongyang
     * @date 2020/7/29 17:36
     **/
    @Test
    public void lambdaThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            System.out.println("lambda实现线程");
        });
    }

    /**
     * lambda foreach
     *
     * @author liaohongyang
     * @date 2020/7/29 17:48
     **/
    @Test
    public void foreach() {
        List<Person> personList = getPersonList();
        personList.forEach(person -> {
            System.out.println(person.toString());
        });

        Consumer<Person> changeAge = e -> e.setAge(e.getAge() + 3);
        personList.forEach(changeAge);
        personList.forEach(System.out::println);
    }

    /**
     * stream filter
     *
     * @author liaohongyang
     * @date 2020/7/29 19:59
     **/
    @Test
    public void filter() {
        // 普通filter
        List<Person> personList = getPersonList();
        personList.stream().filter(person -> person.getAge() > 1).forEach(System.out::println);
        System.out.println("------------------");
        // 组合filter 链式调用
        personList.stream().filter(person -> person.getAge() > 1).filter(person -> Objects.equals(person.getSex(), "女")).forEach(System.out::println);

        System.out.println("------------------");
        // 组合filter 逻辑
        Predicate<Person> ageFilter = person -> person.getAge() > 1;
        Predicate<Person> sexFilter = person -> Objects.equals(person.getSex(), "女");
        personList.stream().filter(ageFilter.and(sexFilter)).forEach(System.out::println);
    }

    /**
     * stream limit
     *
     * @author liaohongyang
     * @date 2020/7/29 20:22
     **/
    @Test
    public void limit() {
        List<Person> personList = getPersonList();
        personList.stream().limit(2).forEach(System.out::println);
    }

    /**
     * stream sorted
     *
     * @author liaohongyang
     * @date 2020/7/29 20:32
     **/
    @Test
    public void sorted() {
        List<Person> personList = getPersonList();
        personList.stream().sorted((p1, p2) -> p2.getAge() - p1.getAge()).forEach(System.out::println);
    }

    /**
     * stream max
     *
     * @author liaohongyang
     * @date 2020/7/29 20:35
     **/
    @Test
    public void max() {
        List<Person> personList = getPersonList();
        Person person = personList.stream().max(Comparator.comparing(Person::getAge)).get();
        System.out.println(person);
    }

    /**
     * stream min
     *
     * @author liaohongyang
     * @date 2020/7/29 20:38
     **/
    @Test
    public void min() {
        List<Person> personList = getPersonList();
        Person person = personList.stream().min(Comparator.comparing(Person::getAge)).get();
        System.out.println(person);
    }

    /**
     * stream map
     *
     * @author liaohongyang
     * @date 2020/7/29 22:40
     **/
    @Test
    public void map() {
        List<Person> personList = getPersonList();
        personList.forEach(System.out::println);
        System.out.println("-----------");
        personList.stream().map(person -> person.setAge(person.getAge() + 1)).forEach(System.out::println);
    }

    /**
     * stream reduce
     *
     * @author liaohongyang
     * @date 2020/7/29 22:47
     **/
    @Test
    public void reduce() {
        List<Person> personList = getPersonList();
        Integer total = personList.stream().mapToInt(Person::getAge).reduce((sum, age) -> sum + age).getAsInt();
        System.out.println(total);
    }

    /**
     * stream collect
     *
     * @author liaohongyang
     * @date 2020/7/29 23:00
     **/
    @Test
    public void collect() {
        List<Person> personList = getPersonList();
        Map<String, Person> stringPersonMap = personList.stream().collect(Collectors.toMap(Person::getName, person -> person, (oldValue, newValue) -> newValue));
        stringPersonMap.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println("-----------------");
        List<String> stringList = personList.stream().map(Person::getName).collect(Collectors.toList());
        stringList.forEach(System.out::println);
    }

    /**
     * stream summaryStatistics
     *
     * @author liaohongyang
     * @date 2020/7/29 23:19
     **/
    @Test
    public void summaryStatistics() {
        List<Person> personList = getPersonList();
        IntSummaryStatistics intSummaryStatistics = personList.stream().mapToInt(Person::getAge).summaryStatistics();
        System.out.println("average:" + intSummaryStatistics.getAverage());
        System.out.println("count:" + intSummaryStatistics.getCount());
        System.out.println("max:" + intSummaryStatistics.getMax());
        System.out.println("min:" + intSummaryStatistics.getMin());
        System.out.println("sum:" + intSummaryStatistics.getSum());
    }

    private List<Person> getPersonList() {
        Person person1 = new Person().setAge(1).setName("liaohongyang").setSex("女");
        Person person2 = new Person().setAge(2).setName("liaohongyang").setSex("男");
        Person person3 = new Person().setAge(3).setName("liaohongyang").setSex("男");
        Person person4 = new Person().setAge(4).setName("liaohongyang").setSex("女");
        List<Person> personList = Lists.newArrayList();
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        return personList;
    }
}