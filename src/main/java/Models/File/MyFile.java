package Models.File;

import DTO.FileDTO;
import DTO.ItemDTO;
import Models.Extension;
import Models.Item;
import Models.Name;
import Models.Size;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyFile implements Item {

    private String absolutePath;

    private Name name;

    private Extension extension;

    private Size size;

    private Date creationDate;

    private Date updateDate;

    private Map<String, Boolean> permissions = new HashMap<>();

    public MyFile(File file) throws IOException {
        absolutePath = file.getAbsolutePath();

        String[] name = file.getName().split("\\.(?=[^\\.]+$)");
        this.name = new Name(name[0]);
        this.extension = new Extension(name[1]);

        this.size = new Size(file.length());

        BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

        creationDate = new Date(attributes.creationTime().toMillis());

        updateDate = new Date(attributes.lastModifiedTime().toMillis());

        this.permissions.put("Read", file.canRead());
        this.permissions.put("Write", file.canWrite());
        this.permissions.put("Execute", file.canExecute());

    }

    public String getAbsolutePath(){
        return this.absolutePath;
    }

    public Name getName(){
        return this.name;
    }

    public Extension getExtension(){
        return extension;
    }

    public String toString(){
        return String.format("Item: File%nName: %s%nExtension: %s%nSize: %s%nCreation Date: %s%nModify Date: %s%nRead: %b%nWrite: %b%nExecute: %b",
                name, extension, size, creationDate, updateDate, permissions.get("Read"), permissions.get("Write"), permissions.get("Execute"));
    }

    @Override
    public String open() throws IOException {
        Desktop.getDesktop().open(new File(absolutePath));
        return "";
    }

    @Override
    public boolean move(String source, String destination) throws IOException {

        Path temp = Files.move(Paths.get(source),
                Paths.get(destination));

        if(temp != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean copy(String destination) throws IOException {
        Path sourceFile = Paths.get(absolutePath);
        Path destinationFile = Paths.get(destination);

        Files.copy(sourceFile, destinationFile);
        return true;
    }

    @Override
    public boolean delete(File destination) {
        return destination.delete();
    }

    @Override
    public ItemDTO toDTO() {
        return new FileDTO(this.name.getValue(), this.extension.getValue(), this.size.toString(), this.creationDate.toString(),
                this.updateDate.toString(), permissions);
    }
}
