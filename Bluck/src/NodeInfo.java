import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class NodeInfo implements Serializable {
    private String s;
    private int bits;
    private byte namespace;

    public NodeInfo(String s, int bits, byte namespace) {
        this.s = s;
        this.bits = bits;
        this.namespace = namespace;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }

    public byte getNamespace() {
        return namespace;
    }

    public void setNamespace(byte namespace) {
        this.namespace = namespace;
    }
}
