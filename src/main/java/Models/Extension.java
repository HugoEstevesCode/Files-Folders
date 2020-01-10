package Models;

public class Extension {

    private String value;

    public Extension(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public String toString(){
        return "." + value;
    }
}
