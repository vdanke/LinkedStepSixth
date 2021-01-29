package org.step.linked.step.service.impl;

import org.step.linked.step.service.FileService;

import java.io.InputStream;

public class LocalFileService implements FileService {

    @Override
//    @SneakyThrows(value = {IOException.class})
    public String save(InputStream io, String filename) {
//        @Cleanup OutputStream ou = new FileOutputStream("file.txt");
        return null;
    }
}
