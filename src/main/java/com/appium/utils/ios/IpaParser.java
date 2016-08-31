package com.appium.utils.ios;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListParser;
import net.lingala.zip4j.core.ZipFile;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by AnsonLiao on 26/8/2016.
 */
public class IpaParser {
    Logger log = LoggerFactory.getLogger(IpaParser.class);

    public Ipa parse(File ipaFile) {

        log.debug("Parsing {}", ipaFile);
        try {
            Path tmpPath = Files.createTempDirectory("ipaparser");
            ZipFile zipFile = new ZipFile(ipaFile.getAbsoluteFile());
            zipFile.extractAll(tmpPath.toString());
            File payloadFolder = new File(tmpPath + File.separator + "Payload");
            File appFolder = payloadFolder.listFiles()[0];

            log.debug("App Folder: {}", appFolder);

            File infoPlist = new File(appFolder + File.separator + "Info.plist");
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(infoPlist);

            Ipa ipa = new Ipa();
            ipa.setBundleName(rootDict.objectForKey("CFBundleName").toString());
            if (rootDict.objectForKey("CFBundleDisplayName") != null) {
                ipa.setBundleDisplayName(rootDict.objectForKey("CFBundleDisplayName").toString());
            }
            else {
                ipa.setBundleDisplayName(ipa.getBundleName());
            }

            ipa.setBundleIdentifier(rootDict.objectForKey("CFBundleIdentifier").toString());
            ipa.setBundleShortVersionString(rootDict.objectForKey("CFBundleShortVersionString").toString());

            File iconFile;
            if (rootDict.objectForKey("CFBundleIconFiles") != null) {
                NSObject[] iconFiles = ((NSArray) rootDict.objectForKey("CFBundleIconFiles")).getArray();
                iconFile = new File(appFolder + File.separator + iconFiles[0].toString());
            }
            else {
                iconFile = new File(appFolder + File.separator + "iTunesArtwork");
            }

            BufferedImage iconImage = ImageIO.read(iconFile);
            ipa.setIcon(iconImage);
            tmpPath.toFile().delete();
            log.debug("Returning {}", ipa);

            return ipa;
        } catch (Exception e) {
            log.error("Error: ", e);
        }

        return null;
    }

//    @Test
//    public void testIpaParser() {
//        File ipaFile = new File("/Users/AnsonLiao/Downloads/ParserTestApp.ipa");
//        IpaParser ipaParser = new IpaParser();
//        Ipa ipa = ipaParser.parse(ipaFile);
//
//        System.out.println(ipa.getBundleName());
//        System.out.println(ipa.getIcon().getHeight());
//        Assert.assertEquals("ParserTestApp", ipa.getBundleDisplayName());
//        Assert.assertEquals(512, ipa.getIcon().getHeight());
//
//    }
}
