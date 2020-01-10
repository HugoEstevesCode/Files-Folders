package UI;

import Controller.FolderController;
import Controller.ItemController;
import DTO.FolderDTO;
import DTO.FolderListDTO;
import DTO.ItemDTO;
import DTO.ItemsListDTO;
import Models.Folder.Folder;
import Models.Item;
import Models.Folder.OpenFolder;
import Models.Folder.SingletonFolder;
import Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SystemUI {

    public static void menu() throws IOException {
        Scanner scanner = new Scanner(System.in);

        SingletonFolder.getInstance().setFolder(new Folder(directoryPath(scanner), new OpenFolder()));

        char action = 'x';
        while ('0' != action) {
            viewMenu();
            FolderController controller = new FolderController();
            action = scanner.nextLine().charAt(0);
            switch (action) {
                case '1':
                    System.out.println("\n\n-------------------------------------- Informação --------------------------------------");
                    FolderDTO dto = controller.getFolderInfo();
                    System.out.println(dto.display());
                    System.out.println("\n----------------------------------------------------------------------------------------");
                    break;
                case '2':
                    System.out.println("\n\n-------------------------------------- Counteudo do Diretório --------------------------------------");
                    FolderDTO dto1 = controller.getFolderInfo();
                    List<ItemDTO> items = controller.getFolderInfo().getMyFileList();

                    for(ItemDTO item : items){
                        System.out.println(item.display() + "\n\n");
                    }

                    System.out.println("\n------------------------------------------------------------------------------------------------------");
                    break;
                case '3':
                    System.out.println("\n\n-------------------------------------- Mudar Diretório --------------------------------------");
                    System.out.println("\nPara que diretório deseja mudar?");
                    FolderListDTO dto2 = controller.getActualFolderFolders();
                    int option = Utils.chooseFolder(dto2, scanner);

                    controller.changeDirectory(option);
                    System.out.println("\n");
                    break;
                case '4':
                    menuAcoes(scanner);
                    break;
                case '0':
                    break;
            }
        }
    }

    public static void menuAcoes(Scanner scanner) throws IOException {

        char action = 'x';
        while ('0' != action) {
            viewAcoes();
            action = scanner.nextLine().charAt(0);
            int option = 0;
            ItemController controller = new ItemController();
            ItemsListDTO dtos = controller.getActualFolderItems();
            switch (action) {
                case '1':
                    option = Utils.chooseItem(dtos, scanner);
                    controller.openItem(option);
                    break;
                case '2':
                    File destination = directoryPath(scanner);
                    option = Utils.chooseItem(dtos, scanner);
                    controller.moveItem(option, destination.getAbsolutePath());
                    break;
                case '3':
                    File destination1 = directoryPath(scanner);
                    option = Utils.chooseItem(dtos, scanner);
                    controller.copyItem(option, destination1.getAbsolutePath());
                    break;
                case '4':
                    option = Utils.chooseItem(dtos, scanner);
                    controller.deleteItem(option);
                    break;
                case '0':
                    break;
            }
        }
        System.out.println("\n");
    }

    public static void viewMenu(){
        System.out.print("1 - Ver informações do diretório actual.\n" +
                "2 - Verificar conteudo de diretório.\n" +
                "3 - Mudar de diretorio.\n" +
                "4 - Ações.\n" +
                "0 - Exit.\n" +
                "Opção: ");
    }

    public static void viewAcoes(){
        System.out.print("\n\n1 - Abrir Item.\n" +
                "2 - Mover item.\n" +
                "3 - Copy item.\n" +
                "4 - Delete item.\n" +
                "0 - Exit.\n" +
                "Opção: ");
    }

    public static void listItem(){
        Folder folder3 = SingletonFolder.getInstance().getFolder();
        List<Item> itemList = folder3.getMyFileList();
        int i;
        for (i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + " - " + itemList.get(i).getName());
        }
        System.out.println("0 - Voltar");
        System.out.print("Opção: ");
    }

    public static File  directoryPath(Scanner scanner){
        System.out.print("\nIntroduza um diretório para começar: ");
        String path = scanner.nextLine();

        File file = new File(path);
        while (!file.isDirectory()) {
            System.out.print("\nIntroduza um diretório válido para começar: ");
            path = scanner.nextLine();
            file = new File(path);
        }
        return file;
    }
}
