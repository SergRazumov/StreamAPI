package org.example;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.*;

class Stream {
    public static void main(String[] args) {


        //Инициализация stream
        Integer[] intArraysObj = {6, 4, 8, 11, 44, 2, 65, 3, 8, 7, 6, 49, 1, 1};
        List<Integer> massStream = Arrays.asList(intArraysObj);
        int[] intArrays = {6, 4, 8, 11, 44, 2, 65, 3, 8, 7, 6, 49, 1, 1};
        char[] chars = {'a', 'b', 'c'};

        Map<Integer, Student> mapWithStudent = new HashMap<>();
        mapWithStudent.put(1, new Student("Ivan", 19, 2));
        mapWithStudent.put(2, new Student("Oleg", 18, 1));
        mapWithStudent.put(3, new Student("Vasya", 20, 3));
        mapWithStudent.put(4, new Student("Anton", 21, 3));
        java.util.stream.Stream<Map.Entry<Integer, Student>> mapWithStudentStream = mapWithStudent.entrySet().stream();


        System.out.println(Arrays.stream(intArraysObj)
                .count()); //=> 14шт
        System.out.println(java.util.stream.Stream.of(intArraysObj).count()); //=> 14шт

        System.out.println(java.util.stream.Stream.of(massStream)
                .count()); //=> 1шт
        System.out.println(massStream.stream().count()); //=> 14шт

        System.out.println(Arrays.stream(intArrays)
                .count()); //=> 14шт
        System.out.println(java.util.stream.Stream.of(intArrays).count()); //=> 1шт

        System.out.println(java.util.stream.Stream.of(chars).count()); //=> 1шт

        //Возвращает поток элементами которого являются упорядоченные значения, недетерминированный
        System.out.print("\n===================OF==================\n");
        java.util.stream.Stream<List<Integer>> streamList = java.util.stream.Stream.of(massStream);
        java.util.stream.Stream<String> streamString = java.util.stream.Stream.of("1", "2", "3");

        java.util.stream.Stream.of(Arrays.asList(intArraysObj), 6).forEach(System.out::println);

        char[] c = {'a', 'b', 'c'};
        System.out.print(java.util.stream.Stream.of(c).count());
        System.out.print(java.util.stream.Stream.of(streamString).count());
        System.out.print(java.util.stream.Stream.of(streamList).count());



        //Возвращает поток, где каждый элемент генерируется поставленным поставщиком
        System.out.print("\n===================Generate==================\n");
        java.util.stream.Stream<Double> doubleGenerateStream = java.util.stream.Stream.generate(Math::random);
        java.util.stream.Stream<Integer> intGenerateStream = java.util.stream.Stream.generate(() -> new Random().nextInt(100));
        Supplier<Double> supplier = new Supplier<Double>() {
            @Override
            public Double get() {
                return Math.random();
            }
        };
        java.util.stream.Stream<Double> doubleGenerateAnonStream = java.util.stream.Stream.generate(supplier);
        Supplier<ArrayList<String>> supplierSt = new Supplier<ArrayList<String>>() {
            @Override
            public ArrayList<String> get() {
                return new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four"));
            }
        };
        java.util.stream.Stream<ArrayList<String>> arrayListGenerateStream = java.util.stream.Stream.generate(supplierSt);

        //Возвращает поток, где каждый элемент генерируется по условию 3 параметра, а второй параметр ограничивает количество генерируемых
        System.out.print("\n==============================Stream iterate=====================================================\n");
        Faculty faculty = new Faculty();
        java.util.stream.Stream.iterate(new Student("Andrey", 27, 0), stud -> stud.getAge() < 65, stud -> stud.newAgeStudent(stud.getAge() + 1)).forEach(faculty.getStudents()::add);

        //Возвращает класс строитель, где каждый элемент отдельный поток, метод accept не терминальный
        System.out.print("\n==============================Stream builder=====================================================\n");
        java.util.stream.Stream.Builder<Object> builder = java.util.stream.Stream.builder().add("1").add("2").add("3").add(new Student("Andre", 27, 0));
        builder.add("4").accept("5");
        System.out.println(builder.build().limit(100).peek(x -> System.out.println(x + " ")).count());

        //Склеивает два стрима, каждый элемент отдельный поток, метод не терминальный но стримы которые он склеивает он закрывает
        System.out.print("\n==============================Stream concat=====================================================\n");
        java.util.stream.Stream<Integer> x = java.util.stream.Stream.of(1, 2, 3, 3);
        java.util.stream.Stream<Integer> y = java.util.stream.Stream.of(4, 5, 6);
        System.out.println("\nSum stream " + java.util.stream.Stream.concat(x, y).distinct().peek(o -> System.out.print(" " + o)).count());
        System.out.println("Sum stream " + (java.util.stream.Stream.of(1, 2, 3).count() + java.util.stream.Stream.of(4, 5, 6).count()));

        System.out.print("\n==============================Stream ofNullable=====================================================\n");
        java.util.stream.Stream.ofNullable(null).forEach(System.out::println);

        //True если хоть 1 подходит anyMatch терминальный
        System.out.print("\n==============================Stream anyMatch=====================================================\n");
        System.out.println(massStream.stream().anyMatch((el) -> el < 10 && el > 0)); //true

        //Количество потоков метод терминальный
        System.out.print("\n==============================Stream count=====================================================\n");
        System.out.println(massStream.stream().count());

        //Оставить только уникальные, peek не способен менять коллекцию, distinct терминальный
        System.out.print("\n==============================Stream distinct=====================================================\n");
        System.out.println(massStream.stream().distinct().sorted().peek(el -> {
            el = el + 1;
        }).peek(p -> System.out.print(p + " ")).count());

        //Фильтрация убирает значения не соответствующие условию фильрации
        System.out.print("\n==============================Stream filter=====================================================\n");
        System.out.println(massStream.stream().peek(t -> System.out.print(" " + t + " ")).filter(num -> num / 10 == 0).peek(t -> System.out.print("afterFilter " + t)).count());

        //Чтобы корректно работало коллекция должна быть сортированная, удалит все пока истино условие, как только ложь идем дальше, dropWhile не терминальный
        System.out.print("\n==============================Stream dropWhile=====================================================\n");
        System.out.println(massStream.stream().peek(t -> System.out.print(t + " ")).sorted().peek(u -> System.out.print("AfterSort " + u + " ")).dropWhile(el -> el < 10).collect(Collectors.toList()));

        //Оставляет первые 5 элементов, forEachOrdered выводит строго последовательно элементы и ему все равно на параллельность, limit терминальный
        System.out.println("\n====================================Stream limit===========================================\n");
        java.util.stream.Stream.of("1", "2", "3", "4", "5", "6").parallel().limit(5).forEachOrdered(System.out::print);

        //Берет максимальное и минимальное значение, метод терминальный
        System.out.println("\n======================================Stream max=============================================\n");
        System.out.println(java.util.stream.Stream.of("1", "2", "3", "4", "5", "6")
                .max((el1, el2) -> Integer.valueOf(el1) - Integer.valueOf(el2)));
        System.out.println("\n=======================================Stream min=========================================\n");
        System.out.println(java.util.stream.Stream.of("1", "2", "3", "4", "5", "6")
                .min(Comparator.comparingInt(Integer::valueOf)));

        //Возвращает true, если все удовлетворяют условию
        System.out.println("\n=======================================Stream allMatch========================================\n");
        System.out.println(java.util.stream.Stream.of(1, 2, 3, 4, 5, 6).allMatch(el -> el > 5));

        //Возвращает true, если ни один из элементов в потоке не удовлетворяет условию
        System.out.println("\n=======================================Stream noneMatch========================================\n");
        System.out.println(java.util.stream.Stream.of(1, 2, 3, 4, 5, 6).noneMatch(el -> el > 5));

        //Отбрасывает первые два элемента, метод терминальный
        System.out.println("\n=======================================Stream skip===============================================\n");
        java.util.stream.Stream.of(1, 2, 3, 4, 5, 6).skip(2).forEach(System.out::print);

        //Сортирует коллекцию, метод не терминальный
        System.out.println("\n=========================================Stream sorted==============================================\n");
        java.util.stream.Stream.of(2, 3, 1, 6, 5, 4).sorted((el1, el2) -> el1 - el2).forEach(System.out::print);

        //Чтобы корректно работало коллекция должна быть сортированная, выполняется пока истино условие, как только ложь удаляет остаток, takeWhile не терминальный
        System.out.println("\n====================================Stream takeWhile==========================\n");
        java.util.stream.Stream.of(2, 3, 1, 6, 5, 4).sorted((el1, el2) -> el2 - el1).takeWhile(el -> el > 3).forEach(System.out::print);

        //Формирует массив object, метод терминальный
        System.out.println("\n=====================================Stream toArray=============================================\n");
        Object[] objects = java.util.stream.Stream.of(2, 3, 1, 6, 5, 4).toArray();
        for (Object a : objects) {
            System.out.print(a);
        }

        //Формирует массив по заказу, метод терминальный
        System.out.println("\n=================================Stream toArray(generator)============================================\n");
        System.out.println(java.util.stream.Stream.of(2, 3, 1, 6, 5, 4).toArray(new IntFunction<Integer[]>() {
            @Override
            public Integer[] apply(int value) {
                return new Integer[value];
            }
        }));
        Integer[] integersToArray = java.util.stream.Stream.of(2, 3, 1, 6, 5, 4).toArray(Integer[]::new);
        System.out.print(integersToArray);
        integersToArray = java.util.stream.Stream.of(0, 3, 1, 10, 5, 4).toArray(value -> new Integer[value]);
        System.out.println(integersToArray);

        //Выводит на выход такое же количество потоков как и поступило на вход, но при этом можем изменить структуру внутри каждого элемента
        System.out.println("\n=================================Stream map============================================\n");
        String mapString = "Hello world !";
        List<char[]> listChar = java.util.stream.Stream.of(mapString.split(" "))
                //String.toCharArray -> char[]
                //Stream<String> -> Stream<char[]>
                //String.chars -> IntStream не бывает стримов из символов поэтому возвращается поток из Integer
                //Stream<String> -> Stream<Stream<Integer>>
                .map(String::toCharArray)
                .limit(3)
                .peek(q -> System.out.print(" " + Arrays.toString(q)))
                .collect(Collectors.toList());
        System.out.println(" listChar: " + listChar.size());
        java.util.stream.Stream<String> listChar2 = java.util.stream.Stream.of(mapString.split(" "));
        listChar2
                .map(s -> java.util.stream.Stream.iterate(new Student("Andrey", 27, 0), stud -> stud.getAge() < 3, stud -> stud.newAgeStudent(stud.getAge() + 1)))
                .forEach(stud -> stud.forEach(Student::getAge));

        //Внутрь метода передаем любой объект, но результатом работы с ним должен быть int, который в свою очередь методом mapToInt будет преобразован IntStream оно же Stream<Integer>
        System.out.println("\n=================================Stream mapToInt============================================\n");
        //Найти сумму UniCode каждого слова
        Integer[] sumUniCodeAllWords = java.util.stream.Stream.of(mapString.split(" ")).mapToInt(v -> v.chars().sum()).boxed().toArray(Integer[]::new);
        System.out.println(sumUniCodeAllWords.length);

        //Найти среднее значение
        System.out.println("\n=================================Stream mapToDouble============================================\n");
        System.out.println(java.util.stream.Stream.of("1", "2", "3", "4", "5", "6", "7").mapToDouble(Integer::valueOf).average().getAsDouble());


        //Объединение элементов, метод терминальный
        System.out.println("\n=================================Stream reduce============================================\n");
//        String stringReduce = Stream.of(new char[]{'h', 'e', 'l', 'l', 'o'}, new char[]{'W', 'o', 'r', 'l', 'd'})
//                .map(sh -> {
//                    StringBuilder sb = new StringBuilder();
//                    Stream.of(sh).forEach(sb::append);
//                    return sb.toString();
//                }).reduce("Sergey", String::concat);
        java.util.stream.Stream<String> test = java.util.stream.Stream.of(new char[]{'h', 'e', 'l', 'l', 'o'}, new char[]{'W', 'o', 'r', 'l', 'd'})
                .map(sh -> {
                    StringBuilder sb = new StringBuilder();
                    java.util.stream.Stream.of(sh).forEach(sb::append);
                    return sb.toString();
                });
        String stringReduce = test.reduce("Sergey", String::concat);
        System.out.println(stringReduce);

        //Почему суммирует какие либо значения, если он работает по Math:max
        int stringReduce2 = java.util.stream.Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 8)
                .parallel()
                .reduce(0, (elem1, elem2) -> elem1 + elem2, Math::max);
        System.out.println(stringReduce2);

        //внутри flatMap создаем stream из полученного элемента, мы должны внутрь подать ему Stream механизм создания, flatMap выдавать будет элементы этого stream
        System.out.println("\n=================================Stream flatMap============================================\n");
        Integer[] flatMap = Arrays.stream(new Integer[]{1, 8, 4, 15, 20, 3})
                .flatMap(el -> {
                    if (el > 1) return java.util.stream.Stream.empty();
                    return java.util.stream.Stream.iterate(el, el1 -> el1 < 21, el1 -> el1 + 1);
                }).toArray(Integer[]::new);
        System.out.println(Arrays.toString(flatMap));

        Integer[] flatMap2 = java.util.stream.Stream.of(new Integer[]{1, 8, 4, 3}, new Integer[] {10,20,30})
                .flatMap(elem -> java.util.stream.Stream.of(elem))
                .toArray(Integer[]::new);

        //
        System.out.println("\n=================================Stream flatMapToInt============================================\n");
        Integer[] flatMapToInt = java.util.stream.Stream.of(1,2,3,4,5)
                .flatMapToInt(n->IntStream.of(n, n*10))
                .boxed()
                .toArray(Integer[]::new);

        String homeString = "Hello World !";
        Integer[] uniCodeInteger = Arrays.stream(homeString.split(" "))
                .flatMapToInt(st->st.chars())
                .boxed()
                .toArray(Integer[]::new);
        for (Integer n : uniCodeInteger) {
            System.out.print(n + " ");
        }
        System.out.println("\n=================================Stream flatMapToDouble============================================\n");

        Double[] uniCodeDouble= Arrays.stream(homeString.split(" "))
                .flatMapToInt(st->st.chars())
                .mapToDouble(z->z)
                .boxed()
                .toArray(Double[]::new);

        System.out.println("\n=================================Stream flatMapToLong============================================\n");

        Long[] uniCodeLong= Arrays.stream(homeString.split(" "))
                .flatMapToInt(st->st.chars())
                .mapToLong(s->s)
                .boxed()
                .toArray(Long[]::new);

        //Данный метод похож на flatMap но он более явен, 1 параметр это строка, второй это некий BiConsumer помойка куда мы складываем все что хотим
        //на выходе из метода получаем все то что положили внутрь помойки по элементно
        System.out.println("\n====================================Stream MapMulti=================================================\n");
        java.util.stream.Stream.of("hello", "world")
                .mapMulti((str, sink) -> {
                    for (char sim : str.toCharArray()) {
                        sink.accept(sim);
                    }
                })
                .forEach(System.out::println);

        System.out.println(java.util.stream.Stream.of(new int[]{1,2,3,4,5},new int[]{5,4,3,2,1}).mapMulti((el, pomoika)->{
            for (int i=el.length-1;i==0;i--) {
                pomoika.accept(el[i]);
            }
        }).collect(Collectors.toList()));







    }
}

class Faculty {
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

class Student {
    private final String name;
    private int age;
    private int course;

    public Student(String name, int age, int course) {
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student newAgeStudent(int age) {
        this.setAge(age);
        return this;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", course=" + course +
                '}';
    }
}
