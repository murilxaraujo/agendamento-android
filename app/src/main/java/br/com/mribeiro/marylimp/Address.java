package br.com.mribeiro.marylimp;

public class Address {
    private String logradouro;
    private Integer icon;
    private String uid;

    public Address() {

    }

    public Address(String logradouro, Integer icon, String uid) {
        this.logradouro = logradouro;
        this.icon = icon;
        this.uid = uid;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Integer getIcon() {
        return icon;
    }

    public String getUid() {
        return uid;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
