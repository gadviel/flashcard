import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FlashcardPlayer {
    
    private JTextArea display;
    private ArrayList<FlashCard> cardList;
    private Iterator<FlashCard> cardIterator;
    private FlashCard currentCard;
    private JButton showAnswer;
    private JFrame frame;
    private boolean isShowAnswer;
    private JPanel mainPanel;

    public FlashcardPlayer (){

        // BUILD UI
        frame = new JFrame("Flash Card Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Font mFont = new Font ("Helvetica Neue", Font.BOLD, 21);

        display = new JTextArea(5, 16);
        display.setFont(mFont);
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);

        JScrollPane qJScrollPane = new JScrollPane(display);
        qJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        qJScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        showAnswer = new JButton("Show Answer");

        // MAIN PANEL COMPONENTS
        mainPanel = new JPanel();
        mainPanel.add(qJScrollPane);
        mainPanel.add(showAnswer);
        showAnswer.addActionListener(new NextCardListener());

        // MENU BAR
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadJMenuItem = new JMenuItem("Load Card Set");
        JMenuItem backMenuItem = new JMenuItem("Back");
        menuBar.add(fileMenu);
        fileMenu.add(loadJMenuItem);
        fileMenu.add(backMenuItem);
        loadJMenuItem.addActionListener(new OpenMenuListener());
        backMenuItem.addActionListener(new BackMenuItemListener());

        // ADD TO FRAME
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(480, 240);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src\\icon.jpg").getImage());
    }


    // PUBLIC STATIC VOID MAIN (STRING [] ARGS)
public static void main(String[] args) {

    try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FlashcardPlayer();
            }
        });
    }

    
// LISTERNERS AND METHODS
    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isShowAnswer) {
                display.setText(currentCard.getAnswer());
                showAnswer.setText("Next Card");
                isShowAnswer = false;
            } else {
                if (cardIterator.hasNext()) {
                    showNextCard();
                } else {
                    display.setText("That was the last card.");
                    showAnswer.setEnabled(false);
                }
            }
        }
    }


    class OpenMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showOpenDialog(frame);
            if (ret == JFileChooser.APPROVE_OPTION) {
                loadFile(fileOpen.getSelectedFile());
            }
        }
    }


    class BackMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new FlashcardSelection().setVisible(true);
        }
    }


    private void loadFile(File selectedFile) {
        cardList = new ArrayList<FlashCard>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't read the card file");
            e.printStackTrace();
        }

        // FIRST CARD
        cardIterator = cardList.iterator();
        showNextCard();
    }


    private void makeCard(String lineToParse) {
        String[] result = lineToParse.split("/");
        FlashCard card = new FlashCard(result[0], result[1]);
        cardList.add(card);
        Collections.shuffle(cardList);
    }


    private void showNextCard() {
        currentCard = cardIterator.next();
        display.setText(currentCard.getQuestion());
        showAnswer.setText("Show Answer");
        isShowAnswer = true;
    }
}