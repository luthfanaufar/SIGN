package imageuploader;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sagungw on 1/26/2015.
 */
public class HttpImageUploader extends ImageUploader {
    public HttpImageUploader(String filePath, String serverUri) {
        this.setFilePath(filePath);
        this.setServerUri(serverUri);
        this.setFileByPath();
    }
    public HttpImageUploader(String filePath, String serverUri, File file) {
        this.setFilePath(filePath);
        this.setServerUri(serverUri);
        this.setFile(file);
    }

    @Override
    protected String uploadFile() {
        String fileServerPath = "";

        if(!this.getFile().isFile()) {
            fileServerPath = "File not found";
        }
        else {
            try {
                HttpURLConnection connection = null;
                DataOutputStream outputStream = null;

                String endOfLine = "\r\n";
                String boundary = "*****";
                String twoHyphens = "--";

                int bytesRead, bytesAvailable, bufferSize, serverResponseCode;
                int maxBufferSize = 1 * 1024 * 1024;
                byte[] buffer;

                FileInputStream fileInputStream = new FileInputStream(this.getFile());
                URL url = new URL(this.serverUri);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", this.getFileNameToSave());

                outputStream = new DataOutputStream(connection.getOutputStream());

                outputStream.writeBytes(twoHyphens + boundary + endOfLine);
                outputStream.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename='" + this.getFileNameToSave() + "'" + endOfLine);
                outputStream.writeBytes(endOfLine);

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(endOfLine);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + endOfLine);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                if(serverResponseCode == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    fileServerPath = reader.readLine();
                    Log.e("Upload completed", "Upload completed");
                }
                fileInputStream.close();
                outputStream.flush();
                outputStream.close();

            } catch(MalformedURLException e) {
                Log.e("Upload failed", "Error: " + e.getMessage(), e);
                fileServerPath = "Posting failed. Error: " + e.getMessage();
            } catch(Exception e) {
                Log.e("Upload failed", "Error: " + e.getMessage(), e);
                fileServerPath = "Posting failed. Error: " + e.getMessage();
            }
        }
        return fileServerPath;
    }

    @Override
    public String uploadFile(String fileNameToSave) {
        this.setFileNameToSave(fileNameToSave);
        return this.uploadFile();
    }

}
