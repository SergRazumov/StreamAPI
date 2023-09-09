package org.example;


import org.junit.Assert;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Practice {
    public static void main(String[] args) {
        System.out.println(findShort("bitcoin take over the world maybe who knows perhaps"));
        printLetter("bitcoin");

        findUniq(new double[]{0, 1, 0});

        System.out.println(countingSheep(0));

        System.out.println(abbrevName("Sam Harris"));

        System.out.println(Arrays.toString(monkeyCount(5)));

        System.out.println(arrayPlusArray(new int[]{-1,-2,-3}, new int[]{-4,-5,-6}));

        System.out.println("longest Fixed Tests");
        Assert.assertEquals("aehrsty", longest("aretheyhere", "yestheyarehere"));
        Assert.assertEquals("abcdefghilnoprstu", longest("loopingisfunbutdangerous", "lessdangerousthancoding"));
        Assert.assertEquals("acefghilmnoprstuy", longest("inmanylanguages", "theresapairoffunctions"));
        Assert.assertEquals(23, new Practice().solution(10));

        System.out.println(new Practice().solution(20));
        System.out.println(new Practice().numberOfDivisors(1));
    }

    public long numberOfDivisors(int n) {

        return IntStream.range(1, n+1).filter(y-> n % y==0).count();

    }

    public int solution(int number) {
        return IntStream.range(1, number).filter(n-> n%3==0 || n%5==0).sum();
    }

    public static String longest (String s1, String s2) {
       return (s1 + s2).chars().distinct().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public static int arrayPlusArray(int[] arr1, int[] arr2) {
        return Stream.of(arr1, arr2).flatMapToInt(x-> Arrays.stream(x)).sum();
    }

    public static int[] monkeyCount(final int n){
        return IntStream.range(1, n+1).toArray();
    }

    public static String abbrevName(String name) {
        return Arrays.stream(name.split(" "))
                .map(String::toUpperCase)
                .map(word -> word.substring(0, 1))
                .collect(Collectors.joining("."));

    }


    public static String countingSheep(int num) {
        return IntStream.rangeClosed(1, num)
                .mapToObj(i -> i + " sheep...")
                .collect(Collectors.joining());

    }





    public static int findSmallestInt(int[] args) {
        return  Arrays.stream(args).min().orElse(0);
    }


    public static double findUniq(double arr[]) {
        double n = arr[0];
        for(int i=1; i< arr.length; i++) {
            if(arr[i] != n) {
                return arr[i];
            }
        }
        return 0;
    }

    public static int findShort(String s) {
        String test = "bitcoin";
        for(char t : test.toCharArray()) {
            System.out.println(t);
        }
       return Arrays.stream(s.split(" ")).min(Comparator.comparingInt(String::length)).orElse("").length();
    }


    public static int printLetter(String s) {
         Arrays.stream(s.split(" ")).flatMap(t-> Stream.of(t.split(""))).forEach(System.out::println);
         return 0;
    }
}
