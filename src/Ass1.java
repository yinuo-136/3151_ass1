package src;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * ass1
 */
public class Ass1 {

    // size of bounded arr
    int N;
    // num of ele
    int numV;
    // last ele index
    int lastEleIndex;
    //
    public static ArrayList<Integer> arr = new ArrayList<Integer>();
    public static ArrayList<Ass1Lock> semArr =  new ArrayList<Ass1Lock>();
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
        //init lock list
        for (int i = 0; i < N; i++) {
            Ass1Lock newLock = new Ass1Lock();
            semArr.add(newLock);
        }
        System.out.println("before start");
        print_Sem_arr();
    }

    public void insert(int x) throws InterruptedException {
        // binaryS(x) ->index
        // findNearestNull(index) -> index2
        // x <> get(index)

        // list is full no empty element
        if (this.numV == N)
            return;
        int insertIndex = this.binarySearch(x);
        // System.out.println("========================before FINISH reading=========================" + "\nIDX:" + insertIndex );
        // print_Sem_arr();
        this.semArr.get(insertIndex).finishReading();
        // System.out.println("========================after FINISH reading=========================" + "\nIDX:" + insertIndex );
        // print_Sem_arr();


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

    public void delete(int x) throws InterruptedException {
        //if there's no element in the list, skip(currently)
        // System.out.println("========================before read IN DELETE=========================" + " IDX:" );
        // print_Sem_arr();
        int resultIndex = this.binarySearch(x);
        //this.semArr.get(resultIndex).startWriting(); --deadlock
        // System.out.println("========================before finish read IN DELETE=========================" + " IDX:"  + resultIndex);
        // print_Sem_arr();
        this.semArr.get(resultIndex).finishReading();
        //System.out.println("========================finish read=========================");
        //other thread may affect the content on resultIndex --PROBLEM

        if (numV == 0) return;
        System.out.println("========================before write=========================" + " IDX:"  + resultIndex);
        print_Sem_arr();
        this.semArr.get(resultIndex).startWriting();
        System.out.println("========================start write=========================" + " IDX:" + resultIndex);
        
        if (this.lastEleIndex < resultIndex) {
            //only happen in concurrency
            this.semArr.get(resultIndex).finishWriting();
            return;
        }
        
        if (this.arr.get(resultIndex) == x) {
            this.arr.set(resultIndex, -1);
            this.numV--;
            if (resultIndex == lastEleIndex) {
                startWritingBlocks(0, resultIndex - 1);  
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
                //this.lastEleIndex = temp;
                endWritingBlocks(0, resultIndex - 1);
                this.lastEleIndex = temp;
            }

        }
        this.semArr.get(resultIndex).finishWriting();
        System.out.println("========================finish write=========================");
    }


    

    // test.arr.add(-1);0
    // test.arr.add(4);1
    // test.arr.add(-1);2
    // test.arr.add(8);3
    // test.arr.add(-1);4
    // test.arr.add(10);5
    public int binarySearch(int tar) throws InterruptedException {

        if (numV == 0) {
            this.semArr.get(0).startReading();
            return 0;
        }
        
        int left = 0;
        int right = this.lastEleIndex;
        while (left <= right) {
            
            int mid = left + ((right - left) >> 1);
            System.out.println(mid + " Start read");
            this.semArr.get(mid).startReading();
            while ( this.arr.get(mid) == -1) {
                //System.out.println("IMHERE---------------");
                int oldMid = mid; // 0
                mid++; // 1
                if (mid > right) {
                    right = left + ((right - left) >> 1) - 1;
                    mid = left + ((right - left) >> 1);
                } 
                if (mid < 0) {
                    System.out.println("origin: mid = " + mid);
                    return 0; // in case mid goes out of left bound
                }
                System.out.println(oldMid + "o finish read");
                this.semArr.get(oldMid).finishReading();        // -- PROBLEM HERE
                System.out.println(mid + "o start read");
                this.semArr.get(mid).startReading();
            }

            if (this.arr.get(mid) == tar) {
                return mid;
            } else if (tar < this.arr.get(mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
            // return before the loop finish so that we don't finish reading 
            if (left > right) {
                System.out.println("left :" + Integer.toString(left));
                System.out.println("right :" + Integer.toString(right));
                System.out.println(mid + " goes out, wait for finish");
                this.semArr.get(mid).finishReading();
                this.semArr.get(left).startReading();
                return left;
            }
            System.out.println(mid + " finish");
            this.semArr.get(mid).finishReading(); // -- PROBLEM HERE
        }
        // System.out.println("left :" + Integer.toString(left));
        // System.out.println("right :" + Integer.toString(right));
        this.semArr.get(left).startReading();
        return left;
    }

    //start reading the arr from index start to the end
    private void startReadingBlocks(int start, int end) throws InterruptedException {
        for (int i = start; i <= end; i++) {
            this.semArr.get(i).startReading();
        }
    }

    private void endReadingBlocks(int start, int end) throws InterruptedException {
        for (int i = start; i <= end; i++) {
            this.semArr.get(i).finishReading();
        }
    }

    private void startWritingBlocks(int start, int end) throws InterruptedException {
        for (int i = start; i <= end; i++) {
            this.semArr.get(i).startWriting();
        }
    }

    private void endWritingBlocks(int start, int end) throws InterruptedException {
        for (int i = start; i <= end; i++) {
            this.semArr.get(i).finishWriting();
        }
    }

    public boolean member(int x) throws InterruptedException {
        // System.out.println("========================before read IN MEMBER=========================" + " IDX:" );
        // print_Sem_arr();
        int result = this.binarySearch(x);
        // System.out.println("========================before finish read IN MEMBER=========================" + " IDX:"  + result);
        // print_Sem_arr();
        //print_Sem_arr();

        // System.out.println(result);
        if (result > lastEleIndex) {
            //System.out.println(result + " left eventually finish");
            this.semArr.get(result).finishReading();
            //print_Sem_arr();
            return false;
        }
        
        if (this.arr.get(result) != x) {
            //System.out.println(result + " left eventually finish");
            this.semArr.get(result).finishReading();
            //print_Sem_arr();
            return false;
        }
        //System.out.println(result + " left eventually finish");
        this.semArr.get(result).finishReading();
        //print_Sem_arr();
        return true;

    }

    public synchronized void print_Sem_arr() {
        int i = 0;
        for (Ass1Lock l : semArr) {
            System.out.println("index: " + i + " readerCount: " + l.readerCount + " writeLock: " + l.writeLock);
            i++;
        }
    }

    public void print_sorted() throws InterruptedException {
        startReadingBlocks(0, this.lastEleIndex);
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
        endReadingBlocks(0, this.lastEleIndex);
    }

}