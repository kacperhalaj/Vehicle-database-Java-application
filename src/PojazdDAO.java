import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PojazdDAO {

    public static void dodajPojazd(Pojazd pojazd){
        String sql = "INSERT INTO pojazdy (marka, model, rokProdukcji, typ) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, pojazd.getMarka());
            statement.setString(2, pojazd.getModel());
            statement.setInt(3, pojazd.getRokProdukcji());
            statement.setString(4, pojazd.getClass().getSimpleName());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static void wyswietlWszystkiePojazdy(){
        String sql = "SELECT * FROM Pojazdy";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("idPojazdu") + ", Marka: " + rs.getString("marka") +
                        ", Model: " + rs.getString("model") + ", Rok Produkcji: " + rs.getInt("rokProdukcji"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    // Usuwanie pojazdu
    public static void usunPojazd(int idPojazdu){
        String sql = "DELETE FROM Pojazdy WHERE idPojazdu = ?";
        try  {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idPojazdu);
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /*
    public static void aktualizujPojazd(Pojazd pojazd, int idPojazdu){
        String sql = "UPDATE Pojazdy SET marka = ?, model = ?, rokProdukcji = ?, typ = ? WHERE idPojazdu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pojazd.getMarka());
            statement.setString(2, pojazd.getModel());
            statement.setInt(3, pojazd.getRokProdukcji());
            statement.setString(4, pojazd.getClass().getSimpleName());
            statement.setInt(5, idPojazdu);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     */

    public static void aktualizujPojazd(Pojazd pojazd, int idPojazdu) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE Pojazdy SET ");
        List<Object> parameters = new ArrayList<>();

        if (pojazd.getMarka() != null) {
            sqlBuilder.append("marka = ?, ");
            parameters.add(pojazd.getMarka());
        }
        if (pojazd.getModel() != null) {
            sqlBuilder.append("model = ?, ");
            parameters.add(pojazd.getModel());
        }
        if (pojazd.getRokProdukcji() != 0) {
            sqlBuilder.append("rokProdukcji = ?, ");
            parameters.add(pojazd.getRokProdukcji());
        }

        sqlBuilder.append("typ = ? ");
        parameters.add(pojazd.getClass().getSimpleName());
        sqlBuilder.append("WHERE idPojazdu = ?");
        parameters.add(idPojazdu);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            statement.executeUpdate();

            if (pojazd instanceof Osobowy) {
                Osobowy osobowy = (Osobowy) pojazd;
                String sqlOsobowy = "UPDATE Osobowe SET liczbaDrzwi = ? WHERE idPojazdu = ?";
                try (PreparedStatement statementOsobowy = conn.prepareStatement(sqlOsobowy)) {
                    statementOsobowy.setInt(1, osobowy.getLiczbaDrzwi());
                    statementOsobowy.setInt(2, idPojazdu);
                    statementOsobowy.executeUpdate();
                }
            } else if (pojazd instanceof Motor) {
                Motor motor = (Motor) pojazd;
                String sqlMotor = "UPDATE Motory SET pojemnoscSilnika = ? WHERE idPojazdu = ?";
                try (PreparedStatement statementMotor = conn.prepareStatement(sqlMotor)) {
                    statementMotor.setInt(1, motor.getPojemnoscSilnika());
                    statementMotor.setInt(2, idPojazdu);
                    statementMotor.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void wyszukajPojazdyPoMarce(String marka) {
        String sql = "SELECT * FROM Pojazdy WHERE marka = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, marka);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("idPojazdu") + ", Marka: " + rs.getString("marka") +
                        ", Model: " + rs.getString("model") + ", Rok Produkcji: " + rs.getInt("rokProdukcji"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
