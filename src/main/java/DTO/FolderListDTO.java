package DTO;

import Models.Folder.Folder;
import Models.Item;

import java.util.ArrayList;
import java.util.List;

public class FolderListDTO {

    private List<String> foldersList;

    public FolderListDTO(List<Folder> folders) {
        this.foldersList = new ArrayList<>();
        for (Folder folder : folders)
            this.foldersList.add(folder.getName().getValue());
    }

    public List<String> getFoldersList(){
        return this.foldersList;
    }
}
