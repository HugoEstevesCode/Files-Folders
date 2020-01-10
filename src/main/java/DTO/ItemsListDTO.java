package DTO;

import Models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsListDTO {

    private List<String> itemsList;

    public ItemsListDTO(List<Item> items) {
        this.itemsList = new ArrayList<>();
        for (Item item : items)
            this.itemsList.add(item.getName().getValue());
    }

    public List<String> getItemsList(){
        return this.itemsList;
    }
}
