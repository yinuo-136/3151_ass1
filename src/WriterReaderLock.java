package src;
import java.util.concurrent.Semaphore;

public class WriterReaderLock {
    private Semaphore writerSemaphore = new Semaphore(1, true);
    private Semaphore readerSemaphore = new Semaphore(1, true);
    private Semaphore finishReadSem = new Semaphore(1, true);
    private Semaphore startwriteSem = new Semaphore(0, true);
    private int activeReaders = 0;
    private boolean isWantToWrite = false;
    
    public void startReading() throws InterruptedException {
        // Acquire a permit from the reader semaphore
        readerSemaphore.acquire();
        
        // Increase the active readers count
        activeReaders++;
        
        // Release the reader semaphore
        readerSemaphore.release();
    }
    
    public void finishReading() throws InterruptedException {
        // Acquire a permit from the reader semaphore
        finishReadSem.acquire();
        
        // Decrease the active readers count
        activeReaders--;
        
        if (activeReaders == 0 && isWantToWrite == true) {
            startwriteSem.release();
        }
        // Release the reader semaphore
        finishReadSem.release();
    }
    
    public void startWriting() throws InterruptedException {
        // Acquire a permit from the writer semaphore
        writerSemaphore.acquire();

        // Acquire a reader semaphore
        readerSemaphore.acquire();
        
        // Wait until all active readers finish writing
        if (activeReaders > 0) {
            isWantToWrite = true;
            startwriteSem.acquire();
        }
    }
    
    public void finishWriting() {
        isWantToWrite = false;
        // Release the writer semaphore
        writerSemaphore.release();
        readerSemaphore.release();
    }
}
