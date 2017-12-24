package com.china.kuaiyou.thread;


import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.fileutil.MyFileSearch;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/15 0015.
 */

public class GetTemplateThread extends Thread {
    private ArrayList<FileInfo> imageFileInfos;
    private ArrayList<FileInfo> videoileInfos;
    private ArrayList<FileInfo> zipInfos;
    private GetTemplateThreadImp getTemplateThreadImp;

    public GetTemplateThread(GetTemplateThreadImp getTemplateThreadImp) {
        this.getTemplateThreadImp = getTemplateThreadImp;
    }

    @Override
    public void run() {
        super.run();
        ArrayList<FileInfo> fileInfos = new ArrayList<>();
        fileInfos = MyFileSearch.getPhoneAllFile(fileInfos);
        imageFileInfos = new ArrayList<>();
        videoileInfos = new ArrayList<>();
        zipInfos=new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            switch (MyFileSearch.fileType(fileInfo.getPath())) {
                case 0:
                    videoileInfos.add(fileInfo);
                    break;
                case 3:
                case 4:
                    imageFileInfos.add(fileInfo);
                    break;
                case 6:
                    zipInfos.add(fileInfo);
                    break;
            }
        }
        if (getTemplateThreadImp != null)
            getTemplateThreadImp.initOk(imageFileInfos, videoileInfos,zipInfos);

    }
}
