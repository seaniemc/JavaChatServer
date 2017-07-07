import java.awt.*;
import java.io.*;

/**
 * Created by smcgrath on 7/7/2017.
 */
public class ChatClient extends Frame implements Runnable {

    protected DataInputStream in;
    protected DataOutputStream out;
    protected TextArea output;
    protected TextField input;
    protected Thread listener;

     public ChatClient (String title, InputStream in, OutputStream out){
         super(title);
         this.i = new DataInputStream(new BufferedInputStream(in));
         this.o = new DataOutputStream(new BufferedOutputStream(out));
         setLayout(new BorderLayout());
         add("Center", output = new TextArea());
         output.setEditable(false);
         add("South", input = new TextField());
         pack();
         show();
         input.requestFocus();
         listener = new Thread(this);
         listener.start();
     }
    public void run () {
         try{
             while (true) {
                 //while string arrives appended to output region of GUI
                 String line = in.readUTF();
                 output.appendText(line + "\n");
                }
                //throws exeception if connection to server is lost
             }catch (IOException ex) {
             ex.printStackTrace();
         }finally {
             listener = null;
             input.hide();
             validate();
             try{
                 out.close();
             }catch (IOException ex){
                 ex.printStackTrace();
             }
         }

    }
    // public boolean handleEvent (Event e) ...
    // public static void main (String args[]) throws IOException ..
}
