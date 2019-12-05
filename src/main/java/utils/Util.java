package utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

    public static Path loadPath(String name) throws URISyntaxException {
        return Paths.get(loadURI(name));
    }

    public static URI loadURI(String name) throws URISyntaxException {
        return Util.class.getResource(name).toURI();
    }

    public static String loadString(String name) throws IOException {
        return IOUtils.toString(loadStream(name), StandardCharsets.UTF_8);
    }

    public static InputStream loadStream(String path) {
        return Util.class.getResourceAsStream(path);
    }

}
