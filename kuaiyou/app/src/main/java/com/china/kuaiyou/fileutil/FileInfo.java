package com.china.kuaiyou.fileutil;

import java.io.Serializable;

public class FileInfo implements Serializable {
    private String DisplayName;
    private String Path;

    public FileInfo() {
        this.DisplayName = "";
        this.Path = "";
    }

    public FileInfo(String DisplayName, String Path) {
        this.DisplayName = DisplayName;
        this.Path = Path;
    }

    @Override
    public String toString() {
        return "FileInfo [DisplayName=" + DisplayName + ", Path=" + Path + "]";
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}
