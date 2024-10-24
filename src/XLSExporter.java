
import org.apache.poi.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XLSExporter {

    public static void exportToXLS(File file) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Pojazdy");
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("Typ");
            header.createCell(1).setCellValue("Marka");
            header.createCell(2).setCellValue("Model");
            header.createCell(3).setCellValue("Rok Produkcji");
            //header.createCell(4).setCellValue("Specyficzny");
            header.createCell(4).setCellValue("Liczba Drzwi");
            header.createCell(5).setCellValue("Pojemność Silnika");

            String sql = "SELECT * FROM Pojazdy";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {

                int rowNum = 1;
                while (rs.next()) {
                    Row row = sheet.createRow(rowNum++);

                    String typ = rs.getString("typ");
                    String marka = rs.getString("marka");
                    String model = rs.getString("model");
                    int rokProdukcji = rs.getInt("rokProdukcji");
                    //int specyficzny;
                    int liczbaDrzwi = -1;
                    int pojemnoscSilnika = -1;

                    if (typ.equalsIgnoreCase("Osobowy")) {
                        //specyficzny = getLiczbaDrzwi(rs.getInt("idPojazdu"));
                        liczbaDrzwi = getLiczbaDrzwi(rs.getInt("idPojazdu"));
                    } else {
                        //specyficzny = getPojemnoscSilnika(rs.getInt("idPojazdu"));
                        pojemnoscSilnika = getPojemnoscSilnika(rs.getInt("idPojazdu"));
                    }

                    row.createCell(0).setCellValue(typ);
                    row.createCell(1).setCellValue(marka);
                    row.createCell(2).setCellValue(model);
                    row.createCell(3).setCellValue(rokProdukcji);
                    //row.createCell(4).setCellValue(specyficzny);

                    if (liczbaDrzwi != -1) {
                        row.createCell(4).setCellValue(liczbaDrzwi);
                    }
                    if (pojemnoscSilnika != -1) {
                        row.createCell(5).setCellValue(pojemnoscSilnika);
                    }


                }
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

        } catch (IOException | SQLException e) {
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
        return -1;
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
        return -1;
    }
}


