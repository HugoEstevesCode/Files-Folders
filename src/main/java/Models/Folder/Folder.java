package Models.Folder;

import DTO.FolderDTO;
import DTO.ItemDTO;
import Models.Item;
import Models.File.MyFile;
import Models.Name;
import Models.Size;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.List;

public class Folder implements Item {

    private String absolutePath;

    private FolderState folderState;

    private Name name;

    private Size size;

    private Date creationDate;

    private Date updateDate;

    private Map<String, Boolean> permissions = new HashMap<>();

    private List<Item> myFileList = new LinkedList<>();


    public Folder(File folder, FolderState folderState) throws IOException {
        absolutePath = folder.getAbsolutePath();

        String[] name = folder.getName().split("\\.(?=[^\\.]+$)");
        this.name = new Name(name[0]);

        this.size = new Size(getFolderSize(folder));

        BasicFileAttributes attributes = Files.readAttributes(folder.toPath(), BasicFileAttributes.class);

        creationDate = new Date(attributes.creationTime().toMillis());

        updateDate = new Date(attributes.lastModifiedTime().toMillis());

        this.permissions.put("Read", folder.canRead());
        this.permissions.put("Write", folder.canWrite());
        this.permissions.put("Execute", folder.canExecute());

        this.folderState = folderState;

        if (this.folderState.getChilds()) {
            for (File f : folder.listFiles()) {
                if (f.isFile())
                    myFileList.add(new MyFile(f));
                else if (f.isDirectory())
                    myFileList.add((new Folder(f, new ClosedFolder())));
            }
        }
    }

    public String getAbsolutePath() {
        return this.absolutePath;
    }

    public Name getName() {
        return this.name;
    }

    public List<Item> getMyFileList() {
        return this.myFileList;
    }

    public List<Folder> getFolders() {
        List<Folder> folderList = new LinkedList<>();
        for (Item item : myFileList) {
            if (item instanceof Folder)
                folderList.add((Folder) item);
        }
        return folderList;
    }

    private double getFolderSize(File folder) {
        double length = 0;
        File[] files = folder.listFiles();

        int count = files.length;

        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            } else {
                length += getFolderSize(files[i]);
            }
        }
        return length;
    }

    public void deleteItem(int item){
        myFileList.remove(item - 1);
    }

    public boolean checkSameItem(int item, String absolutePath){
        return myFileList.get(item - 1).getAbsolutePath().contains(absolutePath);
    }

    public String toString() {
        return String.format("Item: Folder%nName: %s%nSize: %s%nCreation Date: %s%nModify Date: %s%nRead: %b%nWrite: %b%nExecute: %b",
                name, size, creationDate, updateDate, permissions.get("Read"), permissions.get("Write"), permissions.get("Execute"));
    }

    @Override
    public String open() throws IOException {
        Desktop.getDesktop().open(new File(absolutePath));
        return "";
    }

    @Override
    public boolean move(String source, String destination) throws IOException {
        File sourceFile = new File(source);
        File destFile = new File(destination);

        try {
            FileUtils.moveDirectory(sourceFile, destFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean copy(String destination) throws IOException {
        File sourceDirectory = new File(absolutePath);
        File destinationDirectory = new File(destination);

        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);

        return true;
    }

    @Override
    public boolean delete(File destination) {
        File[] allContents = destination.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                delete(file);
            }
        }


        return destination.delete();
    }

    @Override
    public ItemDTO toDTO() {
        return new FolderDTO(name.getValue(), size.toString(), creationDate.toString(), updateDate.toString(), permissions,
                myFileList);
    }
}
