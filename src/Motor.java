public class Motor extends Pojazd {
    private int pojemnoscSilnika;

    public Motor(String marka, String model, int rokProdukcji, int pojemnoscSilnika) {
        super(marka, model, rokProdukcji);
        if (pojemnoscSilnika <= 0) {
            throw new IllegalArgumentException("Pojemność silnika musi być większa niż 0.");
        }
        this.pojemnoscSilnika = pojemnoscSilnika;
    }

    public int getPojemnoscSilnika() {
        return pojemnoscSilnika;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Pojemność silnika: %d cm3", pojemnoscSilnika);
    }



}
