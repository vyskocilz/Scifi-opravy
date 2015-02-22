/*
 * Created by JFormDesigner on Mon Jan 10 00:15:22 CET 2011
 */

package main;

import client.ClientUtils;
import data.Chat;
import data.ClientDataModel;
import ini.ClientProperty;
import log.Log;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Zdenek Vyskocil
 */
public class ClientApplication extends JFrame {

    public static ClientApplication dialog;

    public static void main(String[] args) {
        Log.init();
        JFrame.setDefaultLookAndFeelDecorated(true);
        dialog = new ClientApplication();
        dialog.setExtendedState(Frame.MAXIMIZED_BOTH);
        dialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        dialog.setVisible(true);
    }


    public ClientApplication() {
        initComponents();
        ClientDataModel.Instance.setOpravaPanel(opravaPanel, opravnaMainPanel, mapaPanel);
        setAlwaysOnTop(ClientProperty.getOnTop());
    }


    private void thisWindowOpened(WindowEvent e) {
        ClientUtils.start();
    }

    private void thisWindowClosing(WindowEvent e) {
        String pass = JOptionPane.showInputDialog(this, "Zadejte heslo k ukončení", "Konec?", JOptionPane.QUESTION_MESSAGE);
        if (ClientProperty.getClosePassword().equals(pass)) {
            ClientUtils.stop();
            System.exit(0);
        }
    }

    private void chatButtonActionPerformed(ActionEvent e) {
        if (chatEdit.getText().trim().length() > 0) {
            Chat.sendText(chatEdit.getText().trim());
            chatEdit.setText("");
        }
    }

    public JPanel getChatPanel() {
        return chatPanel;
    }

    private void vloziOpravuActionPerformed(ActionEvent e) {
        ClientUtils.sendKod(kodField.getText());
        kodField.setText("");
    }

    private void chatEditKeyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            chatButtonActionPerformed(null);
        }
    }

    private void createUIComponents() {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        panel1 = new JPanel();
        opravnaMainPanel = new JPanel();
        opravaScrolPanel = new JScrollPane();
        opravaPanel = new JPanel();
        panelNovaOprava = new JPanel();
        label1 = new JLabel();
        kodField = new JTextField();
        vloziOpravu = new JButton();
        chatPanel = new JPanel();
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        list1 = new JList();
        chatEditPanel = new JPanel();
        chatEdit = new JTextField();
        chatButton = new JButton();
        panel3 = new JScrollPane();
        mapaPanel = new MapaPanel();
        chatListModel = Chat.getChatListModel();
        clientDataModel1 = ClientDataModel.Instance;

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setAlwaysOnTop(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
            @Override
            public void windowOpened(WindowEvent e) {
                thisWindowOpened(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //======== opravnaMainPanel ========
            {
                opravnaMainPanel.setPreferredSize(new Dimension(600, 0));
                opravnaMainPanel.setLayout(new BorderLayout());

                //======== opravaScrolPanel ========
                {

                    //======== opravaPanel ========
                    {
                        opravaPanel.setLayout(new VerticalLayout());
                    }
                    opravaScrolPanel.setViewportView(opravaPanel);
                }
                opravnaMainPanel.add(opravaScrolPanel, BorderLayout.CENTER);
            }
            panel1.add(opravnaMainPanel, BorderLayout.CENTER);

            //======== panelNovaOprava ========
            {
                panelNovaOprava.setBorder(new TitledBorder("Nov\u00e1 z\u00e1vada"));
                panelNovaOprava.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- label1 ----
                label1.setText("K\u00f3d");
                panelNovaOprava.add(label1);

                //---- kodField ----
                kodField.setMinimumSize(new Dimension(200, 22));
                kodField.setPreferredSize(new Dimension(200, 22));
                panelNovaOprava.add(kodField);

                //---- vloziOpravu ----
                vloziOpravu.setText("Vlo\u017eit");
                vloziOpravu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        vloziOpravuActionPerformed(e);
                    }
                });
                panelNovaOprava.add(vloziOpravu);
            }
            panel1.add(panelNovaOprava, BorderLayout.PAGE_START);
        }
        contentPane.add(panel1, BorderLayout.LINE_START);

        //======== chatPanel ========
        {
            chatPanel.setBorder(new TitledBorder("Komunik\u00e1tor"));
            chatPanel.setLayout(new BorderLayout());

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout());

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(list1);
                }
                panel2.add(scrollPane2, BorderLayout.CENTER);

                //======== chatEditPanel ========
                {
                    chatEditPanel.setLayout(new BoxLayout(chatEditPanel, BoxLayout.X_AXIS));

                    //---- chatEdit ----
                    chatEdit.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            chatEditKeyTyped(e);
                        }
                    });
                    chatEditPanel.add(chatEdit);

                    //---- chatButton ----
                    chatButton.setText("Odeslat");
                    chatButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            chatButtonActionPerformed(e);
                        }
                    });
                    chatEditPanel.add(chatButton);
                }
                panel2.add(chatEditPanel, BorderLayout.NORTH);
            }
            chatPanel.add(panel2, BorderLayout.CENTER);
        }
        contentPane.add(chatPanel, BorderLayout.PAGE_END);

        //======== panel3 ========
        {
            panel3.setBorder(new TitledBorder("Mapa"));

            //---- mapaPanel ----
            mapaPanel.setMinimumSize(new Dimension(600, 0));
            panel3.setViewportView(mapaPanel);
        }
        contentPane.add(panel3, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_ONCE,
            chatListModel, list1));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
            clientDataModel1, BeanProperty.create("chatEnabled"),
            chatPanel, BeanProperty.create("visible")));
        bindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JPanel panel1;
    private JPanel opravnaMainPanel;
    private JScrollPane opravaScrolPanel;
    private JPanel opravaPanel;
    private JPanel panelNovaOprava;
    private JLabel label1;
    private JTextField kodField;
    private JButton vloziOpravu;
    private JPanel chatPanel;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JList list1;
    private JPanel chatEditPanel;
    private JTextField chatEdit;
    private JButton chatButton;
    private JScrollPane panel3;
    private MapaPanel mapaPanel;
    private ObservableList<java.lang.String> chatListModel;
    private ClientDataModel clientDataModel1;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
