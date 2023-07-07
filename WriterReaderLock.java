import java.util.concurrent.Semaphore;

public class WriterReaderLock {
    private Semaphore writerSemaphore = new Semaphore(1);
    private Semaphore readerSemaphore = new Semaphore(1, true);
    private int activeReaders = 0;
    
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
        readerSemaphore.acquire();
        
        // Decrease the active readers count
        activeReaders--;
        
        // Release the reader semaphore
        readerSemaphore.release();
    }
    
    public void startWriting() throws InterruptedException {
        // Acquire a permit from the writer semaphore
        writerSemaphore.acquire();

        // Acquire a reader semaphore
        readerSemaphore.acquire();
        
        // Wait until all active readers finish writing
        while (activeReaders > 0) {
            Thread.sleep(100); // Sleep for a short duration
        }
    }
    
    public void finishWriting() {
        // Release the writer semaphore
        writerSemaphore.release();
        readerSemaphore.release();
    }
}






 
