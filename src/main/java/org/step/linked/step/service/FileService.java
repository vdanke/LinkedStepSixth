package org.step.linked.step.service;

import java.io.InputStream;

public interface FileService {

    String save(InputStream io, String filename);
}
