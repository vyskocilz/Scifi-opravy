/*
 * Created by JFormDesigner on Sat Jan 08 22:22:25 CET 2011
 */

package main;

import chat.Chat;
import chat.ChatServerData;
import data.ClientType;
import ini.Theme;
import ini.VersionProperty;
import log.Log;
import oprava.Oprava;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import server.ServerUtils;
import urovne.OpravaPopisDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static javax.swing.JOptionPane.YES_OPTION;

/**
 * @author Zdenek Vyskocil
 */
public class Application extends JFrame {

    public static ClientData clientData = new ClientData();
    final JFileChooser fc = new JFileChooser();


    public static void main(String[] args) {
        Theme.init();
        Log.init();
        clientData.init();
        Application dialog = new Application();
        dialog.setVisible(true);
    }

    public Application() {
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(false);
        fc.setFileHidingEnabled(true);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String ex = getExtension(f);
                return ex != null && "opravy".equalsIgnoreCase(ex);
            }

            @Override
            public String getDescription() {
                return "Scifi-opravy soubory (*.opravy)";
            }
        });
        initComponents();
        this.setTitle(this.getTitle() + " (" + VersionProperty.getVersion() + ")");
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private void thisWindowClosing(WindowEvent e) {
        if (YES_OPTION == JOptionPane.showConfirmDialog(null, "Opravdu ukončit server?", "Konec?", JOptionPane.YES_NO_OPTION)) {
            ServerUtils.stopServer();
            System.exit(0);
        }
    }

    private void startServerButtonActionPerformed(ActionEvent e) {
        startServerButton.setEnabled(false);
        ServerUtils.startServer();
        stopServerButton.setEnabled(true);
        zdrojeUlozit.setEnabled(false);
        zdrojeNahrat.setEnabled(false);
    }

    private void stopServerButtonActionPerformed(ActionEvent e) {
        stopServerButton.setEnabled(false);
        ServerUtils.stopServer();
        startServerButton.setEnabled(true);
        zdrojeUlozit.setEnabled(true);
        zdrojeNahrat.setEnabled(true);
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void chatButtonActionPerformed(ActionEvent e) {
        if (chatEdit.getText().trim().length() > 0) {
            Chat.writeText(ClientType.POCITAC, chatEdit.getText().trim());
            chatEdit.setText("");
        }
    }

    private void ulozitActionPerformed(ActionEvent e) {
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setDialogTitle("Uložit nastavení");
        int returnVal = fc.showDialog(this, "Uložit");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getAbsolutePath();
            String ex = getExtension(fc.getSelectedFile());
            if (!"opravy".equalsIgnoreCase(ex)) {
                fileName += ".opravy";
            }
            clientData.save(fileName);
        }
    }

    private void nahratActionPerformed(ActionEvent e) {
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        fc.setDialogTitle("Nahrát nastavení");
        int returnVal = fc.showDialog(this, "Nahrát");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String ex = getExtension(fc.getSelectedFile());
            if ("opravy".equalsIgnoreCase(ex)) {
                clientData.load(fc.getSelectedFile().getAbsolutePath(), mapaPanel);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Vložte soubor s příponou '.opravy'!", "Špatný soubor",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void pridatOpravaButtonActionPerformed(ActionEvent e) {
        clientData.getOpravy().createNewElement();
    }

    private void smazatOpravaButtonActionPerformed(ActionEvent e) {
        Oprava oprava = opravyListModel.get(opravyTable.getSelectedRow());
        if (clientData.getOpravy().removeElement(opravyTable.getSelectedRow())) {
            mapaPanel.removeOprava(oprava);
        }
    }

    private void zmenitPopisButtonActionPerformed(ActionEvent e) {
        if (opravyTable.getSelectedRow() >= 0) {
            OpravaPopisDialog dialog = new OpravaPopisDialog
                    (this, opravyListModel.get(opravyTable.getSelectedRow()));
            dialog.setVisible(true);
        }
    }

    private void oznacitButtonActionPerformed(ActionEvent e) {
        if (opravyTable.getSelectedRow() >= 0) {
            mapaPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mapaPanel.removeMouseListener(this);
                    Oprava oprava = opravyListModel.get(opravyTable.getSelectedRow());
                    oprava.setShowCross(true);
                    oprava.setCrossX(e.getX() - 8);
                    oprava.setCrossY(e.getY() - 8);
                    mapaPanel.addOprava(oprava);
                }
            });
        }
    }

    private void chatEnableButtonActionPerformed(ActionEvent e) {
        chatServerData1.setChatEnabled(!chatServerData1.isChatEnabled());
    }

    private void chatEditKeyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            chatButtonActionPerformed(null);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        mainPanel = new JPanel();
        opravyPanel = new JPanel();
        opravyToolbar = new JToolBar();
        pridatOpravaButton = new JButton();
        smazatOpravaButton = new JButton();
        zmenitPopisButton = new JButton();
        oznacitButton = new JButton();
        scrollPane1 = new JScrollPane();
        opravyTable = new JTable();
        panel3 = new JScrollPane();
        mapaPanel = new MapaPanel();
        mainToolbar = new JToolBar();
        startServerButton = new JButton();
        stopServerButton = new JButton();
        zdrojeUlozit = new JButton();
        zdrojeNahrat = new JButton();
        chatEnableButton = new JButton();
        panel1 = new JPanel();
        chatPanel = new JPanel();
        panel2 = new JPanel();
        chatListPanel = new JScrollPane();
        chatList = new JList();
        chatEditPanel = new JPanel();
        chatEdit = new JTextField();
        chatButton = new JButton();
        clientPanel = new JPanel();
        clinetScrollPane = new JScrollPane();
        clientTable = new JTable();
        clientsListModel = ServerUtils.getServerClients();
        chatListModel = Chat.getChatListModel();
        opravyListModel = clientData.getOpravy().getList();
        chatServerData1 = clientData.getChatServerData();

        //======== this ========
        setTitle("Server - Opravy");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== mainPanel ========
        {
            mainPanel.setLayout(new GridLayout(1, 2));

            //======== opravyPanel ========
            {
                opravyPanel.setBorder(new TitledBorder("Opravy"));
                opravyPanel.setMinimumSize(new Dimension(300, 84));
                opravyPanel.setPreferredSize(new Dimension(700, 477));
                opravyPanel.setLayout(new BorderLayout());

                //======== opravyToolbar ========
                {
                    opravyToolbar.setFloatable(false);

                    //---- pridatOpravaButton ----
                    pridatOpravaButton.setText("Pridat");
                    pridatOpravaButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            pridatOpravaButtonActionPerformed(e);
                        }
                    });
                    opravyToolbar.add(pridatOpravaButton);

                    //---- smazatOpravaButton ----
                    smazatOpravaButton.setText("Smazat");
                    smazatOpravaButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            smazatOpravaButtonActionPerformed(e);
                        }
                    });
                    opravyToolbar.add(smazatOpravaButton);

                    //---- zmenitPopisButton ----
                    zmenitPopisButton.setText("Zm\u011bnit popis");
                    zmenitPopisButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            zmenitPopisButtonActionPerformed(e);
                        }
                    });
                    opravyToolbar.add(zmenitPopisButton);

                    //---- oznacitButton ----
                    oznacitButton.setText("Ozna\u010dit na map\u011b");
                    oznacitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            oznacitButtonActionPerformed(e);
                        }
                    });
                    opravyToolbar.add(oznacitButton);
                }
                opravyPanel.add(opravyToolbar, BorderLayout.PAGE_START);

                //======== scrollPane1 ========
                {
                    scrollPane1.setPreferredSize(new Dimension(700, 419));

                    //---- opravyTable ----
                    opravyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    opravyTable.setMinimumSize(new Dimension(135, 10));
                    scrollPane1.setViewportView(opravyTable);
                }
                opravyPanel.add(scrollPane1, BorderLayout.CENTER);
            }
            mainPanel.add(opravyPanel);

            //======== panel3 ========
            {
                panel3.setBorder(new TitledBorder("Mapa"));
                panel3.setMinimumSize(new Dimension(400, 36));
                panel3.setPreferredSize(new Dimension(400, 36));

                //======== mapaPanel ========
                {
                    mapaPanel.setBackground(Color.white);
                    mapaPanel.setLayout(new BorderLayout());
                }
                panel3.setViewportView(mapaPanel);
            }
            mainPanel.add(panel3);
        }
        contentPane.add(mainPanel, BorderLayout.CENTER);

        //======== mainToolbar ========
        {
            mainToolbar.setFloatable(false);

            //---- startServerButton ----
            startServerButton.setText("Spustit server");
            startServerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    startServerButtonActionPerformed(e);
                }
            });
            mainToolbar.add(startServerButton);

            //---- stopServerButton ----
            stopServerButton.setText("Zastavit server");
            stopServerButton.setEnabled(false);
            stopServerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    stopServerButtonActionPerformed(e);
                }
            });
            mainToolbar.add(stopServerButton);

            //---- zdrojeUlozit ----
            zdrojeUlozit.setText("Ulozit nastaveni");
            zdrojeUlozit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ulozitActionPerformed(e);
                }
            });
            mainToolbar.add(zdrojeUlozit);

            //---- zdrojeNahrat ----
            zdrojeNahrat.setText("Nahrat nastaveni");
            zdrojeNahrat.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nahratActionPerformed(e);
                }
            });
            mainToolbar.add(zdrojeNahrat);

            //---- chatEnableButton ----
            chatEnableButton.setText("text");
            chatEnableButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chatEnableButtonActionPerformed(e);
                }
            });
            mainToolbar.add(chatEnableButton);
        }
        contentPane.add(mainToolbar, BorderLayout.PAGE_START);

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //======== chatPanel ========
            {
                chatPanel.setBorder(new TitledBorder("Chat"));
                chatPanel.setMinimumSize(new Dimension(300, 50));
                chatPanel.setPreferredSize(new Dimension(300, 50));
                chatPanel.setMaximumSize(new Dimension(300, 50));
                chatPanel.setLayout(new BorderLayout());

                //======== panel2 ========
                {
                    panel2.setLayout(new BorderLayout());

                    //======== chatListPanel ========
                    {

                        //---- chatList ----
                        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        chatList.setEnabled(false);
                        chatListPanel.setViewportView(chatList);
                    }
                    panel2.add(chatListPanel, BorderLayout.CENTER);

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
            panel1.add(chatPanel, BorderLayout.CENTER);

            //======== clientPanel ========
            {
                clientPanel.setBorder(new TitledBorder("Klienti"));
                clientPanel.setMaximumSize(new Dimension(300, 150));
                clientPanel.setPreferredSize(new Dimension(300, 150));
                clientPanel.setMinimumSize(new Dimension(300, 150));
                clientPanel.setLayout(new BorderLayout());

                //======== clinetScrollPane ========
                {

                    //---- clientTable ----
                    clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    clinetScrollPane.setViewportView(clientTable);
                }
                clientPanel.add(clinetScrollPane, BorderLayout.CENTER);
            }
            panel1.add(clientPanel, BorderLayout.LINE_END);
        }
        contentPane.add(panel1, BorderLayout.PAGE_END);
        setSize(1080, 600);
        setLocationRelativeTo(null);

        //---- bindings ----
        bindingGroup = new BindingGroup();
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    clientsListModel, clientTable);
            binding.setEditable(false);
            binding.addColumnBinding(BeanProperty.create("clientType"))
                    .setColumnName("Klient")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("clientIp"))
                    .setColumnName("Ip adrresa")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("connected"))
                    .setColumnName("Connected")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("port"))
                    .setColumnName("Port")
                    .setColumnClass(Integer.class);
            bindingGroup.addBinding(binding);
            binding.bind();
        }
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ,
                chatListModel, chatList));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
                chatListModel, ELProperty.create("${length}"),
                chatList, BeanProperty.create("selectedIndex")));
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    opravyListModel, opravyTable);
            binding.addColumnBinding(BeanProperty.create("kod"))
                    .setColumnName("K\u00f3d")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("nazev"))
                    .setColumnName("N\u00e1zev")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("popis"))
                    .setColumnName("Popis")
                    .setColumnClass(String.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("aktivni"))
                    .setColumnName("Aktivn\u00ed")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("viditelnost"))
                    .setColumnName("Viditelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("vyresena"))
                    .setColumnName("Vy\u0159e\u0161eno")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("varovani"))
                    .setColumnName("Varov\u00e1n\u00ed")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("vystraha"))
                    .setColumnName("V\u00fdstraha")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("showCross"))
                    .setColumnName("Na map\u011b")
                    .setColumnClass(Boolean.class);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
                chatServerData1, BeanProperty.create("chatButtonTitle"),
                chatEnableButton, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
                chatServerData1, BeanProperty.create("chatEnabled"),
                chatEdit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
                chatServerData1, BeanProperty.create("chatEnabled"),
                chatButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
                chatServerData1, BeanProperty.create("chatEnabled"),
                chatList, BeanProperty.create("enabled")));
        bindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JPanel mainPanel;
    private JPanel opravyPanel;
    private JToolBar opravyToolbar;
    private JButton pridatOpravaButton;
    private JButton smazatOpravaButton;
    private JButton zmenitPopisButton;
    private JButton oznacitButton;
    private JScrollPane scrollPane1;
    private JTable opravyTable;
    private JScrollPane panel3;
    private MapaPanel mapaPanel;
    private JToolBar mainToolbar;
    private JButton startServerButton;
    private JButton stopServerButton;
    private JButton zdrojeUlozit;
    private JButton zdrojeNahrat;
    private JButton chatEnableButton;
    private JPanel panel1;
    private JPanel chatPanel;
    private JPanel panel2;
    private JScrollPane chatListPanel;
    private JList chatList;
    private JPanel chatEditPanel;
    private JTextField chatEdit;
    private JButton chatButton;
    private JPanel clientPanel;
    private JScrollPane clinetScrollPane;
    private JTable clientTable;
    private ObservableList<server.ServerClient> clientsListModel;
    private ObservableList<java.lang.String> chatListModel;
    private ObservableList<oprava.Oprava> opravyListModel;
    private ChatServerData chatServerData1;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
