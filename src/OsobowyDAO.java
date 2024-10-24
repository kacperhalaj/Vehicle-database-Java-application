import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OsobowyDAO {

    public static void dodajOsobowy(Osobowy osobowy){
        String sql = "INSERT INTO Osobowe (idPojazdu, liczbaDrzwi) VALUES (?, ?)";
        try {
            PojazdDAO.dodajPojazd(osobowy);
            int idPojazdu = getIdPojazdu(osobowy);
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idPojazdu);
            statement.setInt(2, osobowy.getLiczbaDrzwi());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private static int getIdPojazdu(Pojazd pojazd) throws SQLException {
        int idPojazdu = -1;
        String sql = "SELECT idPojazdu FROM Pojazdy WHERE marka = ? AND model = ? AND rokProdukcji = ?";
        try {
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

    public static void usunOsobowy(int idPojazdu){
        String sql = "DELETE FROM Osobowe WHERE idPojazdu = ?";
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
    public static void aktualizujOsobowy(Osobowy osobowy, int idPojazdu){
        PojazdDAO.aktualizujPojazd(osobowy, idPojazdu);
        String sql = "UPDATE Osobowe SET liczbaDrzwi = ? WHERE idPojazdu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, osobowy.getLiczbaDrzwi());
            statement.setInt(2, idPojazdu);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/

    public static void aktualizujOsobowy(Osobowy osobowy, int idPojazdu) {
        PojazdDAO.aktualizujPojazd(osobowy, idPojazdu);
        String sql = "UPDATE Osobowe SET marka = ?, model = ?, rokProdukcji = ?, liczbaDrzwi = ? WHERE idPojazdu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, osobowy.getMarka());
            statement.setString(2, osobowy.getModel());
            statement.setInt(3, osobowy.getRokProdukcji());
            statement.setInt(4, osobowy.getLiczbaDrzwi());
            statement.setInt(5, idPojazdu);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
