package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/27 10:23
 */
public class MyClassloader extends ClassLoader {

    private String classloaderName;

    private String DIR = "C:\\workspace\\IDEA\\threadTest\\classloaderPath";//System.getProperty("user.dir");

    public MyClassloader() {
        super();
    }

    public MyClassloader(String classloaderName) {
        super();
        this.classloaderName = classloaderName;
    }

    public MyClassloader(ClassLoader parent, String classloaderName) {
        super(parent);
        this.classloaderName = classloaderName;
    }

    /**
     * @param name : 加载的类全路径
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        File classFile = new File(DIR, classPath + ".class");
        if (!classFile.exists()) {
            throw new ClassNotFoundException("the class " + name + " not found");
        }
        byte[] classBytes = loadClassBytes(classFile);
        if (null == classBytes || classBytes.length == 0) {
            throw new ClassNotFoundException("load the class " + name + " failed");
        }

        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    /**
     * 文件byte
     * @param classFile
     * @return
     */
    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)) {

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
