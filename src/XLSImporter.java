

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XLSImporter {

    public static void importFromXLS(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                String typ = row.getCell(0).getStringCellValue();
                String marka = row.getCell(1).getStringCellValue();
                String model = row.getCell(2).getStringCellValue();
                int rokProdukcji = (int) row.getCell(3).getNumericCellValue();
                //int specyficzny = (int) row.getCell(4).getNumericCellValue();
                Integer liczbaDrzwi = null;
                Integer pojemnoscSilnika = null;


                Cell liczbaDrzwiCell = row.getCell(4);
                if (liczbaDrzwiCell != null && liczbaDrzwiCell.getCellType() == CellType.NUMERIC) {
                    liczbaDrzwi = (int) liczbaDrzwiCell.getNumericCellValue();
                }

                Cell pojemnoscSilnikaCell = row.getCell(5);
                if (pojemnoscSilnikaCell != null && pojemnoscSilnikaCell.getCellType() == CellType.NUMERIC) {
                    pojemnoscSilnika = (int) pojemnoscSilnikaCell.getNumericCellValue();
                }




                if (typ.equalsIgnoreCase("Osobowy")) {
                    //Osobowy osobowy = new Osobowy(marka, model, rokProdukcji, specyficzny);
                    Osobowy osobowy = new Osobowy(marka, model, rokProdukcji, liczbaDrzwi);
                    OsobowyDAO.dodajOsobowy(osobowy);
                } else if (typ.equalsIgnoreCase("Motor")) {
                    Motor motor = new Motor(marka, model, rokProdukcji, pojemnoscSilnika);
                    //Motor motor = new Motor(marka, model, rokProdukcji, specyficzny);
                    MotorDAO.dodajMotor(motor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
