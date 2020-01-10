package DTO;

import Models.Extension;
import Models.Item;
import Models.Name;
import Models.Size;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDTO implements ItemDTO {

    private String name;

    private String extension;

    private String size;

    private String creationDate;

    private String updateDate;

    private Map<String, String> permissions = new HashMap<>();

    public FileDTO(String name, String extension, String size, String creationDate, String updateDate, Map<String, Boolean> permissions) {
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
            StringBuilder m = new StringBuilder("");
            m.append(entry.getValue());
            this.permissions.put(entry.getKey(), m.toString());
        }
    }

    @Override
    public String display() {
        return String.format("Item: File%nName: %s%nExtension: %s%nSize: %s%nCreation Date: %s%nModify Date: %s%nRead: %b%nWrite: %b%nExecute: %b",
                name, extension, size, creationDate, updateDate, permissions.get("Read"), permissions.get("Write"), permissions.get("Execute"));
    }
}
