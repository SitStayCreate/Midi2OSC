package com.SitStayCreate.GUI;

import com.SitStayCreate.GUI.ActionListeners.DropActionListener;
import com.SitStayCreate.MidiGrid.MidiGridAdapter;
import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DevicesTable extends JPanel implements Scrollable {
    private int yConstraint = 0;
    private final GridBagLayout bagLayout;
    //Switch to Map
    private final Map<String, JComponent[]> tableComponents;

    //TODO: Replace this with a JPanel/GridBagLayout
    public DevicesTable(int FONTSIZE) {

        //Set up and style the table
        bagLayout = new GridBagLayout();
        setLayout(bagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 10, 0, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        Font tableFont = new Font(Font.MONOSPACED, Font.BOLD, FONTSIZE - 2);
        Font tableHeaderFont = new Font(Font.MONOSPACED, Font.PLAIN, FONTSIZE - 2);

        setFont(tableFont);
        setBackground(Color.GRAY);
        setForeground(Color.YELLOW);

        tableComponents = new HashMap<>();

        //Add header row
        JLabel[] headers = new JLabel[]{
            new JLabel(Constants.DROP_LABEL),
            new JLabel(Constants.ID_COLUMN_LABEL),
            new JLabel(Constants.SIZE_COLUMN_LABEL),
            new JLabel(Constants.PORT_IN_COLUMN_LABEL),
            new JLabel(Constants.INVERTED_COLUMN_LABEL),
            new JLabel(Constants.CH_LABEL)
        };

        for(JLabel header : headers){
            constraints.gridx++;
            constraints.gridy = yConstraint;
            bagLayout.setConstraints(header, constraints);
            add(header);
        }
        yConstraint++;
    }

    //Midi Grid
    public void addRow(MidiGridAdapter grid) {
        Dimensions dims = grid.getDimensions();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 10, 0, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = yConstraint;

        JComponent[] components = new JComponent[6];
        JButton deleteButton = new JButton(Constants.DELETE_LABEL);
        deleteButton.setPreferredSize(new Dimension(15, 15));
        deleteButton.addActionListener(new DropActionListener(this, grid));
        components[0] = deleteButton;
        components[1] = new JLabel(grid.getId());
        components[2] = new JLabel(dims.getWidth() + Constants.DEVICE_DIMS_LABEL + dims.getHeight());
        components[3] = new JLabel(String.valueOf(grid.getDecoratedOSCPortIn().getPortIn()));
        components[4] = new JLabel(String.valueOf(dims.isInverted()));
        components[5] = new JLabel(String.valueOf(grid.getHardwareDevice().getChannel() + 1));


        for(JComponent component : components){
            constraints.gridy = yConstraint;
            bagLayout.setConstraints(component, constraints);
            add(component);
            constraints.gridx++;
        }

        tableComponents.put(grid.getId(), components);
        yConstraint++;
    }

    public void dropRow(String key){
        //Remove components from Panel
        for (JComponent c : tableComponents.get(key)){
            remove(c);
        }
        //TODO: Change this to Map
        //Remove components from list
        tableComponents.remove(key);
        updateUI();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 0;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 0;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
