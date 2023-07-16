package src;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

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

    public ArrayList<Integer> arr = new ArrayList<Integer>();
    public ArrayList<Ass1Lock> semArr =  new ArrayList<Ass1Lock>();

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
    }

    public void insert(int x) throws InterruptedException {
        // list is full no empty element
        if (this.numV == N)
            return;
        int insertIndex = this.binarySearch(x);
        this.semArr.get(insertIndex).finishReading();

        this.semArr.get(insertIndex).startWriting();
        if (insertIndex == lastEleIndex + 1) {
            //do nothing
        } else {
            
            if (this.arr.get(insertIndex) == x) {
                // element already exsit, do not insert
                this.semArr.get(insertIndex).finishWriting();
                return;
            }
        }


        // x is the biggest element, append to the last position
        if (insertIndex > this.lastEleIndex) {
            if (!this.arr.contains(-1)) {
                // if everything before insertIndex has no -1, we add the element
                this.numV++;
                this.arr.add(x);
                this.lastEleIndex++;
                this.semArr.get(insertIndex).finishWriting();
                return;
            } else {
                // there's -1 before insertIndex, we insert at the lastIndex
                this.semArr.get(insertIndex).finishWriting();
                insertIndex = this.lastEleIndex;
                this.semArr.get(insertIndex).startWriting();
            }
        }

        // insert the element between the current list
        // already lock from the nullIndex to insert Index
        int nullIndex = this.findNearestNull(insertIndex);

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
                //insert
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
                //insert
                this.arr.set(insertIndex + 1, x);
            }
            //release lock
            endWritingBlocks(insertIndex, nullIndex);
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
            endWritingBlocks(nullIndex, insertIndex);
        }
        this.numV++;
        return;

    }


    public void compress() throws InterruptedException {
        // arr is null, do nothing
        if (this.numV == 0) {
            return;
        }
        this.semArr.get(0).startReading();
        if (this.numV == 1 && this.arr.get(0) != -1) {
            this.semArr.get(0).finishReading();
            return;
        }
        this.semArr.get(0).finishReading();

        startWritingBlocks(0, lastEleIndex);
        this.arr.removeIf(n -> (n == -1));
        int oldLastEleIndex = lastEleIndex;
        this.lastEleIndex = this.arr.size() - 1;
        endWritingBlocks(0, oldLastEleIndex);

    }

    private int findNearestNull(int index) throws InterruptedException {
        // list is full, return -1.
        if (this.numV == N)
            return -1;

        int i = index;
        int j = index;
        
        boolean nullflag = true;

        // i--;
        // j++;
        while (nullflag) {
            if (i != index) this.semArr.get(i).startWriting();
            if (j != index) this.semArr.get(j).startWriting();

            nullflag = this.arr.get(i) != -1 && this.arr.get(j) != -1;
            if (!nullflag) {
                break;
            }
            if (i > 0) {
                i--; 
            }
            if (j < this.lastEleIndex) {
                j++;
            }

            if (j == this.lastEleIndex) {
                if (j != index) this.semArr.get(j).startWriting();
                if (this.arr.get(j) != -1) {
                    if (i != index) this.semArr.get(i).startWriting();
                    if (this.arr.get(i) != -1) {
                        //left unlock
                        if (index > 0) {
                            endWritingBlocks(i, index - 1);
                        }

                        
                        //right keep lock [index,j+1]
                        this.semArr.get(j+1).startWriting();
                        return j + 1;
                    }
                    //right unlock
                    //left keep

                    if (i != index) this.semArr.get(i).finishWriting();
                    
                }
                if (j != index) this.semArr.get(j).finishWriting();
            }
        }
        if (this.arr.get(i) == -1 && this.arr.get(j) == -1) {
            // back and front both have null
            if (index - i <= j - index) {
                if (index != lastEleIndex) endWritingBlocks(index + 1, j);
                return i;
            } else {
                if (index > 0) endWritingBlocks(i, index - 1);
                return j;
            }

        } else if (this.arr.get(i) == -1 && this.arr.get(j) != -1) {
            // front has null
            if (index != lastEleIndex) endWritingBlocks(index + 1, j);
            return i;
        } else {
            // back has null
            if (index > 0) endWritingBlocks(i, index - 1);
            return j;
        }

    }

    public void delete(int x) throws InterruptedException {

        int resultIndex = this.binarySearch(x);

        this.semArr.get(resultIndex).finishReading();

        if (numV == 0) return;
        this.semArr.get(resultIndex).startWriting();
        
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
                endWritingBlocks(0, resultIndex - 1);
                this.lastEleIndex = temp;
            }

        }
        this.semArr.get(resultIndex).finishWriting();
    }

    public int binarySearch(int tar) throws InterruptedException {

        if (numV == 0) {
            this.semArr.get(0).startReading();
            return 0;
        }
        
        int left = 0;
        int right = this.lastEleIndex;
        while (left <= right) {
            
            int mid = left + ((right - left) >> 1);
            this.semArr.get(mid).startReading();
            while ( this.arr.get(mid) == -1) {
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
                this.semArr.get(oldMid).finishReading();    
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
                this.semArr.get(mid).finishReading();
                if (left > lastEleIndex) {
                    //add for concurrency
                    left = lastEleIndex;
                }
                this.semArr.get(left).startReading();
                return left;
            }
            this.semArr.get(mid).finishReading(); // -- PROBLEM HERE
        }
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
        int result = this.binarySearch(x);

        if (result > lastEleIndex) {
            this.semArr.get(result).finishReading();
            //print_Sem_arr();
            return false;
        }
        
        if (this.arr.get(result) != x) {
            this.semArr.get(result).finishReading();
            return false;
        }
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
        int i = 0;
        result = result + "[";
        for (int ele : this.arr) {
            if (ele == -1) {
                i++;
                continue;
            }
            if (i == this.lastEleIndex) {
                result = result + Integer.toString(ele);
            } else {
                result = result + Integer.toString(ele) + ", ";
            }
           
            i++;
        }
        result = result + "]";
        System.out.println(result);
        endReadingBlocks(0, this.lastEleIndex);
    }

}