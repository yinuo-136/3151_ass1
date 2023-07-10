import java.util.ArrayList;
import java.util.concurrent.Semaphore;
/**
 * ass1
 */
public class Ass1 {

    // size of bounded arr
    static int N = 5;   // this can be modified, currently set to 5 for test purposes
    // num of actual element in the array
    static int numV = 0;
    // last ele index
    static int lastEleIndex = 0;

    // the array containing data
    static ArrayList<Integer> arr = new ArrayList<Integer>();

    // an array of semphores that each controls writer and reader to a single cell, i.e semArr[0] manage arr[0]'s reads and writes.
    static ArrayList<WriterReaderLock> semArr = new ArrayList<>();
    
    // a semphore to control the array so there can not be above N elements inside the array
    static Semaphore arrLockSem = new Semaphore(N, true); 

    // a lock that each writer operations(insert and delete in this case) needs to acquire before they do any operation.
    static Semaphore insertDeletelockSem = new Semaphore(1, true);

    // // constructor
    public Ass1() {
        if (semArr.size() == 0) {
            for (int i = 0; i < N; i++) {
                semArr.add(new WriterReaderLock());
            }
        }
    }

    public void insert(int x) throws InterruptedException{
        // acquire the array semaphore to start inserting, otherwise pending
        arrLockSem.acquire();
        
        // acquire the insertDeleteLock to modify the array
        insertDeletelockSem.acquire();

        // case 1: the array is empty
        if (numV == 0) {
            arr.add(x);
            lastEleIndex = 0;
            numV++;
            insertDeletelockSem.release();
            return;
        }

        int index = binarySearch(x);
        signalFinishReading(index, index);

        if(index > lastEleIndex && lastEleIndex < N - 1) {
            arr.add(x);
            lastEleIndex++;
            numV++;
            insertDeletelockSem.release();
            return;
        }

        // case 2: duplication
        if (arr.get(index) == x) {
            arrLockSem.release();
            insertDeletelockSem.release();
            return;
        }
        
        int emptyIndex = findNearestEmpty(index);
        
        // case 3: shift to nearest empty cell
        if (emptyIndex != -1) {
            // do the shift
            if (arr.get(index) < x && emptyIndex > index) {
                index++; 
            } else if (arr.get(index) > x && emptyIndex < index) {
                index--;
            }
            if (index >= emptyIndex) {
                signalWantWriting(emptyIndex, index);
            } else {
                signalWantWriting(index, emptyIndex);
            }
            shift(emptyIndex, index);
            arr.set(index, x);
            numV++;
            if (index >= emptyIndex) {
                signalFinishWriting(emptyIndex, index);
            } else {
                signalFinishWriting(index, emptyIndex);
            }
            insertDeletelockSem.release();
            return;
        }

        // case 4: normal shift
        arr.add(-1);
        lastEleIndex++;

        // do the shift
        signalWantWriting(index, lastEleIndex);
        shift(lastEleIndex ,index);
        arr.set(index, x);
        numV++;
        signalFinishWriting(index, lastEleIndex);
        insertDeletelockSem.release();
    }

    public void delete(int x) throws InterruptedException  {
        // acquire the insertDeltelock to modify the array
        insertDeletelockSem.acquire();

        int result = this.binarySearch(x);
        signalFinishReading(result, result);
        if (arr.get(result) == x) {
            arr.set(result, -1);
            numV--;
        }
        
        // check if a compact is needed
        if (arr.size()/numV > 1) {
            compact();
        } else {
            // if not compact, release the array lock once
            arrLockSem.release();
        }
        
        // after deletion, release the writer lock
        insertDeletelockSem.release();
    }

    

    public boolean member(int x) throws InterruptedException {
        int result = this.binarySearch(x);
    
        if (result > lastEleIndex || result < 0) {
            signalFinishReading(result, result);
            return false;
        }

        if (arr.get(result) != x) {
            signalFinishReading(result, result);
            return false;
        }
        signalFinishReading(result, result);
        return true;
    }

    public void print_sorted() throws InterruptedException {
        signalWantReading(0, lastEleIndex);
        String result = "";
        result = result + "{";
        int index = 0;
        for (int ele : arr) {
            if (ele == -1) {
                continue;
            }
            result = result + Integer.toString(ele);
            if (index < lastEleIndex) {
                result = result + ", ";
            }
            signalFinishReading(index, index);
            index++;
        }
        result = result + "}";
        System.out.println(result);
    }


    /*
     *  helper functions
     */
    
    // shift the array elements from fromIndex to emptyIndex
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
    public void compact() throws InterruptedException {
        // arr is null, do nothing
        if (numV == 0) {
            return;
        }
        if (numV == 1 && arr.get(0) != -1) {
            return;
        }
        // signal this thread wants to modify whole array
        signalWantWriting(0, lastEleIndex);
        
        int temp_last = lastEleIndex;
        arr.removeIf(n -> (n == -1));
        lastEleIndex = arr.size() - 1;

        // release arrLockSem the amount of time equal to the empty element got deleted
        for (int i = temp_last; i > lastEleIndex; i--) {
            arrLockSem.release();
        }

        // signal writing finishes
        signalFinishWriting(0, temp_last);
        
    }

    // a binary search algo that adapted to search for an element in an array that has empty value indexes.
    private int binarySearch(int tar) throws InterruptedException {
        if (numV == 0)
            return 0;

        int left = 0;
        int right = lastEleIndex;
        signalWantReading(left, right);
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
                signalFinishReading(left, mid - 1);
                signalFinishReading(mid + 1, right);
                return mid;
            } else if (tar < arr.get(mid)) {
                int old_right = right;
                right = mid - 1;
                signalFinishReading(mid, old_right);
            } else {
                int old_left = left;
                left = mid + 1;
                signalFinishReading(old_left, mid);
            }
        }

        return left < N - 1 ? left : N - 1;
    }

    // signal a writer wants to write from fromIndex to toIndex
    private void signalWantWriting(int fromIndex, int toIndex) throws InterruptedException {
        for(int i = fromIndex; i <= toIndex; i++) {
            semArr.get(i).startWriting();
        }
    }

    // signal a writer finshed writing from fromIndex to toIndex
    private void signalFinishWriting(int fromIndex, int toIndex) throws InterruptedException {
        for(int i = fromIndex; i <= toIndex; i++) {
            semArr.get(i).finishWriting();
        }
    }

    // signal a reader wants to read from fromIndex to toIndex
    private void signalWantReading(int fromIndex, int toIndex) throws InterruptedException {
        for(int i = fromIndex; i <= toIndex; i++) {
            semArr.get(i).startReading();
        }
    }

    // signal a reader finshed writing from fromIndex to toIndex
    private void signalFinishReading(int fromIndex, int toIndex) throws InterruptedException {
        for(int i = fromIndex; i <= toIndex; i++) {
            semArr.get(i).finishReading();
        }
    }

}