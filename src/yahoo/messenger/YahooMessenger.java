/*
 * Parts of code are taken from http://www.ase.md/~aursu/ClientServerThreads.html
 * All credit for the basic chat function goes to them.
 */
package yahoo.messenger;

import static java.awt.event.KeyEvent.VK_ENTER;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author alexa_000
 */
public class YahooMessenger extends javax.swing.JFrame {

    /**
     * Creates new form YahooMessenger
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    public YahooMessenger() throws MalformedURLException, IOException {
        /* Updater Script */
        // Remove old files
        File updater = new File("Updater.jar");
        if (updater.exists()) {
            updater.delete();
        }
        File version = new File("version.txt");
        version.delete();

        // Check if update exists
        if (betaVersion == false) {
            URL website = new URL("http://aica.org.ro/images/FTP/version.txt");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("version.txt");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            BufferedReader x = new BufferedReader(new FileReader("version.txt"));
            String latestVersion = x.readLine();
            if (!myVersion.equals(latestVersion)) {
                // Get updater from server
                URL srv = new URL("http://aica.org.ro/images/FTP/Updater.jpg");
                ReadableByteChannel rbc2 = Channels.newChannel(srv.openStream());
                FileOutputStream fos2 = new FileOutputStream("updater.jar");
                fos2.getChannel().transferFrom(rbc2, 0, Long.MAX_VALUE);

                // Start the updater
                Runtime.getRuntime().exec("cmd /c java -jar updater.jar");

                System.exit(0);

            }
        }

        /* End of Updater Script */
        initComponents();

        new chatClient().execute();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                message = "/quit";
                System.exit(0);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatBox = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        messageBox = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        onlineUsers = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Yahooo Messenger v1.0");
        setResizable(false);

        chatBox.setColumns(20);
        chatBox.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        chatBox.setRows(5);
        chatBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        chatBox.setDoubleBuffered(true);
        chatBox.setEnabled(false);
        jScrollPane1.setViewportView(chatBox);

        messageBox.setColumns(20);
        messageBox.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        messageBox.setRows(5);
        messageBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageBoxKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(messageBox);

        sendButton.setText("SEND");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Send File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        onlineUsers.setColumns(20);
        onlineUsers.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N
        onlineUsers.setRows(5);
        onlineUsers.setEnabled(false);
        jScrollPane3.setViewportView(onlineUsers);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Preferences");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        message = messageBox.getText();
        messageBox.setText("");
    }//GEN-LAST:event_sendButtonActionPerformed

    private void messageBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageBoxKeyPressed
        if (evt.getKeyCode() == VK_ENTER) {
            message = messageBox.getText();
            messageBox.setText("");
            evt.consume();
        }
    }//GEN-LAST:event_messageBoxKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SendFile.main(new String[0]);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Preferences.main(new String[0]);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    class chatClient extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            MultiThreadChatClient.main(new String[0]);
            return 666;
        }

    }

    static class MultiThreadChatClient implements Runnable {

        // The client socket
        public static Socket clientSocket = null;
        // The output stream
        public static PrintStream os = null;
        // The input stream
        public static DataInputStream is = null;

        private static String inputLine = null;
        private static boolean closed = false;

        public static void main(String[] args) {

            // The default port.
            int portNumber = 5556;
            // The default host.
            String host = "188.26.255.139";

            /*
             * Open a socket on a given host and port. Open input and output streams.
             */
            try {
                clientSocket = new Socket(host, portNumber);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new DataInputStream(clientSocket.getInputStream());
            } catch (UnknownHostException e) {
                chatBox.append("Don't know about host " + host + "\n");
            } catch (IOException e) {
                chatBox.append("Couldn't get I/O for the connection to the host "
                        + host + "\n");
            }

            /*
             * If everything has been initialized then we want to write some data to the
             * socket we have opened a connection to on the port portNumber.
             */
            if (clientSocket != null && os != null && is != null) {
                try {
                    /* Create a thread to read from the server. */
                    new Thread(new MultiThreadChatClient()).start();
                    while (!closed) {
                        inputLine = message;
                        System.out.print("");
                        if ((inputLine != null) && (!inputLine.equals(lastMessage))) {
                            os.println(inputLine);
                            lastMessage = inputLine;
                        }
                    }

                    /*
                     * Close the output stream, close the input stream, close the socket.
                     */
                    os.close();
                    is.close();
                    clientSocket.close();
                } catch (IOException e) {
                    chatBox.append("IOException:  " + e);
                }
            }
        }

        /*
         * Create a thread to read from the server. (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            /*
             * Keep on reading from the socket till we receive "Bye" from the
             * server. Once we received that then we want to break.
             */
            String responseLine, users;
            try {
                while ((responseLine = is.readLine()) != null) {

                    /* Users online */
                    if (responseLine.startsWith("/users")) {
                        onlineUsers.setText("");
                        usersList.clear();
                        while (!(users = is.readLine()).equals("/usersEnd")) {
                            onlineUsers.append(users.substring(1) + "\n");
                            usersList.add(users.substring(1));
                        }
                        continue;
                    }

                    /* File transfer protocol */
                    if (responseLine.startsWith("/send")) {
                        long sstart, scost, sspeed, stotal;
                        accepted = false;
                        declined = false;

                        Socket sk = new Socket("188.26.255.139", 5557);

                        /* Set variables for the server */
                        InputStream input = sk.getInputStream();
                        BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                        BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));

                        /* Read the filename */
                        String filename = inReader.readLine();
                        filenameString = filename;
                        if(filenameString.length() > 14){
                            filenameString = filenameString.substring(0, 10) + "...";                                     
                        }
                        
                        /* Waiting for accept */
                        FileReceiver.main(new String[0]);
                        while(true){
                            if (accepted || declined)
                                break;
                            System.out.print("");
                        }
                        
                        if(declined){
                            outReader.write("ABORD\n");
                            outReader.flush();
                            continue;
                        }

                        if (!filename.equals("")) {
                            outReader.write("READY\n");
                            outReader.flush();
                        }

                        /* Create a new file in the tmp directory using the filename */
                        FileOutputStream wr = new FileOutputStream(new File("C://tmp/" + filename));

                        /* Get the buffer length from the server */
                        int len = Integer.parseInt(inReader.readLine());
                        byte[] buffer = new byte[len];

                        int bytesReceived;

                        while ((bytesReceived = input.read(buffer)) > 0) {
                            /* Write to the file */
                            wr.write(buffer, 0, bytesReceived);                            
                        }
                        continue;
                    }

                    /* Normal chat messages */
                    chatBox.append(responseLine + "\n");
                    chatBox.setCaretPosition(chatBox.getDocument().getLength());
                    if (responseLine.indexOf("*** Bye") != -1) {
                        break;
                    }
                }
                closed = true;
            } catch (IOException e) {
                chatBox.append("IOException:  " + e);
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(YahooMessenger.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YahooMessenger.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YahooMessenger.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YahooMessenger.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new YahooMessenger().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(YahooMessenger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea chatBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTextArea messageBox;
    private static javax.swing.JTextArea onlineUsers;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
    private static String message;
    private static String lastMessage = null;
    public static ArrayList<String> usersList = new ArrayList<String>();
    public static final String myVersion = "101";
    public static final boolean betaVersion = true;
    public static boolean accepted = false;
    public static boolean declined = false;
    public static String receiver, filenameString;
}
