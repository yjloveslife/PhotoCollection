import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyTest {

    @Value("${url}")
    String url;

    @Test
    public void One() {
        System.out.println(System.currentTimeMillis());
        String path = System.getProperty("user.dir")+"/src/main/resources/static/img/";
        path = path.replace("\\","/");
        File file = new File(path);
        File[] filelist = file.listFiles();
        System.out.println(filelist+"!!!!!!!!!");
        System.out.println(path);
        System.out.println(filelist[0].getName());
    }

    @Test
    public void two() {
        long a = System.currentTimeMillis();
        System.out.println(a);

        System.out.println(url);
    }
}
