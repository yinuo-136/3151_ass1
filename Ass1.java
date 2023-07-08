import java.util.ArrayList;
/**
 * ass1
 */
public class Ass1 {

    // size of bounded arr
    static int N;
    // num of actual element in the array
    static int numV;
    // last ele index
    static int lastEleIndex;

    // the array containing data
    static ArrayList<Integer> arr = new ArrayList<Integer>();

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
        
        // check if the array is empty
        if (numV == 0) {
            arr.add(x);
            lastEleIndex = 0;
            numV++;
            return;
        }

        int index = binarySearch(x);

        // check duplication
        if (arr.get(index) == x) {
            return;
        }

        if(index > lastEleIndex && lastEleIndex < N - 1) {
            arr.add(x);
            lastEleIndex++;
            numV++;
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

    // compact the array when there are too many empty slots(i.e -1 inside the array)
    public void compact() {
        // arr is null, do nothing
        if (numV == 0) {
            return;
        }
        if (numV == 1 && arr.get(0) != -1) {
            return;
        }
        arr.removeIf(n -> (n == -1));
        lastEleIndex = arr.size() - 1;
        
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
        result = result + "{";
        int index = 0;
        for (int ele : arr) {
            // if (ele == -1) {
            //     continue;
            // }
            result = result + Integer.toString(ele);
            if (index < lastEleIndex) {
                result = result + ", ";
            }
            index++;
        }
        result = result + "}";
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

        test.delete(8);
        test.delete(10);

        System.out.println(numV);
        System.out.println(lastEleIndex);



        test.print_sorted();
    }
}