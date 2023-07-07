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
        
        if (numV == 0) {
            arr.add(x);
            lastEleIndex = 0;
            numV++;
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
            if (arr.get(index) < x && emptyIndex > index) {
                index++; 
            } else if (arr.get(index) > x && emptyIndex < index) {
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
    

    public static void main(String[] args) {
        Ass1 test = new Ass1(10);
        numV = 0;
        lastEleIndex = 0;
        test.insert(8);
        test.insert(10);
        test.insert(9);
        test.insert(1);




        test.print_sorted();
    }
}