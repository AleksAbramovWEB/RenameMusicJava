import java.io.*;

import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class RenameMusic {

    private static final String ANSI_RESET = "\u001B[0m";

    private static final String ANSI_RED = "\u001B[31m";

    private static final String[] VALID_EXTENSIONS = {"mp3"};

    protected static String pathFrom;

    protected static String pathTo;

    protected ArrayList<ResourceMusic> ResourcesMusic = new ArrayList<>();

    protected ProgressBar progressBar;

    public static void main(String[] args) {
        try (ProgressBar pb = new ProgressBar("Запись музыки", 2000)) {

            if (args.length < 2) {
                throw new RuntimeException("Параметры не пререданы");
            }

            pathFrom = args[0];
            pathTo = args[1];

            RenameMusic renameMusic = new RenameMusic(pb);
            renameMusic.scanDir()
                       .clearToDir()
                       .run();

        } catch (Exception exception) {
            System.out.println(ANSI_RED + exception.getMessage() + ANSI_RESET);
        }
    }

    RenameMusic(ProgressBar pb) {
        progressBar = pb;
    }

    public void run() throws IOException {
        progressBar.setExtraMessage("Копирование файлов");
        for (ResourceMusic resource : ResourcesMusic) {
            FileUtils.copyFile(
                    resource.getResourceFile(),
                    resource.getNewFile()
            );
            progressBar.step();
        }
        progressBar.setExtraMessage("Готово");
    }

    public RenameMusic clearToDir() throws IOException {

        progressBar.setExtraMessage("Очистка директории: " + pathTo);

        File dir = new File(pathTo);

        if (!dir.isDirectory()) {
            throw new RuntimeException("Исходный путь не является директорией: " + pathTo);
        }

        FileUtils.cleanDirectory(dir);

        return this;
    }

    public RenameMusic scanDir() {

        progressBar.setExtraMessage("Сканирование директорий");

        File dir = new File(pathFrom);

        if (!dir.isDirectory()) {
            throw new RuntimeException("Исходный путь не является директорией: " + pathFrom);
        }

        Collection<File> files = FileUtils.listFiles(dir, VALID_EXTENSIONS, true);

        int size = files.size();

        if (size == 0) {
            throw new RuntimeException("Файлы не найлены: " + pathFrom);
        }

        progressBar.maxHint(size);

        ArrayList<Integer> numbers = new ArrayList<>();

        for (File file : files) {
            int index = randNumber(size, numbers);

            ResourceMusic resourceMusic = new ResourceMusic();
            resourceMusic.setResourceFile(file)
                         .setIndex(index);

            ResourcesMusic.add(resourceMusic);
        }

        return this;
    }

    private int randNumber(int max, ArrayList<Integer> numbers) {
        int min = 1;
        Random rand = new Random();
        int newNumber = rand.nextInt((max - min) + 1) + min;
        if (numbers.contains(newNumber)) {
            return this.randNumber(max, numbers);
        }

        numbers.add(newNumber);

        return newNumber;
    }
}