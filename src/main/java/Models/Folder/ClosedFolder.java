package Models.Folder;

public class ClosedFolder implements FolderState {

    @Override
    public boolean getChilds() {
        return false;
    }
}
