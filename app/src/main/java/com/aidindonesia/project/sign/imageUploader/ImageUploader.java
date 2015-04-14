package com.aidindonesia.project.sign.imageUploader;

import java.io.File;

/**
 * Created by sagungw on 1/26/2015.
 */
public abstract class ImageUploader {

    protected String serverUri = "";
    protected String filePath = "";
    protected String fileNameToSave = "";
    protected File file;

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public String getServerUri() {
        return this.serverUri;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFileNameToSave(String fileNameToSave) {
        this.fileNameToSave = fileNameToSave;
    }

    public String getFileNameToSave() {
        return this.fileNameToSave;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void setFileByPath() {
        if(this.getFilePath() != "")
            file = new File(this.getFilePath());
    }

    protected abstract String uploadFile();

    public abstract String uploadFile(String fileNameToSave);

}
