package rhzhou.util;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class MyUtils {
    public static String readClasspathFileAsString(String filePathString) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePathString);
        File filePath = resource.getFile();

        //读取文件
        String input = FileUtils.readFileToString(filePath, "UTF-8");
        return input;
    }
}
