/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialtrade1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author Harry
 */
public class ChildThread  extends Thread{
  String    url="";
  String body="";
  int count=0;
  String Cookie="";
  int positionNumber=0;
  //int timeout=45000;
  int state=0;
  
    ChildThread(String url,String body,String cookie,int positionNumber,int state)
            
    {
        this.Cookie=cookie;
        this.body=body;
        this.url=url;
        this.positionNumber=positionNumber;
        this.state=state;
    }
 
    public void run()
    {
        if(state==-1)updateUI();
        else if(state==0)FetchCookie();
        else if(state==1)Get();
        else Post();
        
    }
    void updateUI()
    {
         try
        {
        while(true)
        {
                   Thread.sleep(1000);
            
            for(int i=0;i<10;i++)
            {
                int k=Engine.completed_links[i];
                int   amount=(int)(4.34*k);
                int r=Engine.remaingTime[i];
               
                String message=Engine.Data[i][0]+" :: "+k+" :: "+Engine.messages[i]+"  :: "+r+": Rs "+amount;
                 if(message.indexOf("Done")!=-1)r=0;
                Engine.remaingTime[i]--;
                if(i==0){if(message.indexOf("Done")!=-1)MainWindow.jLabel1.setForeground(Color.blue);MainWindow.jLabel1.setText(message);}
                if(i==1){if(message.indexOf("Done")!=-1)MainWindow.jLabel2.setForeground(Color.blue);MainWindow.jLabel2.setText(message);}
                if(i==2){if(message.indexOf("Done")!=-1)MainWindow.jLabel3.setForeground(Color.blue);MainWindow.jLabel3.setText(message);}
                if(i==3){if(message.indexOf("Done")!=-1)MainWindow.jLabel4.setForeground(Color.blue);MainWindow.jLabel4.setText(message);}
                if(i==4){if(message.indexOf("Done")!=-1)MainWindow.jLabel5.setForeground(Color.blue);MainWindow.jLabel5.setText(message);}
                if(i==5){if(message.indexOf("Done")!=-1)MainWindow.jLabel6.setForeground(Color.blue);MainWindow.jLabel6.setText(message);}
                if(i==6){if(message.indexOf("Done")!=-1)MainWindow.jLabel7.setForeground(Color.blue);MainWindow.jLabel7.setText(message);}
                if(i==7){if(message.indexOf("Done")!=-1)MainWindow.jLabel8.setForeground(Color.blue);MainWindow.jLabel8.setText(message);}
                if(i==8){if(message.indexOf("Done")!=-1)MainWindow.jLabel9.setForeground(Color.blue);MainWindow.jLabel9.setText(message);}
                if(i==9){if(message.indexOf("Done")!=-1)MainWindow.jLabel10.setForeground(Color.blue);MainWindow.jLabel10.setText(message);}
           
            
                        
            }
            
        }
            } catch (InterruptedException ex) {
              }
        }


     void Get()
    {
        try{
            
        
            
            URL    url1            = new URL( url );
            HttpURLConnection con= (HttpURLConnection) url1.openConnection();
            con.setDoOutput( true );
            con.setInstanceFollowRedirects( true );
            con.setRequestMethod( "GET" );
            con.setRequestProperty( "Content-Type", " application/json; charset=UTF-8");
            con.setRequestProperty( "charset", "utf-8");
            con.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Host", "https://www.socialtrade.biz");
            con.setRequestProperty("Cookie",Cookie);
            con.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream( con.getOutputStream());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response1 = new StringBuffer();
            Engine.ThreadStatus[positionNumber][state]=con.getResponseCode();
            
            while ((inputLine = in.readLine()) != null) {
                response1.append(inputLine);
            }
            Engine.ThreadResponse[positionNumber][state]=response1.toString();
            in.close();
        }
        catch(Exception m){
            
        }
    }
  void Post()
    {
        
        try{
            
            byte[] postData       = body.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            
            URL    url1            = new URL( url );
            HttpURLConnection con= (HttpURLConnection) url1.openConnection();
            con.setDoOutput( true );
            con.setInstanceFollowRedirects( true );
            con.setRequestMethod( "POST" );
            con.setRequestProperty( "Content-Type", " application/json; charset=UTF-8");
            con.setRequestProperty( "charset", "utf-8");
            con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            con.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Host", "https://www.socialtrade.biz");
            con.setRequestProperty("Cookie",Cookie);
                      con.setConnectTimeout(90000);
    con.setReadTimeout(90000);
            con.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream( con.getOutputStream());
            wr.write( postData );
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response1 = new StringBuffer();
            Engine.ThreadStatus[positionNumber][state]=con.getResponseCode();
            while ((inputLine = in.readLine()) != null) {
                response1.append(inputLine);
            }
            String resp=response1.toString();
            
            Engine.ThreadResponse[positionNumber][state]=resp;
            in.close();
        }
        catch(Exception m){
       m.getMessage();
        }
    }
     void FetchCookie()
    {
        System.out.println("fetch cookie called ");
        try {
       PostMethod      Postmethod= new PostMethod(url);
            Postmethod.addRequestHeader("Host","www.socialtrade.biz");
            Postmethod.addRequestHeader("User-Agent"," Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
            Postmethod.addRequestHeader("Accept","application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,*/*;q=0.5");
            Postmethod.addRequestHeader("Accept-Language","en-US,en;q=0.8");
            Postmethod.addRequestHeader("Accept-Encoding","gzip,deflate,sdch");
            Postmethod.addRequestHeader("X-Client-Data","CKK2yQEIxLbJAQj9lcoB");
            Postmethod.addRequestHeader("Connection","keepalive,Keep-Alive");
            Postmethod.addRequestHeader("Cookie",Cookie);
            Postmethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
            Postmethod.setRequestBody(body);
            Postmethod.addRequestHeader("Content-Length",""+body.length());
            Postmethod.getFollowRedirects();
            
            
            Postmethod.setFollowRedirects(true);
            HttpClient client = new HttpClient();
            int status=    client.executeMethod(Postmethod);
                Cookie[] cookies = client.getState().getCookies();
        int i=0;
        String cookie="";
        while(i<cookies.length)
        {
            cookie=cookie+";"+cookies[i].getName()+"="+cookies[i].getValue();
            i++;
        }
           cookie=cookie.trim().substring(1);
            System.out.println("cookie "+cookie);
            Engine.ThreadCookie[positionNumber]=cookie;
            if(cookie.charAt(0)==';')cookie=cookie.substring(1);
            int n1=cookie.indexOf("UserID=");
            int n2=cookie.indexOf("&",n1);
            
            Engine.UserID[positionNumber]=cookie.substring(n1+7,n2).trim();
            Engine.ThreadStatus[positionNumber][state]=status;
         
        }
        catch(Exception m)
        {
            System.out.println(""+m.getMessage());
           while(count++<5)FetchCookie();
           // m.printStackTrace();
        }
        
        
        
        System.out.println("fetch cookie compleetd");
    
    }
    
}
