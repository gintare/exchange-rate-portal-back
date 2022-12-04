package com.portal.exchangerate.controller;

import com.portal.exchangerate.service.DataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data_loader")
public class DoataLoaderController {

    private final DataLoaderService service;

    @Autowired
    public DoataLoaderController(DataLoaderService service) {
        this.service = service;
    }

    @GetMapping("")
    public void runDataLoaderJob(){
        service.runDataLoaderJob();
    }

}
