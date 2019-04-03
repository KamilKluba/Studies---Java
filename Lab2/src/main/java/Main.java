import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class Main{
    private int knapsackCapacity;
    private int repetitions;
    private ArrayList<Thread> listOfThreads;
    private int numberOfThreads;
    private Map<Long, Object> mapOfResults;
    private Random random;
    private long seed;
    private KnapsackClassLoader kcl;
    private File file;
    private URL[] urlsToLoadFrom;
    private URLClassLoader loader;
    private ArrayList<Class> classes;
    private Class classItem;
    private Class classKnapsack;
    private ArrayList<Object> listOfItems;
    private boolean kill;

    public Main() throws Exception {
        knapsackCapacity = 1000;
        repetitions = 1000;
        kill = false;

        setClasses();
        generateItems();

//        for(Class c : classes)
//            System.out.println(c);

//        Class c = classes.get(0);
//        Object o = c.getConstructors()[0].newInstance(listOfItems, 1000);
////        Method m = c.getMethod("solve");
////        m.invoke(c);
//        Method m2 = c.getMethod("description");
//        System.out.println(m2.invoke(o));

        runThreads();
    }

    private void generateItems(){
        listOfItems = new ArrayList<Object>();
        try {
            Random random = new Random();

            for (int i = 0; i < 24; i++)
                if (listOfItems.size() < 32) {
                    Object o = classItem.getConstructors()[0].newInstance(("item" + (listOfItems.size() + 1)), (Math.abs(random.nextInt() % 1000)), (random.nextDouble() * 1000 + random.nextDouble()));
                    listOfItems.add(o);
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setClasses(){
        try {
            URL url = new URL("file:D:\\Programy\\Eclipse\\Workspace\\javazaawansowana.lab1\\target\\classes\\");
            urlsToLoadFrom = new URL[]{url};
            loader = new URLClassLoader(urlsToLoadFrom);
            classes = new ArrayList<Class>();

            File dir = new File("D:\\Programy\\Eclipse\\Workspace\\javazaawansowana.lab1\\target\\classes\\javazaawansowana\\lab1");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    String childName = child.getName();
                    String className = childName.replace(".class", "");
                    String classPackage = "javazaawansowana.lab1.";
                    String fullClassName = classPackage.concat(className);
                    Class c = loader.loadClass(fullClassName);
                    Method[] methods = c.getMethods();

                    for (Method m : methods) {
                        if (m.getName().equals("solve") && !c.isInterface()) {
                            classes.add(c);
                            break;
                        }
                        if (m.getName().equals("getItemValue")) {
                            classItem = c;
                            break;
                        }
                        if (m.getName().equals("getMaxCapacity")) {
                            classKnapsack = c;
                            break;
                        }
                    }
                }

                System.out.println("Załadowane klasy:");
                for(Class c : classes)
                    System.out.println(c.getName());
                if(classItem != null)
                    System.out.println(classItem.getName());
                if(classKnapsack != null)
                    System.out.println(classKnapsack.getName());
                System.out.println("\n\n\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void runThreads() {
        final long[] totalReferences = new long[3];
        final long[] failReferences = new long[3];
        final long[] number = new long[3];
        final int[] algorithm = new int[3];
        final boolean[] exist = new boolean[3];
        listOfThreads = new ArrayList<Thread>();
        numberOfThreads = 3;
        mapOfResults = new HashMap<Long, Object>();

        random = new Random();

        mapOfResults.put(5000000000L, 0);
        mapOfResults.put(5000000001L, 0);

        for (int i = 0; i < numberOfThreads; i++) {
            final int finalI = i;
            listOfThreads.add(new Thread(new Runnable() {
                public void run() {
                    while (!kill) {
                        System.gc();
                        seed = random.nextLong() % 1000;
                        random.setSeed(seed);
                        exist[finalI] = false;

                        synchronized (mapOfResults) {
                            totalReferences[finalI] = Long.decode(String.valueOf(mapOfResults.get(5000000000L)));
                            totalReferences[finalI]++;
                            mapOfResults.put(5000000000L, totalReferences[finalI]);

                            if (mapOfResults.containsKey(seed)) {
                                exist[finalI] = true;
                            } else {
                                failReferences[finalI] = Long.decode(String.valueOf(mapOfResults.get(5000000001L)));
                                failReferences[finalI]++;
                                mapOfResults.put(5000000001L, failReferences[finalI]);
                            }
                        }

                        if (!exist[finalI]) {
                            try {
                                algorithm[finalI] = Math.abs(random.nextInt() % 2) + 1;
                                WeakReference<Class> c = new WeakReference<Class>(classes.get(algorithm[finalI]));
                                WeakReference<Object> o;
                                if(algorithm[finalI] == 1)
                                    o = new WeakReference<Object>(c.get().getConstructors()[0].newInstance(listOfItems, knapsackCapacity));
                                else
                                    o = new WeakReference<Object>(c.get().getConstructors()[0].newInstance(listOfItems, knapsackCapacity, repetitions));
                                WeakReference<Method> methodSolve = new WeakReference<Method>(c.get().getMethod("solve"));
                                methodSolve.get().invoke(o.get());
                                System.out.println("\nWatek " + finalI + " rozwiazuje plecak o ziarnie: " + seed);
                                WeakReference<Method> methodDescription = new WeakReference<Method>(c.get().getMethod("description"));
                                System.out.println(methodDescription.get().invoke(o.get()) + "\n" + totalReferences[finalI] + "\n" + failReferences[finalI] + "\n" + 1.0 * failReferences[finalI] / totalReferences[finalI]);

                                mapOfResults.put(seed, o.get());

                            }catch(Exception e){
                                e.printStackTrace();
                            }


                            /* int algorithm = Math.abs(random.nextInt() % 2) + 1;
                                WeakReference<Class> c = new WeakReference<Class>(classes.get(algorithm);
                                Object o;
                                if(algorithm == 1)
                                    o = c.getConstructors()[0].newInstance(listOfItems, knapsackCapacity);
                                else
                                    o = c.getConstructors()[0].newInstance(listOfItems, knapsackCapacity, repetitions);
                                Method methodSolve = c.getMethod("solve");
                                methodSolve.invoke(o);
//                                System.out.println("Rozwiązywanie plecaka o ziarnie: " + seed);
                                Method methodDescription = c.getMethod("description");
//                                System.out.println(methodDescription.invoke(o));

                                mapOfResults.put(seed, o);*/
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));
        }

        new Thread(new Runnable() {
            public void run() {
                for (Thread t : listOfThreads) {
                    t.start();
                    try {
                        Thread.sleep(1600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (!kill) {
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (mapOfResults) {
                        totalReferences[0] = Long.decode(String.valueOf(mapOfResults.get(5000000000L)));
                        failReferences[0] = Long.decode(String.valueOf(mapOfResults.get(5000000001L)));

                        System.out.println("\n\n--------------------------------------------------------------------------------------------------------------------------\n"
                                + "Liczba wszystkich odwołań: " + totalReferences[0] + "\n"
                                + "Liczba nieudanych odwołań: " + failReferences[0]);
                    }
                }
            }
        }).start();

//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(45000);
//                    kill = true;
//                    Thread.sleep(10000);
//                    mapOfResults.clear();
//                    classes.clear();
//                    Thread.sleep(45000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
