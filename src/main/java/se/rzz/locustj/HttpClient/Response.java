package se.rzz.locustj.HttpClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/27/13
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Response {
    private static final String NL = "\r\n";
    private static final int READ_STATUS = 0;
    private static final int READ_HEADERS = 1;
    private static final int READ_BODY = 2;

    private int status;
    private String statusMessage;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();
    private ByteArrayOutputStream body = new ByteArrayOutputStream();



    private StringBuilder buffer = new StringBuilder();

    private int stage = 0;

    void write(byte[] bytes, int offset, int len){


        if(stage == READ_BODY){
            body.write(bytes, offset, len);
            return;
        }

        buffer.append(new String(bytes, offset, len));

        if(buffer.indexOf(NL) == 0){
            stage++;
            body.write(bytes, 2, len);
            return;
        }

        int indexOffset = 0;

        int index;

        while((index = buffer.indexOf(NL, indexOffset)) != -1 ){

            if(index == buffer.indexOf(NL+NL, indexOffset)){
                stage++;
                byte[] b = buffer.substring(indexOffset+NL.length()).getBytes();
                body.write(b, 0, b.length);
                return;
            }

            String strLine = buffer.substring(indexOffset, index);

            indexOffset = index+NL.length();

            if(stage == READ_STATUS){
                stage++;
                String[] parts = strLine.split(" ");
                status = Integer.parseInt(parts[1]);
                statusMessage = parts[2];


            }else if(stage == READ_HEADERS){
                String[] parts = strLine.split(": ", 2);

                headers.put(parts[0].toLowerCase(), parts[1]);

                if(parts[0].equalsIgnoreCase("cookie")){
                    String[] c = parts[1].split(";");
                    for(String cookie : c){
                        cookie = cookie.trim();
                        String kvp[] = cookie.split("=", 2);
                        cookies.put(kvp[0], kvp[1]);
                    }

                }

            }

        }


        buffer.delete(0, indexOffset);


    }
}
