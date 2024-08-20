package lazecoding.project.common.util.file;


import java.io.InputStream;

public class FileMock {

    public static void main(String[] args) {
        FileOperator fileOperator = null;
        boolean isLocal = true;
        if (isLocal) {
            fileOperator = new LocalFileOperator();
        } else {
            fileOperator = new MinioFileOperator();
        }

        String originPath = "/media/lazy/D/downloads/A.txt";
        String destPath = "/media/lazy/D/downloads/A1.txt";
        String destPath2 = "/media/lazy/D/downloads/A2.txt";
        System.out.println("start ========");
        System.out.println("originPath exists:" + originPath + ":" + fileOperator.exists(originPath));
        System.out.println("destPath exists:" + destPath + ":" + fileOperator.exists(destPath));
        System.out.println("rename:" + fileOperator.rename(originPath, "A1.txt"));
        System.out.println("rename ========");
        System.out.println("originPath exists:" + originPath + ":" + fileOperator.exists(originPath));
        System.out.println("destPath exists:" + destPath + ":" + fileOperator.exists(destPath));
        fileOperator.copy(destPath, originPath);
        System.out.println("copy ========");
        System.out.println("originPath exists:" + originPath + ":" + fileOperator.exists(originPath));
        System.out.println("destPath exists:" + destPath + ":" + fileOperator.exists(destPath));
        System.out.println("===========");
        InputStream originInputStream = fileOperator.getInputStream(originPath);
        System.out.println(originInputStream);
        System.out.println("upload:" + fileOperator.upload(destPath2, originInputStream));
        System.out.println("destPath exists:" + destPath2 + ":" + fileOperator.exists(destPath2));
        System.out.println("delete:" + destPath + ":" + fileOperator.delete(destPath));
        System.out.println("delete:" + destPath2 + ":" + fileOperator.delete(destPath2));

    }

}
