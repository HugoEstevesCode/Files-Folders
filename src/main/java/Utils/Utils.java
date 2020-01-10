package Utils;

import DTO.FolderListDTO;
import DTO.ItemsListDTO;

import java.util.List;
import java.util.Scanner;

public class Utils {

    public static int chooseItem(ItemsListDTO dto, Scanner scanner) {
        List<String> items = dto.getItemsList();

        while (true) {
            System.out.println("\n\n-------------------------------------- Choose File --------------------------------------");
            int i;
            for (i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + " - " + items.get(i));
            }
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= 0 && opcao <= items.size())
                    return opcao;
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("\nEscolha uma opção válida.");
            }
        }
    }

    public static int chooseFolder(FolderListDTO dto, Scanner scanner) {
        List<String> folders = dto.getFoldersList();

        while (true) {
            System.out.println("\n\n-------------------------------------- Choose File --------------------------------------");
            int i;
            System.out.println("1 - ..");
            for (i = 1; i < (folders.size() + 1); i++) {
                System.out.println((i + 1) + " - " + folders.get(i - 1));
            }
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= 0 && opcao <= folders.size() + 1)
                    return opcao;
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("\nEscolha uma opção válida.");
            }
        }
    }

    public static double round(double number, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, decimalPlaces);
        number = number * factor;
        long tmp = Math.round(number);
        return (double) tmp / factor;
    }
}
