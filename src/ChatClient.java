import java.awt.*;
import java.io.*;

/**
 * Created by smcgrath on 7/7/2017.
 */
@SuppressWarnings("ALL")
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
                //throws exception if connection to server is lost
             }catch (IOException ex) {
             ex.printStackTrace();
         }finally {
             //signals that the thread is finished
             listener = null;
             input.hide();
             validate();
             try{
                 //close output stream
                 out.close();
             }catch (IOException ex){
                 ex.printStackTrace();
             }
         }

    }
    //Method checks to for 2 UI events
    public boolean handleEvent (Event e) {
         //checks to see if the hits return in the TextField
         if((e.target == input)&& (e.id == Event.ACTION_EVENT)){
             try {
                 //write message to output stream
                 out.writeUTF((String)e.arg);
                 //call flush
                 out.flush();
             } catch (IOException e1) {
                 e1.printStackTrace();
                 //if we get an error we kill the thread
                 listener.stop();
             }
             input.setText("");
             return true;
             //if user closes frame we kill the thread and hide the frame
         }else if((e.target == this)&& (e.id == Event.WINDOW_DESTROY)){
             if(listener != null)
                 listener.stop();
             hide();
             return true;
         }
         return super.handleEvent(e);
    }
     public static void main (String args[]) throws IOException {
         
     }
}
