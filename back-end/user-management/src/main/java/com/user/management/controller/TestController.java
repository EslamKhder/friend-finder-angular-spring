package com.user.management.controller;

import com.user.management.config.translate.BundleTranslator;
import com.user.management.model.bundle.BundleErrorMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/start")
    public String test() {
        return BundleTranslator.getMessage("start");
    }

    @GetMapping("/start/list")
    public BundleErrorMessage testList() {
        return BundleTranslator.getAllMessage("start");
    }
}
