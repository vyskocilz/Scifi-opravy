package komponenty;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class OpravaKomponenta extends JPanel implements Serializable {
    private JScrollPane opravaScrollPane;
    private JTextArea opravaPopisek;
    Timer blinkTimer = new Timer(500, new Blinker());

    public OpravaKomponenta() {
        super();
        opravaScrollPane = new JScrollPane();
        opravaPopisek = new JTextArea();
        setBorder(new TitledBorder(""));
        setLayout(new BorderLayout());

        //======== opravaScrollPane ========
        {

            //---- opravaPopisek ----
            opravaPopisek.setEditable(false);
            opravaPopisek.setBackground(Color.lightGray);
            opravaScrollPane.setViewportView(opravaPopisek);
        }
        add(opravaScrollPane);

    }

    public void setNazev(String jmeno) {
        ((TitledBorder) getBorder()).setTitle(jmeno);
        repaint();
    }


    public void setPopis(String popis) {
        opravaPopisek.setText(popis);
        opravaPopisek.repaint();
    }

    public void setBarva(boolean vyreseno, boolean vystraha, boolean varovani) {
        if (blinkTimer.isRunning()) {
            blinkTimer.stop();
        }
        if (vyreseno) {
            opravaPopisek.setBackground(Color.GREEN);
        } else if (vystraha) {
            blinkTimer.start();
        } else if (varovani) {
            opravaPopisek.setBackground(Color.RED);
        } else {
            opravaPopisek.setBackground(Color.lightGray);
        }
    }

    class Blinker implements ActionListener {
        boolean on = false;

        public void actionPerformed(ActionEvent e) {
            // blink the label background on and off
            opravaPopisek.setBackground(on ? Color.RED : Color.lightGray);
            on = !on;
        }
    }
}
