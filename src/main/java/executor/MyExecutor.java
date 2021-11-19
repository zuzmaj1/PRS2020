package executor;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.sun.javafx.scene.control.skin.TableCellSkin;

/**
 * Executor który uruchamia zadania każdego typu na osobnym wątku w kolejności przyjścia
 */
public class MyExecutor implements Executor {

    private Runnable active = null;
    private ReentrantLock lock;
    // Condition pozwala nam wykonywac await i signal
    private Condition lockObjectCondition;
    private Map<String, Queue<RunnableWithType>> inputQueues;
    // ThreadPool
    private Set<Thread> threads;

    public MyExecutor() {
        // this setup need to be generalized so that we can parametrized it
        this.lock = new ReentrantLock();
        this.lockObjectCondition = lock.newCondition();
        this.inputQueues = new HashMap<String, Queue<RunnableWithType>>();
        inputQueues.put(Type.A.name(), new LinkedList<>());
        inputQueues.put(Type.B.name(), new LinkedList<>());
        inputQueues.put(Type.C.name(), new LinkedList<>());
        this.threads = new HashSet<>();
        MyWorker worker = new MyWorker(1, Type.A.name());
        MyWorker worker2 = new MyWorker(2, Type.B.name());
        MyWorker worker3 = new MyWorker(3, Type.C.name());
        Thread thread = Executors.defaultThreadFactory().newThread(worker);
        thread.start();
        Thread thread2 = Executors.defaultThreadFactory().newThread(worker2);
        thread2.start();
        Thread thread3 = Executors.defaultThreadFactory().newThread(worker3);
        thread3.start();
        worker.setRunning(true);
        worker2.setRunning(true);
        worker3.setRunning(true);
    }

    @Override
    public void execute(Runnable task) {
        MyTask myTask = (MyTask) task;
        lock.lock();
        try {
            inputQueues.get(myTask.getType()).add(myTask);
            lockObjectCondition.signalAll();
        } catch (NullPointerException aE) {
            System.err.println("Error");
        } finally {
            lock.unlock();
        }
    }

    // Klasa prywatna gdyż chcemy móc odnosić się do lockow zdefiniowanych w naszym executorze
    private class MyWorker implements Runnable
    {
        private long workerId;
        private String type;

        private final AtomicBoolean running = new AtomicBoolean( false );

        public MyWorker( Integer workerId, String type)
        {
            this.workerId = workerId;
            this.running.set( false );
            this.type = type;
        }

        private boolean isRunning()
        {
            return running.get();
        }

        private void setRunning( boolean aRunning )
        {
            running.set( aRunning );
        }

        public void shutdown()
        {
            //Todo
        }

        @Override
        public void run()
        {
            while( running.get() )
            {
                try {
                    while (running.get()) {
                        RunnableWithType task = null;
                        lock.lock();
                        try {
                            if(!inputQueues.get(type).isEmpty())
                            {
                               task = inputQueues.get(type).poll();
                            }
                            else
                            {
                                lockObjectCondition.await();
                            }
                        } catch (Exception e) {
                            System.out.println("worker thread interrupted");
                            break;
                        } finally {
                            lock.unlock();
                        }
                        if(task != null) {
                            task.run();
                        }
                    }
                }
                catch( Exception aE )
                {
                    System.err.println(aE.getMessage() + "exception caught on worker thread" );
                }
            }
        }
    }
}
