import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XMLExporter {

    public static void exportToXML(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("pojazdy");
            document.appendChild(root);

            String sql = "SELECT * FROM Pojazdy";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Element pojazd = document.createElement("pojazd");
                    pojazd.setAttribute("typ", rs.getString("typ"));

                    Element marka = document.createElement("marka");
                    marka.appendChild(document.createTextNode(rs.getString("marka")));
                    pojazd.appendChild(marka);

                    Element model = document.createElement("model");
                    model.appendChild(document.createTextNode(rs.getString("model")));
                    pojazd.appendChild(model);

                    Element rokProdukcji = document.createElement("rokProdukcji");
                    rokProdukcji.appendChild(document.createTextNode(String.valueOf(rs.getInt("rokProdukcji"))));
                    pojazd.appendChild(rokProdukcji);

                    if (rs.getString("typ").equalsIgnoreCase("Osobowy")) {
                        Element liczbaDrzwi = document.createElement("liczbaDrzwi");
                        // Retrieve additional data for Osobowy from Osobowe table
                        liczbaDrzwi.appendChild(document.createTextNode(String.valueOf(getLiczbaDrzwi(rs.getInt("idPojazdu")))));
                        pojazd.appendChild(liczbaDrzwi);
                    } else if (rs.getString("typ").equalsIgnoreCase("Motor")) {
                        Element pojemnoscSilnika = document.createElement("pojemnoscSilnika");
                        // Retrieve additional data for Motor from Motory table
                        pojemnoscSilnika.appendChild(document.createTextNode(String.valueOf(getPojemnoscSilnika(rs.getInt("idPojazdu")))));
                        pojazd.appendChild(pojemnoscSilnika);
                    }

                    root.appendChild(pojazd);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getLiczbaDrzwi(int idPojazdu) throws SQLException {
        String sql = "SELECT liczbaDrzwi FROM Osobowe WHERE idPojazdu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idPojazdu);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("liczbaDrzwi");
            }
        }
        return -1; // Default value if not found
    }

    private static int getPojemnoscSilnika(int idPojazdu) throws SQLException {
        String sql = "SELECT pojemnoscSilnika FROM Motory WHERE idPojazdu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idPojazdu);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("pojemnoscSilnika");
            }
        }
        return -1; // Default value if not found
    }
}
