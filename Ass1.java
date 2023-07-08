import java.util.ArrayList;

/**
 * ass1
 */
public class Ass1 extends Thread {

    // size of bounded arr
    int N;
    // num of ele
    int numV;
    // last ele index
    int lastEleIndex;
    //
    ArrayList<Integer> arr = new ArrayList<Integer>();
    // index|value ??
    // ArrayList<String> cp_Arr;

    // concorrency
    // bool isFull;
    // if cv1 = 0, do shift. init val = N/4
    int cv1;
    int shiftRatio = N / 4;

    // constructor
    public Ass1(int N) {
        this.N = N;
        //if N > 0, insert a -1 to the list
        if (this.N > 0) arr.add(-1);
        this.numV = 0;
        this.lastEleIndex = 0;
    }

    public void insert(int x) {
        // binaryS(x) ->index
        // findNearestNull(index) -> index2
        // x <> get(index)

        // list is full no empty element
        if (this.numV == N)
            return;
        int insertIndex = this.binarySearch(x);

        if (insertIndex == lastEleIndex + 1) {

        } else {
            // element already exsit, do not insert
            if (this.arr.get(insertIndex) == x)
                return;
        }


        // x is the biggest element, append to the last position
        if (insertIndex > lastEleIndex) {
            if (!this.arr.contains(-1)) {
                // if everything before insertIndex has no -1, we add the element
                this.numV++;
                this.arr.add(x);
                this.lastEleIndex++;
                return;
            } else {
                // there's -1 before insertIndex, we insert at the lastIndex
                // System.out.println("xxxxxxxxxxxhahaxxxxxxxxxxx");
                insertIndex = this.lastEleIndex;
            }
        }

        // insert the element between the current list
        int nullIndex = this.findNearestNull2(insertIndex);
        System.out.println("insertidx:" + insertIndex);
        System.out.println("nullidx:" + nullIndex);
        if (insertIndex < nullIndex) {
            // RIGHT SHIFT
            if (x < this.arr.get(insertIndex)) {
                // the element we want to insert is larger than the element with index -
                // insertIndex
                int previous_ele = 0;
                for (int i = insertIndex; i <= nullIndex; i++) {

                    if (i > lastEleIndex) {
                        // only execute at the last iteration
                        this.arr.add(previous_ele);
                        this.lastEleIndex++;
                    } else {
                        int temp = this.arr.get(i);
                        this.arr.set(i, previous_ele);
                        previous_ele = temp;
                    }


                }
                this.arr.set(insertIndex, x);
            } else {
                int previous_ele = 0;
                for (int i = insertIndex + 1; i <= nullIndex; i++) {
                    if (i > lastEleIndex) {
                        // only execute at the last iteration
                        this.arr.add(previous_ele);
                        this.lastEleIndex++;
                    } else {
                        int temp = this.arr.get(i);
                        this.arr.set(i, previous_ele);
                        previous_ele = temp;
                    }
                }
                this.arr.set(insertIndex + 1, x);
            }
        } else {
            // LEFT SHIFT
            if (x < this.arr.get(insertIndex)) {
                int previous_ele = 0;
                for (int i = insertIndex - 1; i >= nullIndex; i--) {
                    int temp = this.arr.get(i);
                    this.arr.set(i, previous_ele);
                    previous_ele = temp;
                }
                this.arr.set(insertIndex - 1, x);
            } else {
                int previous_ele = 0;
                for (int i = insertIndex; i >= nullIndex; i--) {
                    int temp = this.arr.get(i);
                    this.arr.set(i, previous_ele);
                    previous_ele = temp;
                }
                this.arr.set(insertIndex, x);
            }
        }
        this.numV++;
        return;

    }

    /*
    public void compress() {
        // arr is null, do nothing
        if (this.numV == 0) {
            return;
        }
        // arr length is 1 and first item not null return
        if (this.numV == 1 && this.arr.get(0) != -1) {
            return;
        }
        int oldLastEleIndex = this.lastEleIndex;
        int i = 1;
        while (i <= oldLastEleIndex) {
            
            int curItem = this.arr.get(i);
            if (curItem != -1) {
                int j = i - 1;
                if (this.arr.get(j) == -1) {
                    this.lastEleIndex--;
                }
                while (j >= 0 && this.arr.get(j) == -1) {
                    this.arr.set(j, curItem);
                    this.arr.set(j+1,-1);
                    j--;
                }               
            }
            i++;
        }
        System.out.println("a: " + this.lastEleIndex);
        System.out.println(oldLastEleIndex);
        for (int k = oldLastEleIndex; k > lastEleIndex; k --) {
            this.arr.remove(k);
        }
    }
    */

    public void compress2() {
        // arr is null, do nothing
        if (this.numV == 0) {
            return;
        }
        if (this.numV == 1 && this.arr.get(0) != -1) {
            return;
        }
        this.arr.removeIf(n -> (n == -1));
        this.lastEleIndex = this.arr.size() - 1;
        
    }

    private int findNearestNull2(int index) {
        // list is full, return -1.
        if (this.numV == N)
            return -1;

        int i = index;
        int j = index;
        while (this.arr.get(i) != -1 && this.arr.get(j) != -1) {
            if (i > 0)
                i--;
            if (j < this.lastEleIndex)
                j++;

            // when lastIndex is not -1 when we're searching to the right, the nearest index
            // must be lastindex + 1
            System.out.println("i:" + i + " j:" + j);
            if (j == this.lastEleIndex && this.arr.get(j) != -1) {
                if (this.arr.get(i) != -1) {
                    return j + 1;
                }
            }
        }

        // int result = -1;
        if (this.arr.get(i) == -1 && this.arr.get(j) == -1) {
            // back and front both have null
            if (index - i <= j - index) {
                return i;
            } else {
                return j;
            }

        } else if (this.arr.get(i) == -1 && this.arr.get(j) != -1) {
            // front has null
            return i;
        } else {
            // back has null
            return j;
        }

    }

    public int findNearestNull1(int index) {
        int i = index;
        int j = index;
        int i_distance = 0;
        int j_distance = 0;
        boolean isIFind = false;
        boolean isJFind = false;

        while (i <= this.lastEleIndex) {
            if (this.arr.get(i) == -1) {
                isIFind = true;
                break;
            }
            i++;
            i_distance++;
        }

        while (j >= 0) {
            if (this.arr.get(j) == -1) {
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
        //if there's no element in the list, skip(currently)
        if (numV == 0) return;
        int resultIndex = this.binarySearch(x);
        if (this.arr.get(resultIndex) == x) {
            this.arr.set(resultIndex, -1);
            this.numV--;
            if (resultIndex == lastEleIndex) {
                int temp = lastEleIndex;
                while (this.arr.get(temp) == -1) {
                    if (temp == 0) {
                        // at index 0, the block is still empty, break
                        this.lastEleIndex = temp;
                        break;
                    }
                    this.arr.remove(temp);
                    temp--;

                }
                this.lastEleIndex = temp;
            }

        }
    }

    // test.arr.add(-1);0
    // test.arr.add(4);1
    // test.arr.add(-1);2
    // test.arr.add(8);3
    // test.arr.add(-1);4
    // test.arr.add(10);5
    private int binarySearch(int tar) {
        if (numV == 0)
            return 0;
        int left = 0;
        int right = this.lastEleIndex;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            while (this.arr.get(mid) == -1) {
                mid++;
                if (mid > right) {
                    right = left + ((right - left) >> 1) - 1;
                    mid = left + ((right - left) >> 1);
                }
                if (mid < 0) {
                    System.out.println("origin");
                    return 0; // in case mid goes out of left bound
                }

            }
            if (this.arr.get(mid) == tar) {
                return mid;
            } else if (tar < this.arr.get(mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.println("left :" + Integer.toString(left));
        System.out.println("right :" + Integer.toString(right));
        return left;
    }

    public boolean member(int x) {
        int result = this.binarySearch(x);
        // System.out.println(result);
        if (result > lastEleIndex)
            return false;
        if (this.arr.get(result) != x) {
            return false;
        }
        return true;
    }

    public void print_sorted() {
        String result = "";
        result = result + "[";
        for (int ele : this.arr) {
            if (ele == -1) {
                continue;
            }
            result = result + Integer.toString(ele) + ", ";
        }
        result = result + "]";
        System.out.println(result);
    }

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
        test.delete(8);
        test.delete(9);
        test.delete(8100);
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