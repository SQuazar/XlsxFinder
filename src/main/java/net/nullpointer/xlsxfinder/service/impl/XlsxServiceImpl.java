package net.nullpointer.xlsxfinder.service.impl;

import net.nullpointer.xlsxfinder.exception.FileNotFoundException;
import net.nullpointer.xlsxfinder.exception.FileReadException;
import net.nullpointer.xlsxfinder.service.XlsxService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class XlsxServiceImpl implements XlsxService {
    private static final String FOLDER = "storage";

    @Override
    public int findLargest(String filePath, int n) {
        File file = Paths.get(FOLDER, filePath).toFile();
        if (!file.exists() || !file.isFile())
            throw new FileNotFoundException("File %s not found".formatted(filePath));

        int[] found = getIntegers(file);
        if (found.length < n)
            throw new IllegalArgumentException("Not enough elements in xlsx file");

        sort(found);
        return found[found.length - n];
    }

    private int[] getIntegers(File file) {
        List<Integer> out = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null)
                throw new IllegalArgumentException("File is empty");
            for (Row row : sheet) {
                Cell cell;
                if ((cell = row.getCell(0)) == null) continue;
                out.add(convertCellToInteger(cell));
            }
        } catch (IOException e) {
            throw new FileReadException("Cannot read file %s.".formatted(file.getPath()));
        }

        if (out.isEmpty())
            throw new IllegalArgumentException("File doesn't has number elements");


        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    private int convertCellToInteger(Cell cell) {
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal numbers in file");
        }
    }

    private void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
