package patterns.behavioral.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingExample {

    List<Person> persons = new ArrayList<>();

    public static void main(String[] args) {
        var sortingExample = new SortingExample();
        sortingExample.persons.addAll(List.of(
                Person.of("Martin", 43),
                Person.of("Kalle", 10),
                Person.of("Anna", 18),
                Person.of("Sven", 78)));

        //  Comparator<Person> sortingStrategy = new SortPersonByNameComparatorStrategy();
        //  Comparator<Person> sortingStrategy = new SortPersonByAgeComparatorStrategy();
        //  sortingExample.persons.sort(sortingStrategy);

        //Strategy Pattern with Java Anonymous Class Implementation
        sortingExample.persons.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.age - o2.age;
            }
        });

        //Strategy Pattern using lambda for implementation
        sortingExample.persons.sort((o1, o2) -> o1.age - o2.age);

        PrintingStrategy printingStrategy = new PrintListOnNewLines();
        //PrintingStrategy printingStrategy = new PrintListOnSameLine();
        printingStrategy.printList(sortingExample.persons);

    }
}
