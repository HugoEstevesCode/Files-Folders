package Models;

public class Name {

    private String value;

    public Name(String name){
        value = name;
    }

    public String getValue(){
        return value;
    }

    private void setValue(String name){
        value = name;
    }

    public boolean updateName(String name){
        setValue(name);
        return true;
    }

    public String toString(){
        return value;
    }
}
