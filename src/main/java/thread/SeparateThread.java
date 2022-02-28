package thread;

public class SeparateThread {

    public static void main(String[] args) {

        Concurrency thread1 = new Concurrency(1); // create an object of class Concurrent which is a thread
        thread1.start();

        Concurrency thread2 = new Concurrency(2); // create an object of class Concurrent which is a thread
        thread2.start();
    }

    static class Concurrency extends Thread { // this class is a thread in Java, elements in run method will be run on separate Thread

        private int loopNum;

        Concurrency(int loopNum) {
            this.loopNum = loopNum;
        }

        @Override // override method from superclass
        public void run() {

            for (int i = 1; i <= 5; i++) {
                System.out.println("Loop " + this.loopNum + ", Iteration: " + i);
            }
        }
    }
}


