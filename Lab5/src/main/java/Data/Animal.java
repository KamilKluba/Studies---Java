package Data;

public class Animal {
    private int id;
    private String name;
    private int ownerID;

    public Animal(){}

    public Animal(int id, String name, int ownerID) {
        this.id = id;
        this.name = name;
        this.ownerID = ownerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerID=" + ownerID +
                '}';
    }
}
