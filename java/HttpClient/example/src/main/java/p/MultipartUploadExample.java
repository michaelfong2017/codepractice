package p;

import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MultipartUploadExample {

//    private static String url = "http://httpbin.org/post";
    private static String url = "http://192.168.200.185:5000/v2/models/posenetclas/predict/?accept=application/json";
    private static String filePath = "test3.jpg";
    private static byte[] dataByteArray;
    public static void main(String args[]) throws Exception{
        dataByteArray = convertFileToByteArray(filePath);

//        // compress
//        System.out.println(dataByteArray.length);
//        byte[] zippedBytes = zipBytes2(dataByteArray);
//        System.out.println(zippedBytes.length);


        createFileFromByteArray2(dataByteArray);
//        Response response = release1();
//        String jsonString = response.body().string();
//        deserializeResponseToPerson(jsonString);
        Response response = release1();
        String results = response.body().string();
        System.out.println(results);

        Person person = DrawKeyPoints.deserializeResponseToPerson(results);
        DrawKeyPoints.initialize();
        BufferedImage image = DrawKeyPoints.drawKeyPoints(person, filePath);

        String base64output = imgToBase64String(image, "jpg");
        ImageIO.write(image, "jpg", new File("drawn.jpg"));

        System.out.println(base64output);
        byte[] decodedString = Base64.getDecoder().decode(base64output.getBytes());
        createFileFromByteArray2(decodedString);
    }

    public static String imgToBase64String(final BufferedImage img, final String formatName)
    {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(img, formatName, os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        }
        catch (final IOException ioe)
        {
            throw new UncheckedIOException(ioe);
        }
    }

    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }

    public static byte[] zipBytes2(byte[] input) throws IOException {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);

        compressor.setInput(input);
        compressor.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        bos.close();
        byte[] compressedData = bos.toByteArray();

        return compressedData;
    }

    private static void test1() {
        //Creating CloseableHttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a file object
        File file = new File("sample.txt");

        //Creating the FileBody object
        FileBody filebody = new FileBody(file, ContentType.DEFAULT_BINARY);

        //Creating the MultipartEntityBuilder
        MultipartEntityBuilder entitybuilder = MultipartEntityBuilder.create();

        //Setting the mode
        entitybuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        //Adding text
        entitybuilder.addTextBody("sample_text", "This is the text part of our file");

        //Adding a file
        entitybuilder.addBinaryBody("data", new File(filePath), ContentType.IMAGE_JPEG, filePath);

        //Building a single entity using the parts
        HttpEntity mutiPartHttpEntity = entitybuilder.build();

        //Building the RequestBuilder request object
        RequestBuilder reqbuilder = RequestBuilder.post(url);

        //Set the entity object to the RequestBuilder
        reqbuilder.setEntity(mutiPartHttpEntity);

        //Building the request
        HttpUriRequest multipartRequest = reqbuilder.build();

        //Executing the request
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(multipartRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Printing the status and the contents of the response
        try {
            System.out.println(EntityUtils.toString(httpresponse.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(httpresponse.getStatusLine());
        System.out.println("test1 complete");
    }

    private static void test2() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder
                .create();
        multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntity.addPart("data", new FileBody(new File(filePath), "image/jpeg"));
        StringBody contentString = new StringBody("This is contentString");
        multipartEntity.addPart("contentString",contentString);

        post.setEntity(multipartEntity.build());
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        //Printing the status and the contents of the response
        System.out.println(EntityUtils.toString(entity));
        System.out.println(response.getStatusLine());
        System.out.println("test2 complete");
    }

    private static void test3() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(100, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("data","test2.jpg",
                        RequestBody.create(MediaType.parse("image/jpeg"),
                                new File("/Users/michael/codepractice/java/HttpClient/test2.jpg")))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        System.out.println("test3 complete");
    }

    private static void test4() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("urls", "https://lh3.googleusercontent.com/proxy/2sih5Hms9-u-ypfbKeBUOlFsgEhsxAI01c4GlpH-CtYj7H5bBcqFZ2zaC267Qd5tHulH-vwRM8cAQh2GW7vbCBywGIxjUUOhDTJYvXd0j3HVZWdmk4Qg-4mGoA")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
        System.out.println("test4 complete");
    }

    private static byte[] convertFileToByteArray(String filePath) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(filePath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        return data;
    }

    private static void createFileFromByteArray(byte[] data) throws IOException {
        long startTime = System.nanoTime();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage = ImageIO.read(bis);
        ImageIO.write(bImage, "jpg", new File("image_to_post.jpg") );
        long endTime = System.nanoTime();
        System.out.println("create file used "+(endTime-startTime)+" nanoseconds");

    }

    private static void createFileFromByteArray2(byte[] data) {
        long startTime = System.nanoTime();

        String filename = "image_to_post2.jpg";

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;

        try {

            // create FileOutputStream from filename
            fos = new FileOutputStream(filename);

            // create BufferedOutputStream for FileOutputStream
            bos = new BufferedOutputStream(fos);

            bos.write(data);

        }
        catch (FileNotFoundException fnfe) {
            System.out.println("File not found" + fnfe);
        }
        catch (IOException ioe) {
            System.out.println("Error while writing to file" + ioe);
        }
        finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            }
            catch (Exception e) {
                System.out.println("Error while closing streams" + e);
            }
        }
        long endTime = System.nanoTime();
        System.out.println("create file used "+(endTime-startTime)+" nanoseconds");
    }

    private static void test5() {
        //Creating CloseableHttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a file object
        File file = new File("sample.txt");

        //Creating the FileBody object
        FileBody filebody = new FileBody(file, ContentType.DEFAULT_BINARY);

        //Creating the MultipartEntityBuilder
        MultipartEntityBuilder entitybuilder = MultipartEntityBuilder.create();

        //Setting the mode
        entitybuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        //Adding text
        entitybuilder.addTextBody("sample_text", "This is the text part of our file");

        //Adding a file
//        entitybuilder.addBinaryBody("data", dataByteArray);
        entitybuilder.addPart("data", new ByteArrayBody(dataByteArray, System.currentTimeMillis() + ".jpg"));

        //Building a single entity using the parts
        HttpEntity mutiPartHttpEntity = entitybuilder.build();

        //Building the RequestBuilder request object
        RequestBuilder reqbuilder = RequestBuilder.post(url);

        //Set the entity object to the RequestBuilder
        reqbuilder.setEntity(mutiPartHttpEntity);

        //Building the request
        HttpUriRequest multipartRequest = reqbuilder.build();

        //Executing the request
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(multipartRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Printing the status and the contents of the response
        try {
            System.out.println(EntityUtils.toString(httpresponse.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(httpresponse.getStatusLine());
        System.out.println("test5 complete");
    }

    private static Response release1() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(25, TimeUnit.SECONDS)
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("data","image_to_post2.jpg",
                        RequestBody.create(MediaType.parse("image/jpeg"),
                                new File("image_to_post2.jpg")))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }


}

