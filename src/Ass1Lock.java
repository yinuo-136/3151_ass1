package src;
import java.util.concurrent.Semaphore;

public class Ass1Lock {
    
    public Semaphore writeSem = new Semaphore(1, true);
    public Semaphore readSem = new Semaphore(1, true);
    public Semaphore endRead = new Semaphore(1, true);
    public Semaphore beginWrite = new Semaphore(0, true);
    public int readerCount = 0;
    public boolean writeLock = false;
    
    public void startReading() throws InterruptedException {
        // Acquire a permit from the reader semaphor
        readSem.acquire();
        
        // Increase the active readers count
        readerCount++;
        
        // Release the reader semaphore
        readSem.release();

        //readSem.hasQueuedThreads()
    }

    public void finishReading() throws InterruptedException {
        // Acquire a permit from the reader semaphore
        endRead.acquire();
        
        // Decrease the active readers count
        readerCount--;
        
        if (readerCount == 0 && writeLock == true) {
            System.out.println("=============release first write===============");
            beginWrite.release();
        }
        // Release the reader semaphore
        endRead.release();
    }
    
    public void startWriting() throws InterruptedException {
        // Acquire a permit from the writer semaphore
        writeSem.acquire();

        // Acquire a reader semaphore
        readSem.acquire();
        
        // Wait until all active readers finish 
        if (readerCount > 0) {
            writeLock = true;
            beginWrite.acquire();
        }
    }
    
    public void finishWriting() {
        writeLock = false;
        // Release the writer semaphore
        writeSem.release();
        readSem.release();
    }

    public void fRsW() throws InterruptedException {
        //=================FINISH READING
        // Acquire a permit from the reader semaphore
        endRead.acquire();
        
        // Decrease the active readers count
        readerCount--;
        
        if (readerCount == 0 && writeLock == true) {
            System.out.println("=============release first write===============");
            beginWrite.release();
        }
        // Release the reader semaphore
        endRead.release();
        
        //===============START WRITING 
        // Acquire a permit from the writer semaphore
        writeSem.acquire();

        // Acquire a reader semaphore
        readSem.acquire();
        
        // Wait until all active readers finish 
        if (readerCount > 0) {
            writeLock = true;
            beginWrite.acquire();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Ass1Lock SemLock = new Ass1Lock();

        //DEADLOCK COMBINATION
        SemLock.startReading();
        SemLock.startWriting();
        SemLock.finishReading();


        //DEADLOCK COMBINATION
        // SemLock.startReading();
        // SemLock.finishReading();
        // SemLock.startWriting();
        

        
        System.out.println("finish");
    }
}

// public class Ass1Lock {
    
//     private Semaphore writeSem = new Semaphore(0, true);
//     private Semaphore readSem = new Semaphore(1, true);

//     private int readerCount = 0;
//     private int writeCount = 0;
    
//     public void startReading() throws InterruptedException {
//         if (this.writeCount != 0 || this.writeSem.hasQueuedThreads()) {
//             this.readSem.acquire();
//         }
//         this.readerCount++;
//         this.readSem.release();
//     }

//     public void finishReading() throws InterruptedException {
//         this.readerCount--;
//         if (this.readerCount == 0) {
//             this.writeSem.release();
//         }
//     }
    
//     public void startWriting() throws InterruptedException {
//         if (this.writeCount != 0 || readerCount != 0) {
//             this.writeSem.acquire();
//         }
//         writeCount++;
//     }
    
//     public void finishWriting() {
//         this.writeCount--;
//         if (!this.readSem.hasQueuedThreads()) {
//             this.writeSem.release();
//         } else {
//             this.readSem.release();
//         }
//     }


//     public static void main(String[] args) throws InterruptedException {
//         Ass1Lock SemLock = new Ass1Lock();

//         //DEADLOCK COMBINATION
//         SemLock.startReading();
//         SemLock.startWriting();
//         SemLock.finishReading();


//         //DEADLOCK COMBINATION
//         // SemLock.startReading();
//         // SemLock.finishReading();
//         // SemLock.startWriting();
        

        
//         System.out.println("finish");
//     }
// }
