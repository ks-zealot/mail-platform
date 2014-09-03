/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamwork.web;

import com.dreamwork.service.ServiceHolder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author y.lybarskiy
 */
public class FrontJsonController extends HttpServlet{
    private static SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();

        String message = "Not supported";
        
        out.write(message.getBytes("UTF-8"));
        out.flush();

    }
     @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestID = request.getHeader("RequestID");
        response.addHeader("RequestID", requestID);
        response.setContentType("text/json; charset=UTF-8");
        ServletInputStream in = request.getInputStream();
        ServletOutputStream out = response.getOutputStream();
        String req = "";
        try {
            req = new String(read(in), "UTF-8");
            
            JSONObject jsonObject = new JSONObject(req);
            JSONObject requestJson = jsonObject.getJSONObject("request");
            String to = requestJson.getString("to");
            String date = requestJson.getString("date");
            String message = requestJson.getString("message");
            ServiceHolder.getService().receiveMessage(to, df.parse(date), message);
           
        } catch (JSONException e) {
            
        } catch (IOException e) {
             
        } catch (RuntimeException e) {
            
        } catch (Exception e) {
           
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
    
     private byte[] read(InputStream in) throws IOException {
        ByteBuffer fullBytesRed = ByteBuffer.allocate(0);
        int n;
        while (true) {
            byte[] b = new byte[2048];
            n = in.read(b);
            if (n == -1) {
                break;
            }
            byte[] currBytes = fullBytesRed.array();
            fullBytesRed = ByteBuffer.allocate(currBytes.length + n);
            fullBytesRed.put(currBytes);
            fullBytesRed.put(b, 0, n);
        }
        byte[] data = fullBytesRed.array();
        return data;
    }
}
