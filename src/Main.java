import java.io.IOException;
import java.util.logging.*;


public class Main {
    static Logger logger;

    public static void main(String[] args) {
        ConsoleHandler handler = new ConsoleHandler();
        FileHandler fileHandler = null;

        try {
            fileHandler = new FileHandler("log.txt");
//            SimpleFormatter formatter=new SimpleFormatter();
//            fileHandler.setFormatter(formatter);
            fileHandler.setFormatter(new MySimpleFormat());
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger = Logger.getLogger(Main.class.getName());
        logger.addHandler(handler);
        logger.addHandler(fileHandler);

        if (fileHandler != null) {
            fileHandler.setLevel(Level.ALL);
        }
        handler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);

        Thread thread1 = new Thread(new MyRunnanble("thread1"));
        Thread thread2 = new Thread(new MyThread("thread2"));

        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        do {
            logger.finest("thread1 is Alive : " + thread1.isAlive());
            logger.finest("thread2 is Alive : " + thread2.isAlive());
        } while (thread1.isAlive() || thread2.isAlive());

        logger.log(Level.INFO, "hi for tetsing result");
    }

    static class MyThread extends Thread {
        String threadName;

        public MyThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                logger.log(Level.INFO, " i : " + i + " from " + threadName);
//                logger.finest(" i : "+ i + " from "+ threadName);

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class MyRunnanble implements Runnable {
        String threadName;

        public MyRunnanble(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                logger.log(Level.INFO, " i : " + i + " from " + threadName);
//                logger.finest(" i : "+ i + " from "+ threadName);

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class MySimpleFormat extends Formatter {

        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n" + record.getLevel() + "\n" + record.getSourceClassName() + "\n\n";
        }
    }

}