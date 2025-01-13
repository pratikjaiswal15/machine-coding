package org.example;

import java.util.List;

class Student {
    String name;
    int marks;
    String departmenet;

}
// Student - name, marks, department.
// List of stuents
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.

//        List<Student> studentList
//        .stream()
//                .filter(student -> student.marks > 80)
//                .map(student -> student.name)
//                .toList()
//        ;

        // 1,2,3,4,5,
        // 2,3,4,5
        // 3,4,5

        int[] arr = {1,2,3,4};


        for(int i=1; i<arr.length; i++) {
            arr[i-1] = arr[i]; // 2,3,3
        }


    }

    public void enqueue(List<Integer> arr, int num) {
        arr.add(num);
    }

    public Integer dequeue(List<Integer> arr) {

        int dequePointer = 0;

        arr.remove(dequePointer);
        dequePointer++;


        Integer firstNum = arr.get(0);
//
//        for(int i=1; i<arr.size(); i++) {
//            arr.set(i - 1, arr.get(i));
//        }
//
//        arr.remove(arr.size() - 1);
        return firstNum;
    }
}