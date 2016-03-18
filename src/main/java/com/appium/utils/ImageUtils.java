package com.appium.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by saikrisv on 17/03/16.
 */
public class ImageUtils {
    ConvertCmd cmd = new ConvertCmd();
    IMOperation op = new IMOperation();

    public void wrapDeviceFrames(String deviceFrame, String deviceScreenToBeFramed, String framedDeviceScreen)
            throws InterruptedException, IOException, IM4JavaException {
        op.addImage(deviceFrame);
        op.addImage(deviceScreenToBeFramed);
        op.gravity("center");
        op.composite();
        op.opaque("none");
        op.addImage(framedDeviceScreen);
        cmd.run(op);
    }


    public static void main(String[] arg) throws IOException {
        File dir = new File(System.getProperty("user.dir")+"/target/screenshot/");
        System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            System.out.println("file: " + file.getCanonicalPath());
        }
    }
}
