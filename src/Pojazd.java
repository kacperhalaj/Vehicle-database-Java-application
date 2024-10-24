public abstract class Pojazd {
    private String marka;
    private String model;
    private int rokProdukcji;

    public Pojazd(String marka, String model, int rokProdukcji) {
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rokProdukcji;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public String toString() {
        return String.format("%s %s, %d", marka, model, rokProdukcji);
    }


}
