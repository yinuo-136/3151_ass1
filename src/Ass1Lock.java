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
        readSem.acquire();
        readerCount++;
        readSem.release();
    }

    public void finishReading() throws InterruptedException {
        endRead.acquire();
        readerCount--;
        
        if (readerCount == 0 && writeLock == true) {
            beginWrite.release();
        }
        endRead.release();
    }
    
    public void startWriting() throws InterruptedException {
        writeSem.acquire();
        readSem.acquire();
        if (readerCount > 0) {
            writeLock = true;
            beginWrite.acquire();
        }
    }
    
    public void finishWriting() {
        writeLock = false;
        writeSem.release();
        readSem.release();
    }

}


