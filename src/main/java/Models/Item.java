package Models;

import DTO.ItemDTO;

import java.io.File;
import java.io.IOException;

public interface Item {

    public String open() throws IOException;
    public boolean move(String source, String destination) throws IOException;
    public boolean copy(String destination) throws IOException;
    public boolean delete(File destination);
    public Name getName();
    public String getAbsolutePath();
    public ItemDTO toDTO();
}
