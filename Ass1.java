import java.util.ArrayList;
/**
 * ass1
 */
public class Ass1 {

    // size of bounded arr
    static int N;
    // num of ele
    static int numV;
    // last ele index
    static int lastEleIndex;
    //
    static ArrayList<Integer> arr = new ArrayList<Integer>();
    // index|value ??
    // ArrayList<String> cp_Arr;

    // concurrency
    // bool isFull;
    // if cv1 = 0, do shift. init val = N/4
    int cv1;
    int shiftRatio = N / 4;

    // constructor
    public Ass1(int n) {
        N = n;
        numV = 0;
        lastEleIndex = 0;
    }

    public void insert(int x) {
        // check if the array is full
        if (numV == N) {
            return;
        }
        
        int index = binarySearch(x);

        if(index > lastEleIndex) {
            arr.add(x);
            lastEleIndex++;
            numV++;
            return;
        }
        // check duplication
        if (arr.get(binarySearch(x)) == x) {
            return;
        }

        int emptyIndex = findNearestEmpty(index);
        // check if there exists empty cell
        if (emptyIndex != -1) {
            // do the shift
            if (arr.get(index) > x) {
                index--;
            }
            shift(emptyIndex, index);

            arr.set(index, x);
            numV++;
            return;
        }

        arr.add(-1);
        lastEleIndex++;
        shift(lastEleIndex ,index);
        arr.set(index, x);
        numV++;

    }
    

    private void shift(int emptyIndex, int fromIndex) {
        // check the direction of the shift
        if (emptyIndex < fromIndex) {
            // shift left
            for(int i = emptyIndex; i < fromIndex; i++) {
                arr.set(i, arr.get(i + 1));
            }
            arr.set(fromIndex, -1);

        } else if(emptyIndex > fromIndex) {
            // shift right
            for(int i = emptyIndex; i > fromIndex; i--) {
                arr.set(i, arr.get(i - 1));
            }

            arr.set(fromIndex, -1);
        } else {
            return;
        }
    }

    // find the nearest empty index (i.e nearest -1 value cell)
    public int findNearestEmpty(int index) {
        int i = index;
        int j = index;
        int i_distance = 0;
        int j_distance = 0;
        boolean isIFind = false;
        boolean isJFind = false;

        while (i <= lastEleIndex) {
            if (arr.get(i) == -1) {
                isIFind = true;
                break;
            }
            i++;
            i_distance++;
        }

        while (j >= 0) {
            if (arr.get(j) == -1) {
                isJFind = true;
                break;
            }
            j--;
            j_distance++;
        }

        if (isIFind == true && isJFind == true) {
            if (i_distance <= j_distance) {
                return i;
            } else {
                return j;
            }
        } else if (isIFind) {
            return i;
        } else if (isJFind) {
            return j;
        } else {
            return -1;
        }

    }

    public void delete(int x) {
        int result = this.binarySearch(x);
        if (arr.get(result) == x) {
            arr.set(result, -1);
            numV--;
        }
        
    }

    private int binarySearch(int tar) {
        if (numV == 0)
            return 0;
        int left = 0;
        int right = lastEleIndex;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);

            while (arr.get(mid) == -1) {
                mid++;
                if (mid > right) {
                    right = left + ((right - left) >> 1) - 1;
                    mid = left + ((right - left) >> 1);
                    if (mid < 0) {
                        return 0;
                    }
                }

            }
            if (arr.get(mid) == tar) {
                return mid;
            } else if (tar < arr.get(mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public boolean member(int x) {
        int result = this.binarySearch(x);
    
        if (result > lastEleIndex || result < 0) {
            return false;
        }

        if (arr.get(result) != x) {
            return false;
        }
        return true;
    }

    public void print_sorted() {
        String result = "";
        result = result + "[";
        for (int ele : arr) {
            // if (ele == -1) {
            //     continue;
            // }
            result = result + Integer.toString(ele) + ", ";
        }
        result = result + "]";
        System.out.println(result);
    }
    

    // public static void main(String[] args) {
    //     Ass1 test = new Ass1(10);
    //     arr.add(2);
    //     arr.add(3);
    //     arr.add(-1);
    //     arr.add(6);
    //     arr.add(10);
    //     numV = 4;
    //     lastEleIndex = 4;
    //     test.print_sorted();
    //     System.out.println(test.member(1));
    //     System.out.println(test.member(10));
    //     System.out.println(test.member(6));
    //     test.insert(8);



    //     test.print_sorted();
    // }


    //     public static void test1() {
    //     System.out.println("================test1=============");
    //     Ass1 test = new Ass1(10);
    //     arr.add(1);
    //     arr.add(2);
    //     arr.add(3);
    //     arr.add(4);
    //     numV = 4;
    //     lastEleIndex = 3;
    //     test.binarySearch(5);
    //     test.insert(5);
    //     System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
    //     test.print_sorted();
    // }

    // public static void test2() {
    //     System.out.println("=========================test2======================");
    //     Ass1 test = new Ass1(10);
    //     arr.add(1);
    //     arr.add(2);
    //     arr.add(3);
    //     arr.add(8);
    //     arr.add(10);
    //     arr.add(11);
    //     numV = 6;
    //     test.lastEleIndex = 5;
    //     test.binarySearch(12);
    //     test.insert(12);
    //     System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
    //     test.print_sorted();
    // }

    // public static void test3() {
    //     System.out.println("==============test3=================");
    //     Ass1 test = new Ass1(10);
    //     test.arr.add(2);
    //     test.arr.add(3);
    //     test.arr.add(4);
    //     test.arr.add(8);
    //     test.arr.add(10);
    //     test.arr.add(11);
    //     test.numV = 6;
    //     test.lastEleIndex = 5;
    //     test.binarySearch(1);
    //     test.insert(1);
    //     System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
    //     test.print_sorted();
    // }

    // public static void test4() {
    //     System.out.println("==============test4=================");
    //     Ass1 test = new Ass1(10);
    //     test.arr.add(1);
    //     test.arr.add(2);
    //     test.arr.add(3);
    //     test.arr.add(8);
    //     test.arr.add(10);
    //     test.arr.add(11);
    //     test.numV = 6;
    //     test.lastEleIndex = 5;
    //     test.binarySearch(8);
    //     test.delete(8);
    //     test.insert(9);
    //     System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
    //     test.print_sorted();
    //     System.out.print("list has -1: ");
    //     System.out.println(test.arr);
    // }

    // public static void test5() {
    //     System.out.println("==============test5=================");
    //     Ass1 test = new Ass1(10);
    //     test.arr.add(1);
    //     test.arr.add(2);
    //     test.arr.add(3);
    //     test.arr.add(8);
    //     test.arr.add(10);
    //     test.arr.add(11);
    //     test.numV = 6;
    //     test.lastEleIndex = 5;
    //     test.binarySearch(8);
    //     test.delete(8);
    //     test.delete(10);
    //     test.insert(9);
    //     System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
    //     test.print_sorted();
    //     System.out.print("list has -1: ");
    //     System.out.println(test.arr);
    // }
    
    // public static void test6() {
    //     System.out.println("==============test6=================");
    //     Ass1 test = new Ass1(10);
    //     test.arr.add(1);
    //     test.arr.add(2);
    //     test.arr.add(3);
    //     test.arr.add(8);
    //     test.arr.add(10);
    //     test.arr.add(11);
    //     test.numV = 6;
    //     test.lastEleIndex = 5;
    //     test.binarySearch(8);
    //     test.delete(8);
    //     test.delete(10);
    //     test.delete(11);
    //     test.insert(9);
    //     System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
    //     test.print_sorted();
    //     System.out.print("list has -1: ");
    //     System.out.println(test.arr);
    // }

    //  public static void test7() {
    //     System.out.println("==============test7=================");
    //     Ass1 test = new Ass1(10);
    //     test.arr.add(1);
    //     test.arr.add(2);
    //     test.arr.add(4);
    //     arr.add(8);
    //     test.arr.add(10);
    //     test.arr.add(11);
    //     test.numV = 6;
    //     test.lastEleIndex = 5;
    //     test.binarySearch(8);
    //     test.delete(8);
    //     test.delete(10);
    //     test.delete(11);
    //     test.insert(3);
    //     System.out.println("numV:" + test.numV + "\nlastEleIndex:" + test.lastEleIndex);
    //     test.print_sorted();
    //     System.out.print("list has -1: ");
    //     System.out.println(test.arr);
    // }

    public static void test8() {
        System.out.println("==============test8=================");
        Ass1 test = new Ass1(10);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(8);
        arr.add(10);
        arr.add(11);
        //test.arr.add(12);
        numV = 6;
        lastEleIndex = 5;
        test.binarySearch(8);
        test.delete(2);
        test.delete(3);
        test.insert(5);
        //test.insert(3);
        System.out.println("numV:" + numV + "\nlastEleIndex:" + lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(arr);
    }

    public static void test9() {
        System.out.println("==============test9=================");
        Ass1 test = new Ass1(10);
        arr.add(2);
        arr.add(-1);
        arr.add(4);
        arr.add(8);
        arr.add(-1);
        arr.add(11);
        //test.arr.add(12);
        numV = 4;
        lastEleIndex = 5;
        test.binarySearch(12);
        test.insert(12);
        //test.insert(3);
        System.out.println("numV:" + numV + "\nlastEleIndex:" + lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(arr);
    }
    
    /*
     * When arr is full, you have to compress(delete all -1)
     * 
     */
    public static void test10() {
        
        System.out.println("==============test10=================");
        Ass1 test = new Ass1(6);
        arr.add(2);
        arr.add(3);
        arr.add(8);
        arr.add(-1);
        arr.add(11);
        //test.arr.add(12);
        numV = 4;
        lastEleIndex = 4;
        test.binarySearch(12);
        test.insert(12);
        
        test.insert(9);
        //test.insert(3);
        System.out.println("numV:" + numV + "\nlastEleIndex:" + lastEleIndex);
        test.print_sorted();
        System.out.print("list has -1: ");
        System.out.println(arr);
    }
    public static void main(String[] args) {
        //Ass1.test1();
        // test1();
        // test2();
        // test3();
        // test4();
        // test5();
        // test6();
        //test7();
        test8();
        // test9();
        // test10();
    }
}