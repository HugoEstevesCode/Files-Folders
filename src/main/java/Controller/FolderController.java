package Controller;

import DTO.FolderDTO;
import DTO.FolderListDTO;
import DTO.ItemsListDTO;
import Models.Folder.Folder;
import Models.Folder.OpenFolder;
import Models.Folder.SingletonFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FolderController {

    public FolderController() {

    }

    public FolderDTO getFolderInfo(){
        return (FolderDTO) SingletonFolder.getInstance().getFolder().toDTO();
    }

    public void changeDirectory(int option) throws IOException {
        Folder folder3 = SingletonFolder.getInstance().getFolder();
        List<Folder> itemList = folder3.getFolders();

        try {
            if (option == 1) {
                File file1 = new File(folder3.getAbsolutePath());
                System.out.println("Mudando de diretório, por favor aguarde.");
                Folder toSave = new Folder(new File(file1.getParent()), new OpenFolder());
                SingletonFolder.getInstance().setFolder(toSave);
            } else if(option > 0) {
                File file1 = new File(itemList.get(option - 2).getAbsolutePath());
                System.out.println("Mudando de diretório, por favor aguarde.");
                Folder toSave = new Folder(file1, new OpenFolder());
                SingletonFolder.getInstance().setFolder(toSave);
            }
        } catch (NullPointerException e) {
            System.out.println("Não foi possível aceder ao diretório especificado.");
        }
    }

    public FolderListDTO getActualFolderFolders(){
        return new FolderListDTO(SingletonFolder.getInstance().getFolder().getFolders());
    }
}
