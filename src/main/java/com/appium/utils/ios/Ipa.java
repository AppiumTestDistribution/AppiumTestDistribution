package com.appium.utils.ios;

import java.awt.image.BufferedImage;

/**
 * Created by AnsonLiao on 26/8/2016.
 */
public class Ipa {

    private String bundleName;

    private String bundleDisplayName;

    private String bundleIdentifier;

    private String bundleShortVersionString;

    private BufferedImage icon;

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getBundleDisplayName() {
        return bundleDisplayName;
    }

    public void setBundleDisplayName(String bundleDisplayName) {
        this.bundleDisplayName = bundleDisplayName;
    }

    public String getBundleIdentifier() {
        return bundleIdentifier;
    }

    public void setBundleIdentifier(String bundleIdentifier) {
        this.bundleIdentifier = bundleIdentifier;
    }

    public String getBundleShortVersionString() {
        return bundleShortVersionString;
    }

    public void setBundleShortVersionString(String bundleShortVersionString) {
        this.bundleShortVersionString = bundleShortVersionString;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Ipa{"
                + "bundleName='" + bundleName + '\''
                + ", bundleDisplayName='" + bundleDisplayName + '\''
                + ", bundleIdentifier='" + bundleIdentifier + '\''
                + ", bundleShortVersionString='" + bundleShortVersionString + '\''
                + '}';
    }
}
