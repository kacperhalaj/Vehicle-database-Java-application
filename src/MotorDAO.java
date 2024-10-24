import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotorDAO {

    public static void dodajMotor(Motor motor){

        String sql = "INSERT INTO Motory (idPojazdu, pojemnoscSilnika) VALUES (?, ?)";
        try  {
            PojazdDAO.dodajPojazd(motor);
            int idPojazdu = getIdPojazdu(motor);
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idPojazdu);
            statement.setInt(2, motor.getPojemnoscSilnika());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static int getIdPojazdu(Pojazd pojazd){
        int idPojazdu = -1;
        String sql = "SELECT idPojazdu FROM Pojazdy WHERE marka = ? AND model = ? AND rokProdukcji = ?";
        try  {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, pojazd.getMarka());
            statement.setString(2, pojazd.getModel());
            statement.setInt(3, pojazd.getRokProdukcji());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idPojazdu = rs.getInt("idPojazdu");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return idPojazdu;
    }

    public static void usunMotor(int idPojazdu){
        String sql = "DELETE FROM Motory WHERE idPojazdu = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idPojazdu);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        PojazdDAO.usunPojazd(idPojazdu);
    }

/*
    public static void aktualizujMotor(Motor motor, int idPojazdu){
        PojazdDAO.aktualizujPojazd(motor, idPojazdu);
        String sql = "UPDATE Motory SET pojemnoscSilnika = ? WHERE idPojazdu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, motor.getPojemnoscSilnika());
            statement.setInt(2, idPojazdu);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 */

    public static void aktualizujMotor(Motor motor, int idPojazdu) {
        PojazdDAO.aktualizujPojazd(motor, idPojazdu);
        String sql = "UPDATE Motory SET marka = ?, model = ?, rokProdukcji = ?, pojemnoscSilnika = ? WHERE idPojazdu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, motor.getMarka());
            statement.setString(2, motor.getModel());
            statement.setInt(3, motor.getRokProdukcji());
            statement.setInt(4, motor.getPojemnoscSilnika());
            statement.setInt(5, idPojazdu);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
