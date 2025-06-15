/* Project Title: Enigma
 * Description: This emulates the Enigma machine, using all of its components.
 *
 * Created by: Isaac Newell
 * Date: 04/22/2017
 *
 *  GUI added by Greg Kraft
 * 8/1/2021 gkraft63@yahoo.com
 * */

import jaco.mp3.player.MP3Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;

public class Enigma extends JPanel {

    private Rotor[] rotors;
    private Reflector reflector;
    private Plugboard plugboard;

    // Characters showing through window at top of rotor
    private char[] tops;
    private int[] ringSettings;

    // Size of blocks of output text: i.e. BXQJ LMCQ OIEE
    private int blockSize = 4;
    JComboBox rotor1Combo, rotor2Combo, rotor3Combo, reflectorCombo;
    JTextPane textPane;
    JTextField ringStart;
    JTextField plugBoardPairs;
    JTextField textRingSettings;
    JScrollPane pane;
    JButton addDecriptEncript, enigmaExit;

    // All the components are set after initialization
    public Enigma(int numRotors) {
        rotors = new Rotor[numRotors];
        tops = new char[numRotors];
        ringSettings = new int[numRotors];

        plugboard = new Plugboard();
    }

    public Enigma(JFrame frame) {
        this(3);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 6, 5, 5));
        LineBorder border = new LineBorder(Color.RED, 4);
        panel.setBorder(border);
        textPane = new JTextPane();
        textRingSettings = new JTextField("01 02 21");
        ringStart = new JTextField("A W V");
        plugBoardPairs = new JTextField("AQ BZ LM");

        textPane.setFont(new Font("TimesRoman", Font.BOLD, 20));
        pane = new JScrollPane(textPane);
        JLabel rotorLbl = new JLabel("Select 3 Rotors");
        JLabel reflectorLbl = new JLabel("<html>Select reflector A B or C<br/");
        JLabel ringsLbl = new JLabel("<html>Ring settings (i.e. 01 02 21)<br/");//<html>some text <br/>

        JLabel ringStartLbl = new JLabel("<html>Ring start pos (i.e. A W V)<br/");
        JLabel plugBoardLbl = new JLabel("<html>Plugboard pairs (i.e. AQ BZ LM ...)<br/");
        enigmaExit = new JButton("Exit");

//    JLabel rotorLbl = new JLabel("Select 3 Rotors");
        pane.setPreferredSize(new Dimension(300, 100));
        addDecriptEncript = new JButton("Run");


        // ComboBox stuff
        String[] rotorStrings = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII"};
        String[] reflectorStrings = {"A", "B", "C"};
        rotor1Combo = new JComboBox<>(rotorStrings);
        rotor2Combo = new JComboBox<>(rotorStrings);
        rotor3Combo = new JComboBox<>(rotorStrings);
        reflectorCombo = new JComboBox<>(reflectorStrings);
        rotor1Combo.setSelectedIndex(0);
        rotor2Combo.setSelectedIndex(1);
        rotor3Combo.setSelectedIndex(2);
        reflectorCombo.setSelectedItem(0);
        addDecriptEncript.addActionListener(e -> {
            //do decript stuff here
        });
        panel.add(reflectorLbl);
        panel.add(reflectorCombo);
        panel.add(rotorLbl);
        panel.add(rotor1Combo);
        panel.add(rotor2Combo);
        panel.add(rotor3Combo);
        panel.add(enigmaExit);
        panel.add(ringsLbl);
        panel.add(textRingSettings);
        panel.add(ringStartLbl);
        panel.add(ringStart);
        panel.add(plugBoardLbl);
        panel.add(plugBoardPairs);

        panel.add(addDecriptEncript);

        add(pane, BorderLayout.PAGE_START);
        add(panel, BorderLayout.CENTER);


        addDecriptEncript.addActionListener(e ->

                {
                    String rotorsString = (String) rotor1Combo.getSelectedItem();
                    rotorsString = rotorsString + " " + rotor2Combo.getSelectedItem();
                    rotorsString = rotorsString + " " + rotor3Combo.getSelectedItem();

                    String nextRotor = "";
                    ArrayList<Rotor> rotorsToAdd = new ArrayList<Rotor>();
                    for (int i = 0; i < rotorsString.length(); i++) {
                        if (rotorsString.charAt(i) == ' ') {
                            if (nextRotor.equals("I"))
                                rotorsToAdd.add(Rotor.I);
                            else if (nextRotor.equals("II"))
                                rotorsToAdd.add(Rotor.II);
                            else if (nextRotor.equals("III"))
                                rotorsToAdd.add(Rotor.III);
                            else if (nextRotor.equals("IV"))
                                rotorsToAdd.add(Rotor.IV);
                            else if (nextRotor.equals("V"))
                                rotorsToAdd.add(Rotor.V);
                            else if (nextRotor.equals("VI"))
                                rotorsToAdd.add(Rotor.VI);
                            else if (nextRotor.equals("VII"))
                                rotorsToAdd.add(Rotor.VII);
                            else if (nextRotor.equals("VIII"))
                                rotorsToAdd.add(Rotor.VIII);

                            nextRotor = "";
                        } else
                            nextRotor += String.valueOf(rotorsString.charAt(i));
                    }
                    if (!nextRotor.equals("")) {
                        if (nextRotor.equals("I"))
                            rotorsToAdd.add(Rotor.I);
                        else if (nextRotor.equals("II"))
                            rotorsToAdd.add(Rotor.II);
                        else if (nextRotor.equals("III"))
                            rotorsToAdd.add(Rotor.III);
                        else if (nextRotor.equals("IV"))
                            rotorsToAdd.add(Rotor.IV);
                        else if (nextRotor.equals("V"))
                            rotorsToAdd.add(Rotor.V);
                        else if (nextRotor.equals("VI"))
                            rotorsToAdd.add(Rotor.VI);
                        else if (nextRotor.equals("VII"))
                            rotorsToAdd.add(Rotor.VII);
                        else if (nextRotor.equals("VIII"))
                            rotorsToAdd.add(Rotor.VIII);
                    }

                    Enigma enigma = new Enigma(rotorsToAdd.size());

                    int rotorIndex = 0;
                    for (Rotor r : rotorsToAdd) {
                        enigma.setRotor(rotorIndex, r);
                        rotorIndex++;
                    }

                    String ref = (String) reflectorCombo.getSelectedItem();
                    if (ref.equals("A"))
                        enigma.setReflector(Reflector.A);
                    else if (ref.equals("B"))
                        enigma.setReflector(Reflector.B);
                    else if (ref.equals("C"))
                        enigma.setReflector(Reflector.C);
                    String ringSets = textRingSettings.getText();

                    int[] rsets = new int[enigma.rotors.length];
                    int ringIndex = 0;
                    String currRing = "";
                    for (int i = 0; i < ringSets.length(); i++) {
                        if (ringSets.charAt(i) == ' ') {
                            if (currRing.charAt(0) == '0') {
                                currRing = currRing.substring(1);
                            }
                            rsets[ringIndex] = Integer.parseInt(currRing);
                            ringIndex++;
                            currRing = "";
                        } else {
                            currRing += String.valueOf(ringSets.charAt(i));
                        }
                    }
                    if (!currRing.equals("")) {
                        if (currRing.charAt(0) == '0') {
                            currRing = currRing.substring(1);
                        }
                        rsets[ringIndex] = Integer.parseInt(currRing);
                    }
                    enigma.setRings(rsets);

                    String ringPosStr = ringStart.getText();

                    int topsIndex = 0;
                    char[] topsToAdd = new char[enigma.rotors.length];
                    for (int i = 0; i < ringPosStr.length(); i++) {
                        if (ringPosStr.charAt(i) == ' ') {
                            topsIndex++;
                        } else {
                            topsToAdd[topsIndex] = ringPosStr.charAt(i);
                        }
                    }
                    enigma.setTops(topsToAdd);

                    String plugString = plugBoardPairs.getText();
                    String currPair = "";
                    for (int i = 0; i < plugString.length(); i++) {
                        if (plugString.charAt(i) == ' ') {
                            currPair = "";
                        } else {
                            currPair += String.valueOf(plugString.charAt(i));
                        }
                        if (currPair.length() == 2) {
                            enigma.plugboard.connect(currPair.charAt(0), currPair.charAt(1));
                        }
                    }

                    String text = textPane.getText();
                    textPane.setText(enigma.encrypt(text));

                }
        );
        enigmaExit.addActionListener(e ->
                {
                    System.exit(1);
                }
        );

    }

    public void PlayMP3()
        {
            try{
            File f = new File("typewriter.mp3");

            MP3Player mp3Player = new MP3Player(f);
            mp3Player.play();

            while (!mp3Player.isStopped()) {
                Thread.sleep(5000);
            }
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }


    public void setRotor(int index, Rotor rotor) {
        rotors[index] = rotor;
    }

    public void setReflector(Reflector reflector) {
        this.reflector = reflector;
    }

    public void setRings(int[] ringSettings) {
        this.ringSettings = ringSettings;
    }

    public void setTops(char[] tops) {
        this.tops = tops;
    }

    // Carries out rotor turnover with double-stepping
    public void step() {
        int rotorIndex = rotors.length - 1;
        // Traverse right to left through rotors
        while (rotorIndex >= 0) {
            // Implement a step of the current rotor
            if (tops[rotorIndex] != 'Z')
                tops[rotorIndex]++;
            else
                tops[rotorIndex] = 'A';
            // If current rotor is passing a turnover notch, move left and keep going
            if (rotors[rotorIndex].isNotch(tops[rotorIndex]))
                rotorIndex--;
                // If not, check for double-stepping
            else {
                if (rotorIndex > 0) {
                    // If rotor to the left is at its turnover notch,
                    // then turn it over and keep going
                    if (rotors[rotorIndex - 1].isNotch(Rotor.offset(tops[rotorIndex - 1], 1)))
                        rotorIndex--;
                    else
                        break;
                } else
                    break;
            }
        }
    }

    // Returns the output after a forward pass through the rotors (right to left)
    public char forwardRotors(char input) {
        int rotorIndex = rotors.length - 1;
        char current = input;
        while (rotorIndex >= 0) {
            // Shift given current rotor position
            current = Rotor.offset(current, (int) (tops[rotorIndex] - 65));
            // Now calclate output given ringsetting
            current = rotors[rotorIndex].output(current, ringSettings[rotorIndex]);
            // Shift back (for moving to the next rotor)
            current = Rotor.offset(current, (int) (65 - tops[rotorIndex]));
            rotorIndex--;
        }
        return current;
    }

    // Returns the output after a backward pass through the rotors (left to right)
    public char backwardRotors(char input) {
        int rotorIndex = 0;
        char current = input;
        while (rotorIndex < rotors.length) {
            current = Rotor.offset(current, (int) (tops[rotorIndex] - 65));
            current = rotors[rotorIndex].revOutput(current, ringSettings[rotorIndex]);
            current = Rotor.offset(current, (int) (65 - tops[rotorIndex]));
            rotorIndex++;
        }
        return current;
    }

    // Encrypts a single character
    public char encrypt(char input) {
        // Steps first
        step();
        char current = input;
        // Passes through all these components
        current = plugboard.output(current);
        current = forwardRotors(current);
        current = reflector.output(current);
        current = backwardRotors(current);
        current = plugboard.output(current);
        return current;
    }

    // Encrypts a whole string
    public String encrypt(String message) {
        PlayMP3();
        String cyphertext = "";
        int count = 0;
        for (int i = 0; i < message.length(); i++) {
            char next = message.charAt(i);
            // Accounts for lower case
            if (next >= 97 && next <= 122)
                next -= 32;
            // Only takes in letter chars
            if (next >= 65 && next <= 90) {
                cyphertext += String.valueOf(encrypt(next));
                count++;
                // Inserts spaces every blockSize for readability
                if (count % blockSize == 0)
                    cyphertext += " ";
            }
        }
        return cyphertext;
    }

    // Parses command line input for setup parameters, then encrypts
    //    or decrypts your message
    public static void main(String[] args) {

        Toolkit t;
        Dimension d;
        JFrame frame = new JFrame("Enigma - Isaac Newell - gui by Greg Kraft gkraft63@yahoo.com");
        //frame.setBackground(Color.RED);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 220));
        t = Toolkit.getDefaultToolkit();
        d = t.getScreenSize();
        frame.setLocation(d.width / 4 - frame.getWidth() / 2, d.height / 3 - frame.getHeight() / 2);
        frame.setContentPane(new Enigma(frame));
        // frame.setSize(260, 300);
        frame.pack();
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentHidden(ComponentEvent e) {
                ((JFrame) (e.getComponent())).dispose();
            }
        });

    }
}
