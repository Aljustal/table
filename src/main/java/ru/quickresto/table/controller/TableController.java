package ru.quickresto.table.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.quickresto.table.service.TableService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TableController {

    private final Map<String, String> cells = new HashMap<>();
    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/updateCell")
    @ResponseBody
    public String updateCell(@RequestParam String cellId, @RequestParam String cellValue) {
        cells.put(cellId, cellValue); // Store the input value in the cells map

        // Calculate the cell value if it's a formula
        if (cellValue.startsWith("=")) {
            String formula = cellValue.substring(1);
            try {
                double result = tableService.calculateFormula(formula, cells);
                cells.put(cellId, String.valueOf(result));
                return String.valueOf(result);
            } catch (Exception e) {
                return "Error: Invalid formula";
            }
        }

        return cells.get(cellId);
    }
}
