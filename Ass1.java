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

    }

    public void delete(int x) {

    }

    private int binarySearch(int tar) {
        int left = 0;
        int right = this.lastEleIndex;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (this.arr.get(mid) == -1) {
                if (mid > 0) {
                    mid = mid - 1;
                } else {
                    mid = mid + 1;
                }
            } else {
                if (this.arr.get(mid) == tar) {
                    return mid;
                } else if (tar < this.arr.get(mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    public boolean member(int x) {
        int result = this.binarySearch(x);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public void print_sorted() {
        String result = "";
        result = result + "[";
        for (int ele : this.arr) {
            result = result + Integer.toString(ele) + ", ";
        }
        result = result + "]";
        System.out.println(result);
    }

    public static void main(String[] args) {
        Ass1 test = new Ass1(10);
        test.arr.add(0);
        test.arr.add(2);
        test.arr.add(4);
        test.arr.add(5);
        test.arr.add(8);
        test.numV = 5;
        test.lastEleIndex = 4;

        System.out.println(test.member(3));
        System.out.println(test.member(2));
        test.print_sorted();
    }
}