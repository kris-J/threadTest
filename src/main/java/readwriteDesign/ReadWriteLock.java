package readwriteDesign;

/**
 * @author fangjie
 * @Description: 读写锁，有写则必须串行
 * @date 2019/12/4 16:20
 */
public class ReadWriteLock {

    //在读的readers
    private int readingReaders = 0;
    //在等待的readers
    private int waitingReaders = 0;
    //在写的writers
    private int writingWriters = 0;
    //在等待的writers
    private int waitingWriters = 0;
    //writer优先
    private boolean preferWriter = true;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders++;
        try {
            //有正在写的writers，则等待。如果writer优先并且有等待writer，则等待
            while (this.writingWriters > 0 || (preferWriter && waitingWriters > 0)) {
                this.wait();
            }
            this.readingReaders++;
        } finally {
            this.waitingReaders--;
        }
    }

    public synchronized void readUnlock() {
        this.readingReaders--;
        this.notifyAll();
    }


    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters++;
        try {
            while (readingReaders > 0 || writingWriters > 0) {
                this.wait();
            }

            this.writingWriters++;
        } finally {
            this.waitingWriters--;
        }
    }


    public synchronized void writeUnlock() {
        this.writingWriters--;
        this.notifyAll();
    }

}
