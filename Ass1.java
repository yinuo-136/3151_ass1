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
        this.numV = 0;
        this.lastEleIndex = 0;
    }

    public void insert(int x) {
        // binaryS(x) ->index
        // findNearestNull(index) -> index2
        // x <> get(index)

        //list is full no empty element
        if (this.numV == N) return; 
        int insertIndex = this.binarySearch(x);
        
        // element already exsit, do not insert
        if (this.arr.get(insertIndex) == x) return;

        // x is the biggest element, append to the last position
        if (insertIndex > lastEleIndex) {
            this.numV++;
            this.arr.add(x);
            this.lastEleIndex++;
            return;
        } 

        //insert the element between the current list
        int nullIndex = this.findNearestNull2(insertIndex);
        if (insertIndex < nullIndex) {
            if (x < this.arr.get(insertIndex)) {
                for (int i = insertIndex; i < nullIndex; i++) {
                    int value = this.arr.get(i);
                    this.arr.set(i + 1, value);
                }
                this.arr.set(insertIndex, x);
            } else {
                for (int i = insertIndex + 1; i < nullIndex; i++) {
                    int value = this.arr.get(i);
                    this.arr.set(i + 1, value);
                }
                this.arr.set(insertIndex + 1, x);
            }
        } else {
            // shift left
        }

    }

    private int findNearestNull2(int index) {
        //list is full, return -1.
        if (this.numV == N) return -1;

        //if not full, and there's no -1, return 


        int i = index;
        int j = index;
        while (this.arr.get(i) != -1 && this.arr.get(j) != -1) {
            if (i > 0) i--;
            if (j < this.lastEleIndex) j++;

            //when lastIndex is not -1 when we're searching to the right, the nearest index
            //must be lastindex + 1
            if (j == this.lastEleIndex && this.arr.get(j) != -1) return j+1;
        }
        //int result = -1;
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
        
        
        // else if (this.arr.get(i) != -1 && this.arr.get(j) == -1) {
        //     // backward has null
        //     return j;
        // } 
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
        int resultIndex = this.binarySearch(x);
        if (this.arr.get(resultIndex) == x) {
            this.arr.set(resultIndex, -1);
            this.numV--;
            if (resultIndex == lastEleIndex) {
                int temp = lastEleIndex;
                while (this.arr.get(temp) == -1) temp--;
                this.lastEleIndex = temp;
            }
            
        }
    }
    // test.arr.add(-1);0
    //     test.arr.add(4);1
    //     test.arr.add(-1);2
    //     test.arr.add(8);3
    //     test.arr.add(-1);4
    //     test.arr.add(10);5
    private int binarySearch(int tar) {
        if (numV == 0) return 0;
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
                    System.out.println("origin" );
                    return 0; //in case mid goes out of left bound
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
        if (result > lastEleIndex) return false; 
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

    public static void main(String[] args) {
        //Ass1.test1();
        test1();
        test2();
    }

    public static void test1() {
        System.out.println("test1");
        Ass1 test = new Ass1(10);
        test.arr.add(-1);
        test.arr.add(2);
        test.arr.add(-1);
        test.arr.add(8);
        test.arr.add(10);
        test.arr.add(11);
        test.numV = 4;
        test.lastEleIndex = 5;
        System.out.println(test.binarySearch(12));
    }

    public static void test2() {
        Ass1 test = new Ass1(10);
        test.arr.add(1);
        test.arr.add(2);
        test.arr.add(3);
        test.arr.add(8);
        test.arr.add(10);
        test.arr.add(11);
        test.numV = 6;
        test.lastEleIndex = 5;
        System.out.println(test.binarySearch(12));
    }

    public static void test3() {
        Ass1 test = new Ass1(10);
        test.arr.add(2);
        test.arr.add(3);
        test.arr.add(4);
        test.arr.add(8);
        test.arr.add(10);
        test.arr.add(11);
        test.numV = 6;
        test.lastEleIndex = 5;
        System.out.println(test.binarySearch(1));
    }

    public static void test4() {
        Ass1 test = new Ass1(10);
        test.arr.add(1);
        test.arr.add(2);
        test.arr.add(3);
        test.arr.add(8);
        test.arr.add(10);
        test.arr.add(11);
        test.numV = 6;
        test.lastEleIndex = 5;
        System.out.println(test.binarySearch(4));
    }
}