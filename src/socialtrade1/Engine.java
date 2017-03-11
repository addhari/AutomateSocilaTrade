/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package socialtrade1;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 *
 * @author Harry
 */
public class Engine extends Thread {
  
    int maxIdCount=10;
    static String[][] Data=new String[10][15];
    static int[] runningState=new int[10];
    boolean EnableCache=true;
    //   static String[] userName=new String[20];
    String hfHiddenFieldID="";
    String  password="";
    String linksData="";
    String viewstateGenerator="";
    static  int[]remaingTime=new int[10];
    static boolean ThreadLogin=true;
    String viewstate="";
    int status=0;
    int PositionNumber=1;
    static String[] messages=new String[20];
    static int[][] ThreadStatus=new int[20][5];
    int remaing_links=0;
    //   int childThreads=3;
    static int[] completed_links=new int[10];
    static String[] UserID=new String[10];
    int state=0;
    int runningSerial=0;
    int timeTaken=0;
    static String  response="";
    static String[][] ThreadResponse=new String[10][10];
    String cookie="";
    static  String[] ThreadCookie=new String[10];
    String url="";
    String body="";
    String referer="";
    static HttpClient client = null;
    PostMethod Postmethod=null;
    GetMethod Getmethod=null;
    boolean isPostMethod=true;
    Document doc=null;
    int sleepingTime=30;
    
    long previousTime=0;
    String[][] links=new String[500][5];
    String NextWorkID="";
    String WorkID="";
    
    Engine(String uname,String password,int PositionNumber)
    {
        this.Data[PositionNumber][0]=uname;
        this.PositionNumber=PositionNumber;
        this.password=password;
        
    }
    
    public void run()
    {
        Automate();
        
    }
    
    public void Automate()
    {
        if(EnableCache)
        {
            state=3;
            loadData();
            
        }
        
        while(state<7)
        {
            runningState[PositionNumber]=state;
            if(state<4)   System.out.println(PositionNumber+"--State-- "+state);
            set(""+state);
            if(state==0)
            {
                
                url="https://www.socialtrade.biz/login.aspx";
                body="__EVENTTARGET="+byId("__EVENTTARGET")+"&__EVENTARGUMENT="+byId("__EVENTARGUMENT")+""
                        + "&__VIEWSTATE="+byId("__VIEWSTATE")+"&__VIEWSTATEGENERATOR="+byId("__VIEWSTATEGENERATOR")+""
                        + "&ctl00%24HFSessionID=&ctl00%24ContentPlaceHolder1%24txtEmailID="+Data[PositionNumber][0]+"&ctl00%24ContentPlaceHolder1"
                        + "%24txtPassword="+encode(password)+"&ctl00%24ContentPlaceHolder1%24CndSignIn=LOGIN";
                body=body.replace("+","%2B").replace("/","%2F");
                
                if(ThreadLogin)
                {
                    PostThread();
                    cookie=ThreadCookie[PositionNumber];
                    System.out.println("cookie is "+cookie);
                }
                else
                {
                    post();
                    FetchCookie();
                }
                viewstateGenerator=byId("__VIEWSTATEGENERATOR");
                System.out.println("viewstate sfecthed "+viewstateGenerator);
            }
            if(state==1)
            {
                url="https://www.socialtrade.biz/User/dashboard.aspx";
                getThread();
                viewstate=byId("__VIEWSTATE");
                System.out.println("viewstte ========"+viewstate);
                new Save("1",response).start();
            }
            if(state==2)
            {
                url="https://www.socialtrade.biz/User/dashboard.aspx";
                body="ctl00%24ScriptManager1=ctl00%24ContentPlaceHolder1%24UpPanel1%7Cctl00%24ContentPlaceHolder1%24lnkViewWork2&__EVENTTARGET=ctl00%24ContentPlaceHolder1%24lnkViewWork2&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE="+viewstate+"&__VIEWSTATEGENERATOR="+viewstateGenerator+"&ctl00%24ContentPlaceHolder1%24HFInVoiceNo=&ctl00%24ContentPlaceHolder1%24HFType=&ctl00%24ContentPlaceHolder1%24Accordion1_AccordionExtender_ClientState=0&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtAccountHolderName=&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtNEFTFromAccount=&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtNEFTBankName=&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtNEFTBranch=&ctl00%24ContentPlaceHolder1%24AccPan2_content%24ddlBank=915020024019456&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtNEFTToAccount=&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtNEFTAmmount=57500.00&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtNEFTUdrNO=&ctl00%24ContentPlaceHolder1%24AccPan2_content%24txtNEFTDate=&ctl00%24ContentPlaceHolder1%24AccPan2_content%24ddlPlan=0&ctl00%24ContentPlaceHolder1%24AccPOS_content%24txtPOSCardHolderName=&ctl00%24ContentPlaceHolder1%24AccPOS_content%24txtPOSMobile=&ctl00%24ContentPlaceHolder1%24AccPOS_content%24txtPOSPaymentID=PNSOL_&ctl00%24ContentPlaceHolder1%24AccPOS_content%24txtPosDate=&ctl00%24ContentPlaceHolder1%24AccPOS_content%24txtPOSAmount=57500.00&ctl00%24ContentPlaceHolder1%24AccPOS_content%24ddlPosPlan=0&__ASYNCPOST=true&";
                PostThread();
                new Save("2",response).start();
            }
            if(state==3)
            {
                
                url="https://www.socialtrade.biz/User/TodayTask.aspx";
                get();
                new Save("3",response).start();
                viewstate=byId("__VIEWSTATE").replace("+","%2B").replace("/","%2F");;
                viewstateGenerator=byId("__VIEWSTATEGENERATOR").replace("+","%2B").replace("/","%2F");;
                set("3_1");
                url="https://www.socialtrade.biz/User/TodayTask179.aspx/GetWorkHistory";
                body="{ 'pageNumber':'1', 'pageSize': '40', 'userId': '"+UserID[PositionNumber]+"'}";
                PostThread();
                
                new Save("3_3",response).start();
                if(response==null)response="";
                if(response.indexOf("NOALERT")!=-1)
                {
                    requestWork();
                    url="https://www.socialtrade.biz/User/TodayTask179.aspx/GetWorkHistory";
                    body="{ 'pageNumber':'1', 'pageSize': '40', 'userId': '"+UserID[PositionNumber]+"'}";
                    PostThread();
                    
                    new Save("3_33",response).start();
                }
                else
                {
                    linksData=response;
                    ParseJson();
                }
                saveData();     
            }
            if(state==4)
            {
                
                previousTime=new Date().getTime();
                long t1=new Date().getTime();
                updateWork();
                long t2=new Date().getTime();
                int t3=(int)(t2-t1)/1000;
                sleepingTime=30-t3;
                if(sleepingTime<0)sleepingTime=1;
                sleepingTime=0;
                if(status!=200)state--;
            }
            if(state==5)
            {
                updateWork1();
                runningSerial++;
                //         SubmitWork();
                remaingTime[PositionNumber]=sleepingTime;
                set("5_1");
                sleep(sleepingTime);
                timeTaken=timeTaken();
                
                
                if(status==200&&state>3)
                {
                    remaing_links--;
                    completed_links[PositionNumber]++;
                    state=3;
                    
                    if(remaing_links==0)
                    {
                        set("Done");
                        state=10;
                        SubmitWork();
                        
                    }            }
                
                state++;
            }
            else if(state<6)
            {
                state++;
            }
            //
        }
        
    }
    
    void loadData()
    {
        System.out.println("load data");
        BufferedReader br=null;
        try
        {
            String d=""+new Date().getDate()+new Date().getMonth();
            File f=new File("test\\"+Data[PositionNumber][0]+d+".html");
            System.out.println("path is "+f.getAbsolutePath());
            if(f.isFile())
            {
                System.out.println("is a file ");
                try {
                    br=new BufferedReader(new FileReader(f));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                }
               String[] userData=br.readLine().split("<>");
               Data[PositionNumber][0]=userData[0];
               UserID[PositionNumber]=userData[1];
               cookie=userData[2];
               viewstate=userData[3];
               viewstateGenerator=userData[4];
            }
          
            
            else
            {
                state=0;
            }
            
        }
        catch(Exception m)
        {
            
        }
       
    }
    
    void saveData()
    {
        String data=Data[PositionNumber][0]+"<>"+ UserID[PositionNumber]+"<>"+cookie+"<>"+viewstate+"<>"+viewstateGenerator;
        String d=""+new Date().getDate()+new Date().getMonth();
        new Save(Data[PositionNumber][0]+d,data).start();
    }
    void set(String s)
    {
        messages[PositionNumber]=s;
    }
    void FetchCookie()
    {
        
        Cookie[] cookies = client.getState().getCookies();
        int i=0;
        cookie="";
        while(i<cookies.length)
        {
            cookie=cookie+";"+cookies[i].getName()+"="+cookies[i].getValue();
            i++;
        }
        try
        {
            cookie=cookie.trim();
            if(cookie.charAt(0)==';')cookie=cookie.substring(1);
            int n1=cookie.indexOf("UserID=");
            int n2=cookie.indexOf("&",n1);
            
            UserID[PositionNumber]=cookie.substring(n1+7,n2).trim();
        }
        catch(Exception m)
        {
            state=-1;
        }
    }
    
    int  timeTaken()
    {
        long  currentTime=new Date().getTime();
        long totalTime=currentTime-previousTime;
        int TotalSeconds=(int)totalTime/1000;
        messages[PositionNumber]=""+TotalSeconds+" S";
        
        return TotalSeconds;
    }
    
    void exit()
    {
        System.exit(0);
    }
    public String encode(String s)
    {
        String returnString="";
        try {
            returnString=  URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  returnString;
    }
    
    void waitForThread()
    {
        while(ThreadStatus[PositionNumber][state]==-1)
            try {
                Thread.sleep(1000);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }
    void ParseJson()
    {
        
        try {
            JSONObject obj = new JSONObject(response);
            String pageName= obj.getJSONObject("d").get("TodayTaskLists").toString();
            JSONArray obj1 = new JSONArray(pageName);
            remaing_links=0;
            for (int i = 0; i < obj1.length(); i++)
            {
                
                if(obj1.getJSONObject(i).getString("Stage").indexOf("A")!=-1)
                {
                    
                    links[remaing_links][0]=obj1.getJSONObject(i).getString("WorkID");
                    links[remaing_links][2]=obj1.getJSONObject(i).getString("Link");
                    links[remaing_links][3]=obj1.getJSONObject(i).getString("CampaignName");
                    try
                    {
                        links[remaing_links][1]=obj1.getJSONObject(i+1).getString("WorkID");
                    }
                    catch(Exception m)
                    {
                        links[remaing_links][1]=obj1.getJSONObject(i).getString("WorkID");
                    }
                    
                    remaing_links++;
                }
                else
                {
                    completed_links[PositionNumber]++;
                }
            }
            if(remaing_links==0)
            {
                System.out.println("No links From parsed Data"+PositionNumber);
                set("Done");
                SubmitWork();
                state=100;
            }
            //   for(int i=0;i<remaing_links;i++)System.out.println(i+":"+links[i][0]+","+links[i][2]+","+links[i][3]);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error parse json");
            System.out.println("restarting ");
            state=-1;   }
    }
    
    
    void updateWork()
    {
        set("UW");
        url="https://www.socialtrade.biz/User/TodayTask179.aspx/UpdateTaskWork";
        body="{user: {\"Username\":\""+Data[PositionNumber][0]+"\",\"WorkID\":"+links[runningSerial][0]+",\"Flag\":\"hand\",\"NextWorkID\":\""+links[runningSerial][1]+"\"}}";
        PostThread();
        
        System.out.println("Completetd  "+completed_links[PositionNumber]);
        status=ThreadStatus[PositionNumber][state];
    }
    void updateWork1()
    {
        
        url="https://www.socialtrade.biz/User/TodayTask179.aspx";
        body="ctl00%24ScriptManager1=ctl00%24ContentPlaceHolder1%24UpPanel1%7Cctl00%24ContentPlaceHolder1%24Button2&"
                + "hfHiddenFieldID="+byId("hfHiddenFieldID")+"&__EVENTTARGET=&"+byId("__EVENTARGUMENT")+"=&__LASTFOCUS="
                + "&__VIEWSTATE="+byId("__VIEWSTATE")+"&__VIEWSTATEGENERATOR="+byId("__VIEWSTATEGENERATOR")+""
                + "&ctl00%24ContentPlaceHolder1%24HFValue=&ctl00%24ContentPlaceHolder1%24HFWorkDonationID=&"
                + "ctl00%24ContentPlaceHolder1%24txtDonationStart=&ctl00%24ContentPlaceHolder1%24txtDonationEnd=&"
                + "ctl00%24ContentPlaceHolder1%24txtClick=&ctl00%24ContentPlaceHolder1%24hfuserid=&"
                + "ctl00%24ContentPlaceHolder1%24txtRemark=&__ASYNCPOST=true&ctl00%24ContentPlaceHolder1%24Button2=Refresh";
        body=body.replace("+","%2B").replace("/","%2F");
        hfHiddenFieldID=byId("hfHiddenFieldID");
        
        new ChildThread(url,body,cookie,PositionNumber,state).start();
        
        
    }
    
    
    void requestWork()
    {
        System.out.println("requesting work................");
        viewstate=byId("__VIEWSTATE");
        viewstateGenerator=byId("__VIEWSTATEGENERATOR");
        
        viewstate=viewstate.replace("+","%3D").replace("/","%2F").trim();
        body="__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE="+viewstate+"&__VIEWSTATEGENERATOR="+viewstateGenerator+"&ctl00%24ContentPlaceHolder1%24HFValue=&ctl00%24ContentPlaceHolder1%24hfuserid=&ctl00%24ContentPlaceHolder1%24cmdGetWork=Request+For+Work&ctl00%24ContentPlaceHolder1%24txtRemark=";
        url="https://www.socialtrade.biz/User/TodayTask.aspx";
        post();
        new Save("3_1",response).start();
        url="https://www.socialtrade.biz/User/TodayTask179.aspx";
        get();
        new Save("3_2",response).start();
        
    }
    
    void SubmitWork()
    {
        url="https://www.socialtrade.biz/User/TodayTask179.aspx";
        body="ctl00%24ScriptManager1=ctl00%24ContentPlaceHolder1%24UpPanel1%7Cctl00%24ContentPlaceHolder1%24cmdSubmit&hfHiddenFieldID="+hfHiddenFieldID+"&ctl00%24ContentPlaceHolder1%24HFValue=&ctl00%24ContentPlaceHolder1%24HFWorkDonationID=&ctl00%24ContentPlaceHolder1%24txtDonationStart=&ctl00%24ContentPlaceHolder1%24txtDonationEnd=&ctl00%24ContentPlaceHolder1%24txtClick=&ctl00%24ContentPlaceHolder1%24hfuserid=&ctl00%24ContentPlaceHolder1%24txtRemark=&__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE="+viewstate+"&__VIEWSTATEGENERATOR="+viewstateGenerator+"&__ASYNCPOST=true&ctl00%24ContentPlaceHolder1%24cmdSubmit=Submit%20Work";
        System.out.println(cookie);
        System.out.println(body);
        PostThread();
        
        new Save("submit",response).start();
        //   System.out.println("submitted");
    }
    
    
    
    void setImage()
    {
        Image image = null;
        try {
            URL url = new URL("https://www.socialtrade.biz/Photo1/636088785030959837.jpeg");
            image = ImageIO.read(url);
            
            ImageIcon icon = new ImageIcon(image);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
    }
    public void post()
    {
        isPostMethod=true;
        try {
            Postmethod= new PostMethod(url);
            Postmethod.addRequestHeader("Host","www.socialtrade.biz");
            Postmethod.addRequestHeader("User-Agent"," Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
            Postmethod.addRequestHeader("Accept","application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,*/*;q=0.5");
            Postmethod.addRequestHeader("Accept-Language","en-US,en;q=0.8");
            Postmethod.addRequestHeader("Accept-Encoding","gzip,deflate,sdch");
            Postmethod.addRequestHeader("X-Client-Data","CKK2yQEIxLbJAQj9lcoB");
            Postmethod.addRequestHeader("Connection","keepalive,Keep-Alive");
            Postmethod.addRequestHeader("Cookie",cookie);
            Postmethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
            Postmethod.setRequestBody(body);
            Postmethod.addRequestHeader("Content-Length",""+body.length());
            Postmethod.getFollowRedirects();
            Postmethod.setFollowRedirects(true);
            client = new HttpClient();
            client.setTimeout(20000);
            client.setConnectionTimeout(15000);
            status=    client.executeMethod(Postmethod);
            
            response=Postmethod.getResponseBodyAsString();
            
        }
        catch(Exception m)
        {
            post();
            m.printStackTrace();
        }
        
        
        
    }
    
    int Post()
    {
        status=-1;
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
            con.setRequestProperty("Cookie",cookie);
            con.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream( con.getOutputStream());
            wr.write( postData );
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response1 = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response1.append(inputLine);
            }
            status=con.getResponseCode();
            in.close();
            response=response1.toString();
        }
        catch(Exception m){
            m.printStackTrace();
            state--;
        }
        return status;
    }
    public void  getThread()
    {
        ThreadStatus[PositionNumber][state]=-1;
        new ChildThread(url, body, cookie,PositionNumber,state).start();
        waitForThread();
        response=ThreadResponse[PositionNumber][state];
    }
    public void  PostThread()
    {
        
        //    while(ThreadStatus[PositionNumber][state]!=200)
        {
        ThreadStatus[PositionNumber][state]=-1;
        new ChildThread(url, body, cookie,PositionNumber,state).start();
        waitForThread();
    }
        response=ThreadResponse[PositionNumber][state];
        //           System.out.println("thread response "+response);
    }
    private void get()  {
        
        try
        {
            
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie",cookie);
            con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response1 = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response1.append(inputLine);
            }
            in.close();
            
            //     System.out.println(state+" Status "+con.getResponseCode());
            response=response1.toString();
        }
        catch(Exception m)
        {
            m.printStackTrace();
        }
        
    }
    
    
    void sleep(int se)
    {
        try
        {
            Thread.sleep(se*1000);
        }
        catch(Exception m)
        {
            
        }
    }
    
    
    
    String byId(String s)
    {
        Element info=null;
        String returnstring="";
        try
        {
            doc = Jsoup.parse( response);
            info = doc.getElementById(s);
            returnstring=""+info.val().replace("=","%3D");
            System.out.println(s+",,,caledd--------------------------------------------------------"+returnstring);sleep(1);
        }
        catch(Exception m)
        {
            
        }
        return returnstring;
    }
    
    
}
