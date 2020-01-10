package DTO;

import Models.Item;

import java.util.*;

public class FolderDTO implements ItemDTO {

    private String name;

    private String size;

    private String creationDate;

    private String updateDate;

    private Map<String, String> permissions = new HashMap<>();

    private List<ItemDTO> myFileList = new LinkedList<>();

    public FolderDTO(String name, String size, String creationDate, String updateDate, Map<String, Boolean> permissions,
                     List<Item> myFileList){
        this.name = name;
        this.size = size;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        for(Map.Entry<String, Boolean> entry : permissions.entrySet()){
            StringBuilder m = new StringBuilder("");
            m.append(entry.getValue());
            this.permissions.put(entry.getKey(), m.toString());
        }

        for(Item item : myFileList){
            this.myFileList.add(item.toDTO());
        }
    }

    @Override
    public String display() {
        return String.format("Item: Folder%nName: %s%nSize: %s%nCreation Date: %s%nModify Date: %s%nRead: %b%nWrite: %b%nExecute: %b",
                name, size, creationDate, updateDate, permissions.get("Read"), permissions.get("Write"), permissions.get("Execute"));
    }

    public List<ItemDTO> getMyFileList() {
        return myFileList;
    }
}
