package com.ivanov.solid_t.controllers;

import com.ivanov.solid_t.services.TestDataBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestDataBuilderController {

    private TestDataBuilderService testDataBuilderService;

    @Autowired
    public void setTestDataBuilderService(TestDataBuilderService testDataBuilderService) {
        this.testDataBuilderService = testDataBuilderService;
    }

    @GetMapping("data")
    public String buildTestData() {
        testDataBuilderService.buildTestData();
        return "redirect:/products";
    }

    @GetMapping("delete_all_data")
    public String deleteAllData() {
        testDataBuilderService.deleteAllData();
        return "redirect:/products";
    }

}
