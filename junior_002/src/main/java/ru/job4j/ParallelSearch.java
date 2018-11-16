package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Поиск текста в файловой системе в некольких потоках.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.04.2018
 */
@ThreadSafe
public class ParallelSearch extends SimpleFileVisitor<Path> {
    private final String root;
    private final String text;
    private final List<String> exts;
    private final ConcurrentLinkedQueue<String> files = new ConcurrentLinkedQueue<>();
    private final List<String> paths = Collections.synchronizedList(new ArrayList<>());
    private volatile boolean isFinished = false;


    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    /**
     * Посещение файла.
     * @param file - путь к файлу.
     * @param attrs - аттрибуты файла.
     * @return - результат посещения файла.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isRegularFile()) {
            for (String extend : exts) {
                if (file.toString().contains(extend)) {
                    files.add(file.toString());
                    synchronized (this) {
                        notify();
                    }
                    break;
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    /**
     * инициализация многопоточного поиска.
     * @throws InterruptedException - если какой-либо из потоков был прерван.
     */
    public void init() throws InterruptedException {
        FileVisitor<Path> visitor = this;
        new Thread(() -> {
            try {
                Files.walkFileTree(Paths.get(root), visitor);
            } catch (IOException e) {
                throw new IllegalStateException("Walk file tree IOException", e);
            }
            isFinished = true;
            synchronized (visitor) {
                visitor.notifyAll();
            }
        }).start();
        int proc = Runtime.getRuntime().availableProcessors() - 1;
        Thread[] readers = new Thread[proc];
        for (int i = 0; i < proc; i++) {
            readers[i] = new Thread(this::runFilesChecker);
            readers[i].start();
        }
        for (Thread reader : readers) {
            reader.join();
        }
    }

    /**
     * Результат поиска.
     * @return - список путей к файлам, содержащим искомый текст.
     */
    public List<String>  result() {
        return this.paths;
    }

    private void runFilesChecker() {
        while (!(isFinished && files.isEmpty())) {
            String path = files.poll();
            if (path == null) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    throw new IllegalStateException("Thread should't be interrupted.", e);
                }
                continue;
            }
            findText(path);
        }
    }

    private void findText(String path) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));
            String nextLine = reader.readLine();
            while (nextLine != null) {
                if (nextLine.contains(text)) {
                    paths.add(path);
                    break;
                }
                nextLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Illegal path.", e);
        }
    }
}
