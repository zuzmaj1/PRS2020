package logger;

import org.apache.log4j.Logger;

public class TwoThreadsLogs {

    public static Logger log = Logger.getLogger(TwoThreadsLogs.class);
    public static Integer i = 0;

    public static void main(String[] args) {

        Concurrency thread1 = new Concurrency(1, log); // create an object of class Concurrent which is a thread
        Concurrency thread2 = new Concurrency(2, log);
        Concurrency thread3 = new Concurrency(3, log);
        Concurrency thread4 = new Concurrency(4, log);
        Concurrency thread5 = new Concurrency(5, log);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }

    static class Concurrency extends Thread { // this class is a thread in Java, elements in run method will be run on separate Thread

        private int loopNum;
        private Logger log;

        Concurrency(int loopNum, Logger log) {
            this.log = log;
            this.loopNum = loopNum;
        }

        @Override // override method from superclass
        public void run() {
            while (i <= 500) {
                log.info("Loop " + this.loopNum + ", Read: " + i);
                i = i + 1;
                log.info("Loop " + this.loopNum + ", Write: " + i);
            }
        }
    }

}

