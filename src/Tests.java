package src;

public class Tests {
    
    public static void test1() {
        System.out.println("================test1=============");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.numV = 4;
        test.lastEleIndex = 3;
        test.binarySearch(5);
        test.insert(5);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test2() {
        System.out.println("=========================test2======================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.binarySearch(0);
        test.insert(12);
        test.insert(13);
        test.insert(14);
        test.insert(15);
        test.insert(16);
        test.insert(16);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test3() {
        System.out.println("==============test3=================");
        Ass1 test = new Ass1(10);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.binarySearch(1);
        test.insert(1);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test4() {
        System.out.println("==============test4=================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.binarySearch(8);
        test.delete(8);
        test.insert(9);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test5() {
        System.out.println("==============test5=================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.binarySearch(8);
        test.delete(8);
        test.delete(10);
        test.insert(9);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test6() {
        System.out.println("==============test6=================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        // test.insert(1);
        // test.insert(2);
        // test.insert(3);
        // test.insert(8);
        // test.insert(10);
        // test.insert(11);
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        test.binarySearch(8);
        test.delete(8);
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        test.delete(10);
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        test.delete(11);
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        test.insert(9);
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test7() {
        System.out.println("==============test7=================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(4);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.numV = 6;
        test.lastEleIndex = 5;
        test.binarySearch(8);
        test.delete(8);
        test.delete(10);
        test.delete(11);
        test.insert(3);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test8() {
        System.out.println("==============test8=================");
        Ass1 test = new Ass1(10);
        test.insert(2);
        
        test.insert(3);
        test.insert(4);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.insert(12);
        test.binarySearch(8);
        test.delete(2);
        test.delete(3);
        test.insert(5);
        System.out.println("======");
        test.insert(6);
        System.out.println("======");
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        test.insert(7);
        // test.insert(3);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test9() {
        System.out.println("==============test9=================");
        Ass1 test = new Ass1(10);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(8);
        test.insert(9);
        test.insert(11);
        // test.arr.add(12);
        test.delete(3);
        test.delete(9);
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        test.binarySearch(12);
        test.insert(12);
        test.insert(3);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    /*
     * When arr is full, you have to compress(delete all -1)
     * 
     */
    public static void test10() {

        System.out.println("==============test10=================");
        Ass1 test = new Ass1(6);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        // test.arr.add(12);
        test.delete(10);
        test.binarySearch(12);
        test.insert(12);

        test.insert(9);
        // test.insert(3);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test11() {
        System.out.println("==============test11=================");
        Ass1 test = new Ass1(10);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.binarySearch(1);
        test.delete(4);
        test.delete(5);
        test.delete(6);
        test.delete(8);
        test.delete(10);
        test.delete(11);
        test.insert(9);
        test.insert(8);
        test.insert(8100);
        // test.delete(8);
        // test.delete(9);
        // test.delete(8100);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test12() {
        System.out.println("==============test12=================");
        Ass1 test = new Ass1(1);
        test.numV = 0;
        test.lastEleIndex = 0;
        test.binarySearch(1);
        test.delete(8);
        test.insert(8);
        // test.delete(8);
        // test.delete(9);
        // test.delete(8100);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test13() {
        System.out.println("==============test13 COMPRESS=================");
        //COMPRESS TEST
        Ass1 test = new Ass1(10);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.delete(2);
        test.delete(3);
        test.delete(4);
        //test.compress();
        test.compress2();


        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test14() {
        System.out.println("==============test14 COMPRESS=================");
        //COMPRESS TEST
        Ass1 test = new Ass1(3);
        test.insert(2);
        test.insert(3);
        test.insert(4);

        test.delete(2);
        test.delete(3);
        test.delete(4);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        //test.compress();
        test.compress2();
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test15() {
        System.out.println("==============test15 COMPRESS=================");
        //COMPRESS TEST
        Ass1 test = new Ass1(3);
        test.insert(2);
        test.insert(3);
        test.insert(4);

        test.delete(2);
        test.delete(3);
       
        //test.compress();
        test.compress2();
        //test.compress2();
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void main(String[] args) {
        // Ass1.test1();
        // test1();
        // test2();
        // test3();
        // test4();
        // test5();
        // test6();
        // test7();
        // test8();
        // test9();
        // test10();
        // test11();
        //test12();
        test13();
        test14();
        test15();
    }
}
