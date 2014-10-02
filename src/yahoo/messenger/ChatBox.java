/*
 * Parts of code are taken from http://www.ase.md/~aursu/ClientServerThreads.html
 * All credit for the basic chat function goes to them.
 */
package yahoo.messenger;

import static java.awt.event.KeyEvent.VK_ENTER;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author alexa_000
 */
public class ChatBox extends javax.swing.JFrame {

    /**
     * Creates new form YahooMessenger
     *
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    public ChatBox() throws MalformedURLException, IOException {  
        /* Updater Script */
        // Remove old files
        File updater = new File("Updater.jar");
        if (updater.exists()) {
            updater.delete();
        }
        
        File oldSettingsYM = new File(System.getProperty("user.home") + "/Documents/settingsYM.txt");
        if (oldSettingsYM.exists()) {
            oldSettingsYM.delete();
        }
        
        File AcerX = new File(System.getProperty("user.home") + "/Documents/AcerX/");
        if (!AcerX.exists()){
            AcerX.mkdir();
        }

        Intro.main(new String[0]);
        // Check if update exists
        URL website = new URL("http://aica.org.ro/images/FTP/version.txt");
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "/Documents/AcerX/version");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        BufferedReader x = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Documents/AcerX/version"));
        String latestVersion = x.readLine();
        if (myVersion < Integer.parseInt(latestVersion)) {
            // Get updater from server
            URL srv = new URL("http://aica.org.ro/images/FTP/Updater.jar");
            ReadableByteChannel rbc2 = Channels.newChannel(srv.openStream());
            FileOutputStream fos2 = new FileOutputStream("updater.jar");
            fos2.getChannel().transferFrom(rbc2, 0, Long.MAX_VALUE);

            // Start the updater
            Runtime.getRuntime().exec("cmd /c java -jar updater.jar");

            System.exit(0);

        }
        /* End of Updater Script */
        introStatus = 50;
        
        /* Check and download required files */
        File BUZZ = new File(System.getProperty("user.home") + "/Documents/AcerX/BUZZ.mp3");
        if (!BUZZ.exists()) {
            URL buzzURL = new URL ("http://aica.org.ro/images/FTP/BUZZ.mp3");
            ReadableByteChannel rbc3 = Channels.newChannel(buzzURL.openStream());
            FileOutputStream fos3 = new FileOutputStream(System.getProperty("user.home") + "/Documents/AcerX/BUZZ.mp3");
            fos3.getChannel().transferFrom(rbc3, 0, Long.MAX_VALUE);            
        }
        introStatus = 100;
        /* End of Check */
        
        
        /* Load saved preferences if there are any */
        File prefFile = new File(System.getProperty("user.home") + "/Documents/AcerX/settingsYM.txt");
        if (prefFile.exists()) {
            BufferedReader pref = new BufferedReader(new FileReader(prefFile));
            nickname = pref.readLine();
            directory = pref.readLine();
            String bool = pref.readLine();

            if (bool.equals("true")) {
                muted = true;
            } else {
                muted = false;
            }

            if ((nickname != null) && (!"".equals(nickname))) {
                nicknameFound = true;
            }

            if (("".equals(directory)) || (directory == null)) {
                directory = "C://tmp/";
            }
        }

        /* Initialize the client */
        initComponents();
        new chatClient().execute();

        /* Set the chatbox to listen HTML format */
        jTextPane1.setContentType("text/html");
        content.append("<html><body><font size=\"3\" face=\"Comic Sans MS\">");

        /* Set to send /quit to server when exiting */
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
        jScrollPane2 = new javax.swing.JScrollPane();
        messageBox = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        onlineUsers = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ChatBox v1.5");

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

        jButton2.setText("Images");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("BUZZ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jScrollPane4.setViewportView(jTextPane1);

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
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
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
        message = "/quit";
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Images.main(new String[0]);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        message = "/BUZZ";
    }//GEN-LAST:event_jButton3ActionPerformed

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
                content.append("Don't know about host " + host + "<br>");
                jTextPane1.setText(content.toString());
            } catch (IOException e) {
                content.append("Couldn't get I/O for the connection to the host "
                        + host + "<br>");
                jTextPane1.setText(content.toString());
            }

            /*
             * If everything has been initialized then we want to write some data to the
             * socket we have opened a connection to on the port portNumber.
             */
            if (clientSocket != null && os != null && is != null) {
                try {
                    /* Create a thread to read from the server. */
                    new Thread(new MultiThreadChatClient()).start();

                    /* Send my name to the server if it is saved */
                    if (nicknameFound) {
                        message = nickname;
                    }

                    /* Write to server */
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
                    content.append("IOException:  " + e);
                    jTextPane1.setText(content.toString());
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

                    /* BUZZ */
                    if (responseLine.contains("/BUZZ")) {
                        String u = "file:/" + System.getProperty("user.home") + "/Documents/AcerX/BUZZ.mp3";
                        u = u.replace("\\", "/");
                        Media bz = new Media(u);
                        MediaPlayer mediaPlayer = new MediaPlayer(bz);
                        mediaPlayer.play();
                        /*InputStream inDing = new FileInputStream(System.getProperty("user.home") + "/Documents/AcerX/BUZZ.mp3");
                        AudioStream asDing = new AudioStream(inDing);
                        AudioPlayer.player.start(asDing);*/
                    }

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
                        accepted = false;
                        declined = false;
                        receiverStatus = "Setting enviroment...";

                        Socket sk = new Socket("188.26.255.139", 5557);

                        /* Set variables for the server */
                        InputStream input = sk.getInputStream();
                        BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                        BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));

                        /* Read the filename */
                        String filename = inReader.readLine();
                        filenameString = filename;
                        if (filenameString.length() > 14) {
                            filenameString = filenameString.substring(0, 10) + "...";
                        }

                        /* Waiting for accept */
                        FileReceiver.main(new String[0]);
                        while (true) {
                            if (accepted || declined) {
                                break;
                            }
                            System.out.print("");
                        }

                        if (declined) {
                            outReader.write("ABORD\n");
                            outReader.flush();
                            continue;
                        }

                        getFile.main(new String[0]);
                        if (!filename.equals("")) {
                            receiverStatus = "Sending READY to server...";
                            outReader.write("READY\n");
                            outReader.flush();
                        }

                        /* Create a new file in the tmp directory using the filename */
                        receiverStatus = "Creating the file...";
                        FileOutputStream wr = new FileOutputStream(new File(directory + filename));

                        /* Get the buffer length from the server */
                        receiverStatus = "Handshaking...";
                        int len = Integer.parseInt(inReader.readLine());
                        byte[] buffer = new byte[len];

                        int bytesReceived;

                        receiverStatus = "Receiving...";
                        while ((bytesReceived = input.read(buffer)) > 0) {
                            /* Write to the file */
                            wr.write(buffer, 0, bytesReceived);
                        }
                        receiverStatus = "DONE!";
                        continue;
                    }

                    /* Normal chat messages */
                    /* Check if message contains <,>, or & and repair it */
                    int index = responseLine.indexOf("</B>");
                    for (int i = index+4; i < responseLine.length(); i++) {
                        if (responseLine.charAt(i) == '<') {
                            responseLine = responseLine.substring(0, i) + "&lt;" + responseLine.substring(i+1, responseLine.length());
                            i = i + 3;
                        }
                        if (responseLine.charAt(i) == '>') {
                            responseLine = responseLine.substring(0, i) + "&gt;" + responseLine.substring(i+1, responseLine.length());
                            i = i + 3;
                        }
                        if (responseLine.charAt(i) == '&') {
                            responseLine = responseLine.substring(0, i) + "&amp;" + responseLine.substring(i+1, responseLine.length());
                            i = i + 3;
                        }
                    }
                    content.append(responseLine).append("<br>");
                    jTextPane1.setText(content.toString());
                    jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());


                    /* Pplaying a sound when a normal chat message is received */
                    if (!muted) {
                        InputStream inDing = new FileInputStream(System.getenv("SystemRoot") + "/Media/Windows Pop-up Blocked.wav");
                        AudioStream asDing = new AudioStream(inDing);
                        AudioPlayer.player.start(asDing);
                    }

                    /* Check if the message contains a link and parse it */
                    /* JPG AND FACEBOOK(50%) SUPPORT */
                    if ((responseLine.contains(".jpg"))) {
                        int x = responseLine.indexOf("http");
                        int y = responseLine.indexOf(".jpg");

                        URL url = new URL(responseLine.substring(x, y + 4));
                        imgList.add(url.toString());
                        jButton2.setEnabled(true);

                        BufferedImage img = ImageIO.read(url);
                        int height, width;
                        height = img.getHeight();
                        width = img.getWidth();

                        if (responseLine.contains("https")) {
                            content.append("<img src=\"").append("http").append(responseLine.substring(x + 4, y + 4)).append("\" height=\"").append(height).append("\" width=\"").append(width).append("\"<br>");
                        } else {
                            content.append("<img src=\"").append(responseLine.substring(x, y + 4)).append("\" height=\"").append(height).append("\" width=\"").append(width).append("\"<br>");
                        }

                        jTextPane1.setText(content.toString());
                        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
                    }
                    
                    /* PNG SUPPORT */
                    if ((responseLine.contains(".png"))) {
                        int x = responseLine.indexOf("http");
                        int y = responseLine.indexOf(".png");

                        URL url = new URL(responseLine.substring(x, y + 4));
                        imgList.add(url.toString());
                        jButton2.setEnabled(true);

                        BufferedImage img = ImageIO.read(url);
                        int height, width;
                        height = img.getHeight();
                        width = img.getWidth();

                        if (responseLine.contains("https")) {
                            content.append("<img src=\"").append("http").append(responseLine.substring(x + 4, y + 4)).append("\" height=\"").append(height).append("\" width=\"").append(width).append("\"<br>");
                        } else {
                            content.append("<img src=\"").append(responseLine.substring(x, y + 4)).append("\" height=\"").append(height).append("\" width=\"").append(width).append("\"<br>");
                        }

                        jTextPane1.setText(content.toString());
                        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
                    }
                    
                    /* GIF SUPPORT */
                    if ((responseLine.endsWith(".gif"))) {
                        int x = responseLine.indexOf("http");
                        int y = responseLine.indexOf(".gif");

                        URL url = new URL(responseLine.substring(x, y + 4));
                        imgList.add(url.toString());
                        jButton2.setEnabled(true);

                        BufferedImage img = ImageIO.read(url);
                        int height, width;
                        height = img.getHeight();
                        width = img.getWidth();

                        content.append("<img src=\"").append(responseLine.substring(x, y + 4)).append("\" height=\"").append(height).append("\" width=\"").append(width).append("\"<br>");
                        jTextPane1.setText(content.toString());
                        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
                    }
                    
                    /* 9GAG NON-GIF SUPPORT */
                    if (responseLine.contains("http://9gag.com/gag/")) {
                        int x = responseLine.indexOf("http://9gag.com/gag/");

                        URL url = new URL("http://images-cdn.9gag.com/photo/" + responseLine.substring(x + 20, x + 27) + "_700b.jpg");
                        imgList.add(url.toString());
                        jButton2.setEnabled(true);

                        BufferedImage img = ImageIO.read(url);
                        int height, width;
                        height = img.getHeight();
                        width = img.getWidth();

                        content.append("<img src=\"http://images-cdn.9gag.com/photo/").append(responseLine.substring(x + 20, x + 27)).append("_700b.jpg").append("\" height=\"").append(height).append("\" width=\"").append(width).append("\"<br>");
                        jTextPane1.setText(content.toString());
                        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
                    }
                    if (responseLine.indexOf("*** Bye") != -1) {
                        break;
                    }
                }
                closed = true;
            } catch (IOException e) {
                content.append("IOException:  " + e);
                jTextPane1.setText(content.toString());
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
            java.util.logging.Logger.getLogger(ChatBox.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatBox.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatBox.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatBox.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ChatBox().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ChatBox.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private static javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private static javax.swing.JTextPane jTextPane1;
    private static javax.swing.JTextArea messageBox;
    private static javax.swing.JTextArea onlineUsers;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
    /* General variables */
    private static final int myVersion = 150;
    public static ArrayList<String> usersList = new ArrayList<String>();
    public static int introStatus = 0;

    /* Chat variables */
    public static String message;
    private static String lastMessage = null;
    private static StringBuilder content = new StringBuilder();
    private static String nickname;
    private static boolean nicknameFound = false;

    /* File transfer variables */
    public static boolean accepted = false;
    public static boolean declined = false;
    public static String receiver, filenameString, directory, receiverStatus;

    /* Sound system variables */
    public static boolean muted = false;

    /* Images system variables */
    public static ArrayList<String> imgList = new ArrayList<String>();
}
