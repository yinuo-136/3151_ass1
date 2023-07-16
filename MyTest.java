public class MyTest {

    // thread operations
    public static class deleteThread extends Thread {
        int deleteNum;

        deleteThread(int Num) {
            deleteNum = Num;
        }

        public void deleteOp(Ass1 test, int num) throws InterruptedException {
            test.delete(num);
        }

        @Override
        public void run() {
            Ass1 newTest = new Ass1();
            try {
                deleteOp(newTest, deleteNum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
 
     }

     public static class insertThread extends Thread {
        int insertNum;

        insertThread(int Num) {
            insertNum = Num;
        }

        public void insertOp(Ass1 test, int num) throws InterruptedException {
            test.insert(num);
        }

        @Override
        public void run() {
            Ass1 newTest = new Ass1();
            try {
                insertOp(newTest, insertNum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
 
     }

     public static class memberThread extends Thread {
        int target;

        memberThread(int num) {
            target = num;
        }

        public boolean memberOp(Ass1 test, int num) throws InterruptedException {
            return test.member(num);
        }

        @Override
        public void run() {
            Ass1 newTest = new Ass1();
            try {
                if (memberOp(newTest, target)) {
                    System.out.println("number found!");
                } else {
                    System.out.println("number not found!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
     }


    // test the array will have a fixed bound, i.e after inserted N number of elements, it will block the further insertion
    // until deletions occur.

    // test 1: test block case
    public static void TestCaseFixBoundOne() throws InterruptedException {
        Ass1 test = new Ass1();
        test.insert(8);
        test.insert(10);
        test.insert(9);
        test.insert(1);
        test.insert(2);
        test.insert(3); // this further insertion will be blocked
    }

    // test 2: test delete after block
    public static void TestCaseFixBoundTwo() throws InterruptedException {
        Ass1 test = new Ass1();
        test.insert(8);
        test.insert(10);
        test.insert(9);
        test.insert(1);
        test.insert(2);
        // this insertion will be blocked
        insertThread insertThree = new insertThread(3);
        insertThree.start();
        
        // another thread to delete existing element
        deleteThread deletionEight = new deleteThread(8);
        deletionEight.start();

        // now the test can run to the end without getting blocked.
        Thread.sleep(1000);
        test.print_sorted();
    }

    // test the safety property of this implementation, 
    // i.e read operations will never return false result when there is writing operation concurrently running
    
    // test: 
    public static void TestCaseSaftey() throws InterruptedException {
        Ass1 test = new Ass1();
        test.insert(8);
        test.insert(10);
        test.insert(9);
        test.insert(1);
        test.insert(2);
        test.delete(8);
        
        insertThread insertThree = new insertThread(11);
        insertThree.start();

        memberThread memberNine = new memberThread(9);
        memberNine.start(); // this should print out "number found"
    }

    // test a more complex cases with multiple threads running the same time and doing different operations
    public static void TestCaseComplex() throws InterruptedException {
        Ass1 test = new Ass1();
        test.insert(8);
        test.insert(10);

        // insert threads
        for(int i = 1; i < 4; i++) {
            insertThread insertNum = new insertThread(i);
            insertNum.start();
        }

        // delete threads
        for(int j = 1; j < 3; j++) {
            deleteThread deleteNum = new deleteThread(j);
            deleteNum.start();
        }

        // member threads, should all print "number found"
        memberThread memberEight = new memberThread(8);
        memberEight.start();
        memberThread memberTen = new memberThread(10);
        memberTen.start();

        test.print_sorted(); // don't have to be the final array
    }

    public static void main(String[] args) throws InterruptedException {
        // test 1: test block case
        // Expected output: The insertion should be blocked. Therefore not output is expected.
        TestCaseFixBoundOne(); 

        // test 2: test delete after block
        // Expected output: {1, 2, 3, 9, 10}
        // TestCaseFixBoundTwo();

        // test 3 the safety property of this implementation, 
        // i.e read operations will never return false result when there is writing operation concurrently running
        // Expected output: number found!
        // TestCaseSaftey();

        // test 4 a more complex cases with multiple threads running the same time and doing different operations
        // Expected output: 
        // {3, 8, 10, }
        // number found!
        // number found!
        // TestCaseComplex();
    }
}
