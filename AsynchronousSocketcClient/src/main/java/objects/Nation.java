package objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Nation implements Serializable {
    private long n_nationkey;
    private String n_name;
    private long n_regionkey;
    private String n_comment;

    public Nation(long n_nationkey, String n_name, long n_regionkey, String n_comment) {
        this.n_nationkey = n_nationkey;
        this.n_name = n_name;
        this.n_regionkey = n_regionkey;
        this.n_comment = n_comment;
    }
    public long getN_nationkey() {
        return n_nationkey;
    }

    public void setN_nationkey(long n_nationkey) {
        this.n_nationkey = n_nationkey;
    }

    public String getN_name() {
        return n_name;
    }

    public void setN_name(String n_name) {
        this.n_name = n_name;
    }

    public long getN_regionkey() {
        return n_regionkey;
    }

    public void setN_regionkey(long n_regionkey) {
        this.n_regionkey = n_regionkey;
    }

    public String getN_comment() {
        return n_comment;
    }

    public void setN_comment(String n_comment) {
        this.n_comment = n_comment;
    }
}
