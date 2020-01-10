package Controller;

import DTO.ItemsListDTO;
import Models.*;
import Models.File.MyFile;
import Models.Folder.Folder;
import Models.Folder.OpenFolder;
import Models.Folder.SingletonFolder;
import Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ItemController {

    public void openItem(int option) {
        Folder folder = SingletonFolder.getInstance().getFolder();

        List<Item> items = folder.getMyFileList();

        if (option != 0) {
            Item item = items.get(option - 1);
            try {
                item.open();
            } catch (IOException e) {
                System.out.println("Não foi possível abrir o item desejado.");
            }
        }
    }

    public void moveItem(int option, String destination) {
        Folder folder = SingletonFolder.getInstance().getFolder();

        List<Item> items = folder.getMyFileList();

        try {
            if (option != 0) {
                Item item = items.get(option - 1);
                String source = item.getAbsolutePath();
                boolean flag = false;
                if (item instanceof MyFile) {
                    MyFile file = (MyFile) item;
                    flag = file.move(source, destination + "\\" + item.getName().getValue() + file.getExtension());
                } else if (item instanceof Folder) {
                    Folder file = (Folder) item;
                    flag = file.move(source, destination + "\\" + file.getName());
                }

                if (flag) {
                    if(folder.checkSameItem(option, destination + "\\" + item.getName().getValue())) {
                        System.out.println("Não pode mover para o mesmo diretório.");
                    } else {
                        folder.deleteItem(option);
                        SingletonFolder.getInstance().setFolder(folder);
                        System.out.println("Item movido com sucesso.");
                    }
                } else {
                    System.out.println("Não foi possível mover o ficheiro.");
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possível aceder ao diretório especificado.");
        } catch (NullPointerException e) {
            System.out.println("O diretório especificado não existe.");
        }
    }

    public void copyItem(int option, String destination) {
        Folder folder = SingletonFolder.getInstance().getFolder();

        List<Item> items = folder.getMyFileList();

        try {
            if (option != 0) {
                Item item = items.get(option - 1);
                boolean flag = false;
                if (item instanceof MyFile) {
                    MyFile file = (MyFile) item;
                    flag = file.copy(destination + "\\" + item.getName().getValue() + file.getExtension());
                } else if (item instanceof Folder) {
                    Folder file = (Folder) item;
                    flag = file.copy(destination + "\\" + file.getName());
                }

                if (flag) {
                    System.out.println("Item copiado com sucesso.\nLoading new folder state");
                } else {
                    System.out.println("Não foi possível mover o ficheiro.");
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possível aceder ao diretório especificado.");
        } catch (NullPointerException e) {
            System.out.println("O diretório especificado não existe.");
        }
    }

    public void deleteItem(int option) {
        Folder folder = SingletonFolder.getInstance().getFolder();

        List<Item> items = folder.getMyFileList();

        try {
            if (option > 0) {
                Item item = items.get(option - 1);
                boolean flag = false;
                if (item instanceof MyFile) {
                    MyFile file = (MyFile) item;
                    flag = file.delete(new File(file.getAbsolutePath()));
                } else if (item instanceof Folder) {
                    Folder file = (Folder) item;
                    flag = file.delete(new File(file.getAbsolutePath()));
                }

                if (flag) {
                    folder.deleteItem(option);
                    SingletonFolder.getInstance().setFolder(folder);
                    System.out.println("Item apagado com sucesso.\nLoading new folder state");
                } else {
                    System.out.println("Não foi possível mover o ficheiro.");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("O diretório especificado não existe.");
        } catch (NumberFormatException e) {
            System.out.println("O Item selecionado não existe.");
        }
    }

    public ItemsListDTO getActualFolderItems(){
        return new ItemsListDTO(SingletonFolder.getInstance().getFolder().getMyFileList());
    }
}
