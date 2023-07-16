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
        // Acquire a permit from the reader semaphore
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

}


