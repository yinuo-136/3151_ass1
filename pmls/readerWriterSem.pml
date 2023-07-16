mtype = { READ, WRITE }

chan writeSem = [1] of { bit };
chan readSem = [1] of { bit };
chan endRead = [1] of { bit };
chan beginWrite = [1] of { mtype };

int readerCount = 0;
bit writeLock = 0;

inline StartRead() {
    atomic { readSem?0; readerCount++; readSem!0; }
}

inline FinishRead() {
    atomic { endRead?0; readerCount--; if :: (readerCount == 0 && writeLock == 1) -> beginWrite!WRITE; :: else -> skip; fi; endRead!0; }
}

inline StartWrite() {
    atomic { writeSem?0; readSem?0; if :: (readerCount > 0) -> writeLock = 1; beginWrite?WRITE; :: else -> skip; fi; }
}

inline FinishWriting() {
    atomic { writeLock = 0; writeSem!0; readSem!0; }
}

active[2] proctype ReadABlock() {
    StartRead();
csp1:printf("read critical\n");
    FinishRead();
}

active[3] proctype WriteABlock() {
    StartWrite();
csp2:printf("write critical\n");
    FinishWriting();
}

#define csr0	ReadABlock[0]@csp1
#define csr1	ReadABlock[1]@csp1
#define csw0	WriteABlock[0]@csp2
#define csw1	WriteABlock[1]@csp2
#define csw2	WriteABlock[2]@csp2

// this ltl check that the critical section of ReadBlock() can be executed simultaneously by multiple ReadABlock process.
ltl readmutex { (csr0 && csr1) || (!csr0 && csr1) || (csr0 && !csr1) || (!csr0 && !csr1) }
// this ltl check that the critical section of WriteBlock() can not be executed simultaneously by multiple WriteABlock process.
ltl writemutex { !<>(csw0 && csw1 && csw2) }

// this ltl check that the critical section of WriteBlock() and ReadABlock() can not be executed simultaneously
ltl readAndwritemutex { !<>(csr0 && csw0)}