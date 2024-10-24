public class Osobowy extends Pojazd {
    private int liczbaDrzwi;

    public Osobowy(String marka, String model, int rokProdukcji, int liczbaDrzwi) {
        super(marka, model, rokProdukcji);
        if (liczbaDrzwi <= 0) {
            throw new IllegalArgumentException("Liczba drzwi musi być większa niż 0.");
        }
        this.liczbaDrzwi = liczbaDrzwi;
    }

    public int getLiczbaDrzwi() {
        return liczbaDrzwi;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Drzwi: %d", liczbaDrzwi);
    }



}
