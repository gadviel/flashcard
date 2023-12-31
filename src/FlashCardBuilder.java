import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;


    // BUILD THE USER INTERFACE
    public FlashCardBuilder() {
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("src\\icon.jpg").getImage());
        frame.setBounds(100, 100, 563, 312);
        frame.setResizable(false);
        

        // PANEL
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setBorder(new LineBorder(new Color(0, 0, 66, 10), 5));
		frame.setContentPane(mainPanel);
		mainPanel.setLayout(null);

        // FONT
        Font font = new Font("Helvetica Neue", Font.BOLD, 21);

        // QUESTION
        question = new JTextArea(5, 16);
        question.setBounds(21, 33, 222, 176);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(font);
        JScrollPane questionScroll = new JScrollPane(question);
        questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(question);

        // ANSWER
        answer = new JTextArea(5, 16);
        answer.setBounds(295, 33, 222, 176);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(font);
        JScrollPane answerScroll = new JScrollPane(answer);
        answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(answer);

        JButton nextButton = new JButton("Next Card");
        nextButton.setForeground(Color.BLACK);
        nextButton.setFont(new Font("Garamond", Font.PLAIN, 12));
        nextButton.setBackground(new Color(255, 255, 255));
        nextButton.setBounds(216, 227, 108, 23);
        cardList = new ArrayList<FlashCard>();
        
        // This "TEXT AREA" is under construction
        //JTextArea textArea = new JTextArea();
		//textArea.setBounds(295, 33, 222, 176);
		//mainPanel.add(textArea);

		//JTextArea textArea1 = new JTextArea();
		//textArea1.setBounds(21, 33, 222, 176);
		//mainPanel.add(textArea1);


        // LABELS
        JLabel questionLabel = new JLabel("FRONT");
		questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		questionLabel.setBounds(21, 11, 58, 14);
		mainPanel.add(questionLabel);
        JLabel answerLabel = new JLabel("BACK");
		answerLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		answerLabel.setBounds(295, 11, 58, 14);
		mainPanel.add(answerLabel);

        // MAIN PANEL COMPONENTS
        mainPanel.add(questionLabel);
        mainPanel.add(questionScroll);
        mainPanel.add(answerLabel);
        mainPanel.add(answerScroll);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        
        // MENU BAR
        JMenuBar MenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem backMenuItem = new JMenuItem("Back");

        MenuBar.add(fileMenu);
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(backMenuItem);


        // ADD EVENTLISTENERS
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        backMenuItem.addActionListener(new BackMenuItemListener());
        

        // ADD TO THE FRAME
        frame.setJMenuBar(MenuBar);
        frame.setSize(563, 330);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
    }


    // PUBLIC STATIC VOID MAIN (STRING [] ARGS)
    public static void main(String[] args) throws Exception {

        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlashCardBuilder();
            }
        });
    }

    
    // LISTENERS AND METHODS

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // CREATE A FLASHCARD
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }

    

    class NewMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("New Menu Clicked");
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }

    class SaveMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);

            // FILE CHOOSER
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }

    }

    class BackMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showOptionDialog
                (null, "Go back to main menu?", "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                if(option == JOptionPane.YES_OPTION){
                    frame.dispose();
                    new FlashcardSelection().setVisible(true);
    }
        }
    }

    class WindowStateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showOptionDialog
                (null, FlashCardBuilder.this, "Are you sure you want to exit?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                if(option == JOptionPane.YES_OPTION){
                    frame.dispose();
                    new FlashcardSelection().setVisible(true);
    }
        }
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }
    
    private void saveFile(File selectedFile) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));

            Iterator<FlashCard> cardIterator = cardList.iterator();

            while (cardIterator.hasNext()) {
                FlashCard card = (FlashCard)cardIterator.next();
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();

        } catch (Exception e) {
            System.out.println("Couldn't write to file");
            e.printStackTrace();
        }
    }
    
}
