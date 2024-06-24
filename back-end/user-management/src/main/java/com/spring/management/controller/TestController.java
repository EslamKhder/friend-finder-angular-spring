package com.spring.management.controller;

import com.spring.commonlib.config.translate.BundleTranslator;
import com.spring.commonlib.model.bundle.BundleErrorMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/start")
    public String test() {
        return BundleTranslator.getMessage("start");
    }

    @GetMapping("/start/list")
    public BundleErrorMessage testList() {
        return BundleTranslator.getMessages("start");
    }
}
