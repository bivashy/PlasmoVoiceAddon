package com.ubivashka.plasmovoice.sound.holder.json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import com.google.gson.Gson;
import com.ubivashka.plasmovoice.sound.holder.AbstractHolder;

public abstract class AbstractJsonHolder<T> extends AbstractHolder<T> {
    protected final Gson gson;
    private final Class<T> clazz;
    protected final File filesFolder;

    public AbstractJsonHolder(Class<T> clazz, File folder, Gson gson) {
        this.clazz = clazz;
        this.gson = gson;
        this.filesFolder = folder;
        folder.mkdir();
        load();
    }

    public void load() {
        Arrays.asList(getAllFiles()).forEach(file -> {
            T object = loadFromFile(file);
            if (object == null) {
                onObjectLoadNull();
                return;
            }
            list.add(object);
        });
    }

    public void save() {
        list.forEach(object -> {
            if (object == null) {
                onObjectSaveNull();
                return;
            }
            File file = new File(filesFolder + File.separator + object + ".json");
            if (!file.exists())
                try {
                    file.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            saveToFile(object, file);
        });
    }

    protected void saveToFile(T object, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(object, writer);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected T loadFromFile(File file) {
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected File[] getAllFiles() {
        if (filesFolder.listFiles() == null)
            return new File[0];
        return Arrays.stream(filesFolder.listFiles())
                .filter(file -> file.getName().contains(".") && file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("json"))
                .toArray(File[]::new);
    }

    protected void onObjectLoadNull() {
        System.err.println("Tried to load null " + clazz.getSimpleName());
    }

    protected void onObjectSaveNull() {
        System.err.println("Tried to save null " + clazz.getSimpleName());
    }
}
