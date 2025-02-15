package net.nullpointer.xlsxfinder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nullpointer.xlsxfinder.service.XlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "XLSX API", description = "API для работы с XLSX файлами сервера")
@RestController
@RequestMapping("/api/v1/xlsx")
public class XlsxController {

    private final XlsxService xlsxService;

    @Autowired
    public XlsxController(XlsxService xlsxService) {
        this.xlsxService = xlsxService;
    }

    @Operation(summary = "Получить Nное максимальное число из XLSX файла")
    @GetMapping("/max-number")
    public int getMaxNumber(@RequestParam String filePath, int n) {
        return xlsxService.findLargest(filePath, n);
    }
}
