package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collect {
    public static void main(String[] args) {
        //������� ������, 1 �������� � ����� ������ ���������, 2 �������� ��� ���������, 3 �������� ��� ������ �� ��������, ���  ��������� � ������ ������ ������
        List<Integer> list= Arrays.stream(new int[]{5,4,3,2,1,12,3,4,5,6}).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
        System.out.println(list);

        //������� Map, 1 �������� � ����� ��������� ���������, 2 �������� ��� ���������, map ���������, el �������, 3 �������� ��� ������ �� map
        Map<Integer,String> map= Arrays.stream(new int[]{5,4,3,2,1,12,3,4,5,6}).collect(HashMap::new,(mapp,el)->mapp.put(el,String.valueOf(el).concat("value")),HashMap::putAll);
        System.out.println(map);

        //��������� �������� �� 5 �� 10 �������, ��������� � ArrayList
        List<Integer> list2=IntStream.range(5,11).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
        System.out.println(list2);

        //1 �������� ����, 2 �������� ��������, 3� �������� ������� ������ �������� � 2�� ����������� value, � ������ ��������� ����� ����������
        Stream.of(1,2,3,4,5).collect(Collectors.toMap(el->el, el->el+1));
        Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.toMap(el->el, el->el+1,(value1,value2)->value2));

        //(1, "asad")  (1, "a")  el1.concat(el2) � ��� �� �������
        Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.toMap(el->el, el->el+1,(value1,value2)->value2,TreeMap::new));

        //������� ������ �� ��������
        System.out.println(Stream.of("5,4,3,2,1,12,3,4,5,6","dfdf","Dyha").collect(Collectors.joining()));
        System.out.println(Stream.of("5,4,3,2,1,12,3,4,5,6","dfdf","Dyha").collect(Collectors.joining("AAA","OngBack","Molodec")));

        //������� ������� � ���������� ���
        System.out.println(Stream.of(5.3,4.3,2.5,12.6).collect(Collectors.averagingDouble(el->el)));

        //������� ����� ��������
        System.out.println(Stream.of(5.3,4.3,2.5,12.6).collect(Collectors.summingDouble(el->el)));

        //����������� ������ DoubleSummaryStatistics
        DoubleSummaryStatistics doubleSummaryStatistics=Stream.of(5.3,4.3,2.5,12.6).collect(Collectors.summarizingDouble(el->el));
        System.out.println(doubleSummaryStatistics);

        // Collections::unmodifiableList ������ ��� ������ �� ����� ������ ������� ���� ����� ����������� (���� �� ���������� ������� �� �� ��������)
        Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.collectingAndThen(Collectors.toList(),Collections::unmodifiableList));

        // ������ map � ������� ����� 2 ����� true � false � �� ��������� ���������
        System.out.println(Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.partitioningBy(el->el>9)));
    }
}
