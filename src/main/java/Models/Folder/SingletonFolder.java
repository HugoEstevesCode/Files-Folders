package Models.Folder;

public class SingletonFolder {

    private static SingletonFolder singletonFolder = null;

    private Folder folder;

    private SingletonFolder(){

    }

    public static SingletonFolder getInstance(){
        if(singletonFolder == null){
            singletonFolder = new SingletonFolder();
        }

        return singletonFolder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Folder getFolder() {
        return folder;
    }
}
