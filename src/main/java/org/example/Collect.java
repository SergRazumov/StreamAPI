package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collect {
    public static void main(String[] args) {
        //Создаем массив, 1 параметр в какой список добавлять, 2 параметр как добавлять, 3 параметр что делать со списками, тут  добавляем в список другой список
        List<Integer> list= Arrays.stream(new int[]{5,4,3,2,1,12,3,4,5,6}).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
        System.out.println(list);

        //Создаем Map, 1 параметр в какую структуру добавлять, 2 параметр как добавлять, map структура, el элемент, 3 параметр что делать со map
        Map<Integer,String> map= Arrays.stream(new int[]{5,4,3,2,1,12,3,4,5,6}).collect(HashMap::new,(mapp,el)->mapp.put(el,String.valueOf(el).concat("value")),HashMap::putAll);
        System.out.println(map);

        //Генерация значений от 5 до 10 включит, поместить в ArrayList
        List<Integer> list2=IntStream.range(5,11).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
        System.out.println(list2);

        //1 параметр ключ, 2 параметр значение, 3й параметр функция некого действия с 2мя одинаковыми value, в данном контексте будет перезапись
        Stream.of(1,2,3,4,5).collect(Collectors.toMap(el->el, el->el+1));
        Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.toMap(el->el, el->el+1,(value1,value2)->value2));

        //(1, "asad")  (1, "a")  el1.concat(el2) а так бы склеили
        Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.toMap(el->el, el->el+1,(value1,value2)->value2,TreeMap::new));

        //Создаем строку из значений
        System.out.println(Stream.of("5,4,3,2,1,12,3,4,5,6","dfdf","Dyha").collect(Collectors.joining()));
        System.out.println(Stream.of("5,4,3,2,1,12,3,4,5,6","dfdf","Dyha").collect(Collectors.joining("AAA","OngBack","Molodec")));

        //Считаем среднее и возвращаем его
        System.out.println(Stream.of(5.3,4.3,2.5,12.6).collect(Collectors.averagingDouble(el->el)));

        //Считаем сумму значений
        System.out.println(Stream.of(5.3,4.3,2.5,12.6).collect(Collectors.summingDouble(el->el)));

        //Специальный объект DoubleSummaryStatistics
        DoubleSummaryStatistics doubleSummaryStatistics=Stream.of(5.3,4.3,2.5,12.6).collect(Collectors.summarizingDouble(el->el));
        System.out.println(doubleSummaryStatistics);

        // Collections::unmodifiableList лямбда или ссылка на метод должны вернуть лист после модификаций (сорт не возвращает поэтому он не подходит)
        Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.collectingAndThen(Collectors.toList(),Collections::unmodifiableList));

        // Выдаст map у которой всего 2 ключа true и false и по придикату раскидает
        System.out.println(Stream.of(5,4,3,2,1,12,3,4,5,6).collect(Collectors.partitioningBy(el->el>9)));
    }
}
