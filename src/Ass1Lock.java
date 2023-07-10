package src;

import java.util.concurrent.Semaphore;

public class Ass1Lock {

    private Semaphore writeSem = new Semaphore(1, true);
    private Semaphore readSem = new Semaphore(1, true);
    private Semaphore endRead = new Semaphore(1, true);
    private Semaphore beginWrite = new Semaphore(0, true);
    public int readerCount = 0;
    public boolean writeLock = false;

    public void startReading() throws InterruptedException {
        // Acquire a permit from the reader semaphore
        readSem.acquire();

        // Increase the active readers count
        readerCount++;

        // Release the reader semaphore
        readSem.release();

        // readSem.hasQueuedThreads()
    }

    public void finishReading() throws InterruptedException {
        // Acquire a permit from the reader semaphore
        endRead.acquire();

        // Decrease the active readers count
        readerCount--;
        // System.out.println("reader count33: " + this.readerCount);
        // System.out.println("writer lock34: " + this.writeLock);

        if (readerCount == 0 && writeLock == true) {
            System.out.println("reader count35: " + this.readerCount);
            System.out.println("writer lock36: " + this.writeLock);
            System.out.println("release!!!!!!!!!!!!!!!!!");
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
        System.out.println("=========in start writing==========");
        System.out.println("reader count: " + this.readerCount);
        System.out.println("writer lock: " + this.writeLock);
        System.out.println("=========in start writing==========");
        // Wait until all active readers finish writing
        if (readerCount > 0) {
            writeLock = true;
            System.out.println("=========inside if==========");
            System.out.println("reader count55: " + this.readerCount);
            System.out.println("writer lock56: " + this.writeLock);
            System.out.println("beginWrite56: " + this.beginWrite);
            beginWrite.acquire();
        }
        System.out.println("=========end start writing==========");
    }

    public void finishWriting() {
        System.out.println("================finish writing=========");
        writeLock = false;
        // Release the writer semaphore
        writeSem.release();
        readSem.release();
    }

    public static void main(String[] args) throws InterruptedException {
        Ass1Lock SemLock = new Ass1Lock();

        // DEADLOCK COMBINATION
        SemLock.startReading();
        SemLock.startWriting();
        SemLock.finishReading();

        System.out.println("finish");
    }
}
