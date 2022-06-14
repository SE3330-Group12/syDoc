package com.group12.syDocbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin
@RestController
public class SheetController
{
    @RequestMapping("/load")
    public Object load() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("./def.json"));
        System.out.println("load sheet");
        return new String(bytes);
    }
}
