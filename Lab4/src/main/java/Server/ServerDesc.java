package Server;

import java.io.IOException;
import java.io.Serializable;

public class ServerDesc implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int port;
    private String description;

    public ServerDesc(){}

    public ServerDesc(String name, int port, String description){
        this.name = name;
        this.port = port;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return name + " " + port + " " + description;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
