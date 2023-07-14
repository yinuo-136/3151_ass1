package src;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class Tests {

    public static class Compress extends Thread {

        Ass1 ass1;
        ArrayList<Thread> threads;
        public Compress(Ass1 a, ArrayList<Thread> ts) {
            this.ass1 = a;
            this.threads = ts;
        }

        @Override
        public void run() {

            while (oneThreadAlive()) {
                if (ass1.lastEleIndex - ass1.numV > ass1.shiftRatio) {
                    try {
                        ass1.compress2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                

            }


        }

        private boolean oneThreadAlive() {
            boolean result = false;
            for (Thread t : this.threads) {
                result = result || t.isAlive();
            }
            return result;
        }

    }

    public static class InsertWriterRandom extends Thread {

        Ass1 ass1;

        public InsertWriterRandom(Ass1 a) {
            this.ass1 = a;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                try {
                    Random rn = new Random();
                    int answer = rn.nextInt(10);
                    ass1.insert(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static class InsertWriterInOrder extends Thread {

        Ass1 ass1;

        public InsertWriterInOrder(Ass1 a) {
            this.ass1 = a;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("insert " + i);
                    ass1.insert(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static class MemberRandomReader extends Thread {

        Ass1 ass1;

        public MemberRandomReader(Ass1 a) {
            this.ass1 = a;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                Random rn = new Random();
                int answer = rn.nextInt(10);
                try {
                    System.out.println(this + "member: " + answer + "is in the list? ->" + ass1.member(answer));
                    //ass1.print_Sem_arr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            // try {
            //     System.out.println(this + "member: " + 1 + "is in the list? ->" + ass1.member(1));
            // } catch (InterruptedException e) {
     
            //     e.printStackTrace();
            // }

        }
    }

    public static class MemberReader extends Thread {

        Ass1 ass1;

        public MemberReader(Ass1 a) {
            this.ass1 = a;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println(this + "member: " + i + "is in the list? ->" + ass1.member(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static class DeleteWriter extends Thread {

        Ass1 ass1;

        public DeleteWriter(Ass1 a) {
            this.ass1 = a;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println(this + "delete: " + i);
                    ass1.delete(i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static class DeleteRandomWriter extends Thread {

        Ass1 ass1;

        public DeleteRandomWriter(Ass1 a) {
            this.ass1 = a;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                Random rn = new Random();
                int answer = rn.nextInt(10);
                try {
                    // System.out.println(this + "delete: " + answer);
                    // ass1.delete(answer);
                    //System.out.println(this + "delete: " + 3);
                    ass1.delete(answer);
                    //System.out.println("======================deleter===================");
                    //System.out.println(ass1.arr);
                    // ass1.compress2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // try {
            //     //System.out.println(this + "delete: " + 1);
            //     ass1.delete(1);
            // } catch (InterruptedException e) {
    
            //     e.printStackTrace();
            // }
            

            
        }
    }

    public static void test1() throws InterruptedException {
        System.out.println("================test1=============");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.numV = 4;
        test.lastEleIndex = 3;
        // test.binarySearch(5);
        test.insert(5);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test2() throws InterruptedException {
        System.out.println("=========================test2======================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        // test.binarySearch(0);
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

    public static void test3() throws InterruptedException {
        System.out.println("==============test3=================");
        Ass1 test = new Ass1(10);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        // test.binarySearch(1);
        test.insert(1);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test4() throws InterruptedException {
        System.out.println("==============test4=================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        // test.binarySearch(8);

        test.delete(8);
        test.insert(9);

        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        test.print_Sem_arr();
    }

    public static void test5() throws InterruptedException {
        System.out.println("==============test5=================");
        Ass1 test = new Ass1(10);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        // test.binarySearch(8);
        test.delete(8);
        test.delete(10);
        test.insert(9);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test6() throws InterruptedException {
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
        // test.binarySearch(8);
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

    public static void test7() throws InterruptedException {
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
        // test.binarySearch(8);
        test.delete(8);
        test.delete(10);
        test.delete(11);
        test.insert(3);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test8() throws InterruptedException {
        System.out.println("==============test8=================");
        Ass1 test = new Ass1(10);
        test.insert(2);

        test.insert(3);
        test.insert(4);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        test.insert(12);
        // test.binarySearch(8);
        test.delete(2);
        test.delete(3);
        test.insert(5);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
        // System.out.println("======");
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

    public static void test9() throws InterruptedException {
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
        // test.binarySearch(12);
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
    public static void test10() throws InterruptedException {

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
        // test.binarySearch(12);
        test.insert(12);

        test.insert(9);
        // test.insert(3);
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test11() throws InterruptedException {
        System.out.println("==============test11=================");
        Ass1 test = new Ass1(10);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(8);
        test.insert(10);
        test.insert(11);
        // test.binarySearch(1);
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

    public static void test12() throws InterruptedException {
        System.out.println("==============test12=================");
        Ass1 test = new Ass1(1);
        test.numV = 0;
        test.lastEleIndex = 0;
        // test.binarySearch(1);
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

    public static void test13() throws InterruptedException {
        System.out.println("==============test13 COMPRESS=================");
        // COMPRESS TEST
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
        // test.compress();
        test.compress2();

        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test14() throws InterruptedException {
        System.out.println("==============test14 COMPRESS=================");
        // COMPRESS TEST
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
        // test.compress();
        test.compress2();
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test15() throws InterruptedException {
        System.out.println("==============test15 COMPRESS=================");
        // COMPRESS TEST
        Ass1 test = new Ass1(3);
        test.insert(2);
        test.insert(3);
        test.insert(4);

        test.delete(2);
        test.delete(3);

        // test.compress();
        test.compress2();
        // test.compress2();
        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test16() throws InterruptedException {
        System.out.println("==============test16 CONCURRENCY MEMBER=================");
        // COMPRESS TEST
        Ass1 test = new Ass1(10);
        test.insert(0);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(7);
        test.insert(8);
        test.insert(9);

        MemberRandomReader m1 = new MemberRandomReader(test);
        MemberRandomReader m2 = new MemberRandomReader(test);

        m1.start();
        m2.start();

        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test17() throws InterruptedException {
        System.out.println("==============test17 CONCURRENCY MEMBER AND DELETE=================");
        // COMPRESS TEST
        Ass1 test = new Ass1(10);
        test.insert(0);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(7);
        test.insert(8);
        test.insert(9);
        // System.out.println("=======jjjjjjjjjjjjjjjjjjjjj=======");
        // test.print_Sem_arr();
        // MemberReader m1 = new MemberReader(test);
        // DeleteWriter m2 = new DeleteWriter(test);

        MemberReader m1 = new MemberReader(test);
        DeleteRandomWriter m2 = new DeleteRandomWriter(test);

        m2.start();
        m1.start();

        m1.join();
        m2.join();

        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test18() throws InterruptedException {
        System.out.println("==============test18 CONCURRENCY MEMBER AND DELETE AND INSERT=================");
        // COMPRESS TEST
        Ass1 test = new Ass1(10);
        test.insert(0);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(7);
        test.insert(8);
        test.insert(9);

        test.print_Sem_arr();
        // MemberReader m1 = new MemberReader(test);
        // DeleteWriter m2 = new DeleteWriter(test);

        MemberRandomReader m1 = new MemberRandomReader(test);
        DeleteRandomWriter m2 = new DeleteRandomWriter(test);
        InsertWriterInOrder m3 = new InsertWriterInOrder(test);

        m2.start();
        m1.start();
        m3.start();

        m1.join();
        m2.join();
        m3.join();

        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test19() throws InterruptedException {
        System.out.println("==============test19 CONCURRENCY ALL RANDOM=================");
        // COMPRESS TEST
        Ass1 test = new Ass1(10);
        test.insert(0);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(7);
        test.insert(8);
        test.insert(9);

        test.print_Sem_arr();
        // MemberReader m1 = new MemberReader(test);
        // DeleteWriter m2 = new DeleteWriter(test);
        MemberRandomReader m1 = new MemberRandomReader(test);
        DeleteRandomWriter m2 = new DeleteRandomWriter(test);
        InsertWriterRandom m3 = new InsertWriterRandom(test);
        // Compress m4 = new Compress(test);

        // m4.start();
        m2.start();
        m1.start();
        m3.start();

        m1.join();
        m2.join();
        m3.join();
        // m4.join();

        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void test20() throws InterruptedException {
        System.out.println("==============test20 COMPRESS=================");
        // COMPRESS TEST
        Ass1 test = new Ass1(10);
       
        //test.insert(0);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(7);
        test.insert(8);
        test.insert(9);

        test.delete(2);
        test.delete(3);
        test.delete(4);
        test.delete(5);
        test.delete(6);
        test.delete(7);

        //test.print_Sem_arr();
        // MemberReader m1 = new MemberReader(test);
        // DeleteWriter m2 = new DeleteWriter(test);
        MemberRandomReader m1 = new MemberRandomReader(test);
        DeleteRandomWriter m2 = new DeleteRandomWriter(test);
        //InsertWriterRandom m3 = new InsertWriterRandom(test);

        ArrayList<Thread> ts = new ArrayList<>();
        ts.add(m1);
        ts.add(m2);
        //ts.add(m3);
        Compress m4 = new Compress(test,ts);

        
        m2.start();
        m1.start();
        //m3.start();
        m4.start();

        m1.join();
        m2.join();
        //m3.join();
        m4.join();

        System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(test.arr);
    }

    public static void main(String[] args) throws InterruptedException {
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
        // test12();
        // test13();
        // test14();
        // test15();
        // test16();
        //test17();
        //test18();
        // test19();
        test20();
        // HashMap<Integer, ArrayList<Integer>> h = new HashMap<>();

        // h.put(1, new ArrayList<Integer>());
        // //key
        // int key = (int) h.keySet().toArray()[0];
        // System.out.println(key + " " + (h.get(key)).getClass());

    }
}
