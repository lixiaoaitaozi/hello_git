package com.china.kuaiyou.fileutil;

/**
 * 黎潇自写文件搜索的格式类
 */
public class FileType {
    /**
     * 文件为视频的数组
     */
    public static String fileIsVideo[] = {".mp4", ".3gp", ".wmv", ".ts",
            ".rmvb", ".mov", ".m4v", ".avi", ".m3u8", ".3gpp", ".3gpp2",
            ".mkv", ".flv", ".divx", ".f4v", ".rm", ".asf", ".ram", ".mpg",
            ".v8", ".swf", ".m2v", ".asx", ".ra", ".ndivx", ".xvid"};
    /**
     * 文件为音频的数组
     */
    public static String fileIsVoice[] = {".mp3", ".aac", ".wav", ".wma",
            ".cda", ".flac", ".m4a", ".mid", ".mka", ".mp2", ".mpa", ".mpc", ".ape",
            ".ofr", ".ogg", ".ra", ".wv", ".tta", ".ac3", ".dts"};

    /**
     * 文件为APK的数组
     */
    public static String fileIsApk[] = {".apk"};

    public static String fileIsImage[] = {".bmp", ".jpg", ".tiff", ".gif", ".pcx",
            ".tga", ".exif", ".fpx", ".svg", ".psd", ".cdr", ".pcd", ".dxf", ".ufo",
            ".eps", ".ai", ".raw"};
    public static String fileiIsGif[] = {".gif"};
    public static String fileiIsHtml[] = {".html"};
    public static String[] fileTypes[] = {fileIsVideo, fileIsVoice, fileIsApk, fileIsImage,fileiIsGif, fileiIsHtml};


}
