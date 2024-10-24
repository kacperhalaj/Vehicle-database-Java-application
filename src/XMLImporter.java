import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

public class XMLImporter {

    public static void importFromXML(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("pojazd");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String typ = element.getAttribute("typ");
                    String marka = element.getElementsByTagName("marka").item(0).getTextContent();
                    String model = element.getElementsByTagName("model").item(0).getTextContent();
                    int rokProdukcji = Integer.parseInt(element.getElementsByTagName("rokProdukcji").item(0).getTextContent());

                    if (typ.equalsIgnoreCase("Osobowy")) {
                        int liczbaDrzwi = Integer.parseInt(element.getElementsByTagName("liczbaDrzwi").item(0).getTextContent());
                        Osobowy osobowy = new Osobowy(marka, model, rokProdukcji, liczbaDrzwi);
                        OsobowyDAO.dodajOsobowy(osobowy);
                    } else if (typ.equalsIgnoreCase("Motor")) {
                        int pojemnoscSilnika = Integer.parseInt(element.getElementsByTagName("pojemnoscSilnika").item(0).getTextContent());
                        Motor motor = new Motor(marka, model, rokProdukcji, pojemnoscSilnika);
                        MotorDAO.dodajMotor(motor);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
