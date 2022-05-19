import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.Component;

public class GUI implements ActionListener, ItemListener
{
    private JPanel weatherPanel;
    private JTextField zipCodeEntry;
    private Networking client;
    private JTextArea weatherInfo;
    private JCheckBox tempType;

    public GUI()
    {
        zipCodeEntry = new JTextField();
        client = new Networking();
        weatherPanel = new JPanel();
        weatherInfo = new JTextArea();
        tempType = new JCheckBox();

        setupGui();
    }

    private void setupGui()
    {
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("src/weather.jpg");
        Image imageData = image.getImage();
        Image scaledImage = imageData.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);  // transform it back
        JLabel pictureLabel = new JLabel(image);
        JLabel welcomeLabel = new JLabel("Current Weather");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.blue);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(pictureLabel);
        logoWelcomePanel.add(welcomeLabel);

        image = new ImageIcon("src/placeholder.jpg");
        imageData = image.getImage(); // transform it
        scaledImage = imageData.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        image = new ImageIcon(scaledImage);  // transform it back
        pictureLabel = new JLabel(image);
        weatherPanel.add(pictureLabel);

        tempType.addItemListener(this);

        JPanel entryPanel = new JPanel();
        JLabel weatherLabel = new JLabel("Enter Zip Code: ");
        zipCodeEntry = new JTextField(10);
        JButton sendButton = new JButton("Submit");
        JButton resetButton = new JButton("Clear");
        entryPanel.add(weatherLabel);
        entryPanel.add(zipCodeEntry);
        entryPanel.add(sendButton);
        entryPanel.add(resetButton);

        tempType = new JCheckBox("Show Celcius");
        tempType.setBounds(100, 100, 50, 50);
        entryPanel.add(tempType);

        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(entryPanel, BorderLayout.WEST);
        frame.add(weatherPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(this);
        resetButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void loadDisplay()
    {
        Component[] componentList = weatherPanel.getComponents();

        for(Component c : componentList)
        {
            if(c instanceof JLabel || c instanceof JTextArea){
                weatherPanel.remove(c);
            }
        }
        weatherPanel.revalidate();
        weatherPanel.repaint();
    }

    public void loadInformation()
    {
        String zip = zipCodeEntry.getText();
        DataModel report = client.getWeatherDetails(zip);

        double temp = report.getTempF();

        if (tempType.isSelected())
        {
            temp = report.getTempC();
        }
        String info = "Temperature: " + temp + " Condition: " + report.getCondition();

        ImageIcon image = new ImageIcon("src/" + report.getIcon().substring(21));
        Image imageData = image.getImage();
        Image scaledImage = imageData.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        JLabel icon = new JLabel(image);

        weatherPanel.add(weatherInfo);
        weatherPanel.add(icon);
        weatherInfo.setText(info);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();

        if (text.equals("Submit"))
        {
            loadDisplay();
            loadInformation();
        }
        else if (text.equals("Clear"))
        {
            zipCodeEntry.setText("");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        int checkBoxOnOrOff = e.getStateChange();
        JCheckBox cb = (JCheckBox)e.getSource();
        loadDisplay();
        loadInformation();
    }
}
