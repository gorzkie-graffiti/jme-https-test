import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

public class TestMIDlet extends MIDlet implements CommandListener {
    private Display display;
    private Form form;
    private StringItem result;
    private Command testCmd;

    public void startApp() {
        display = Display.getDisplay(this);
        form = new Form("HTTPS Test");
        result = new StringItem("Status:", "Klikaj...");
        testCmd = new Command("Testuj", Command.ITEM, 1);
        form.append(result);
        form.addCommand(testCmd);
        form.setCommandListener(this);
        display.setCurrent(form);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == testCmd) {
            result.setLabel("Status:");
            result.setText("Łączenie z proxy...");
            new Thread(new Runnable() {
                public void run() {
                    doRequest();
                }
            }).start();
        }
    }

    void doRequest() {
        HttpConnection conn = null;
        InputStream is = null;
        try {
            String url = "http://jme-https-test.mateusz-pierzchala2005.workers.dev/";
            conn = (HttpConnection) Connector.open(url);
            conn.setRequestMethod(HttpConnection.GET);
            is = conn.openInputStream();
            byte[] buf = new byte[256];
            int len = is.read(buf);
            
            String resp = "Brak odpowiedzi";
            if (len != -1) {
                resp = new String(buf, 0, len);
            }
            
            result.setLabel("Odpowiedź:");
            result.setText(resp);
        } catch (Throwable e) {
            result.setLabel("Błąd:");
            result.setText(e.toString());
        } finally {
            try { if (is != null) is.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    public void pauseApp() {}
    public void destroyApp(boolean u) {}
}
