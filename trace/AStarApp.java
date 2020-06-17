import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;


/**
 * A simple Swing application to demonstrate the A* pathfinding algorithm.  The
 * user is presented with a map, containing a start and end location.  The user
 * can draw or clear obstacles on the map, and then press a button to compute a
 * path from start to end using the A* pathfinding algorithm.  If a path is
 * found, it is displayed in green.
 **/
public class AStarApp {

    /** The number of grid cells in the X direction. **/
    private int width;

    /** The number of grid cells in the Y direction. **/
    private int height;

    /** The location where the path starts from. **/
    private Location startLoc;

    /** The location where the path is supposed to finish. **/
    private Location finishLoc;
    private boolean aStar = true;
    /**
     * This is a 2D array of UI components that provide display and manipulation
     * of the cells in the map.
     ***/
    private JMapCell[][] mapCells;


    /**
     * This inner class handles mouse events in the main grid of map cells, by
     * modifying the cells based on the mouse button state and the initial edit
     * that was performed.
     **/
    private class MapCellHandler implements MouseListener
    {
        /**
         * This value will be true if a mouse button has been pressed and we are
         * currently in the midst of a modification operation.
         **/
        private boolean modifying;

        /**
         * This value records whether we are making cells passable or
         * impassable.  Which it is depends on the original state of the cell
         * that the operation was started within.
         **/
        private boolean makePassable;

        /** Initiates the modification operation. **/
        public void mousePressed(MouseEvent e)
        {
            modifying = true;

            JMapCell cell = (JMapCell) e.getSource();

            // If the current cell is passable then we are making them
            // impassable; if it's impassable then we are making them passable.

            makePassable = !cell.isPassable();

            cell.setPassable(makePassable);
        }

        /** Ends the modification operation. **/
        public void mouseReleased(MouseEvent e)
        {
            modifying = false;
        }

        /**
         * If the mouse has been pressed, this continues the modification
         * operation into the new cell.
         **/
        public void mouseEntered(MouseEvent e)
        {
            if (modifying)
            {
                JMapCell cell = (JMapCell) e.getSource();
                cell.setPassable(makePassable);
            }
        }

        /** Not needed for this handler. **/
        public void mouseExited(MouseEvent e)
        {
            // This one we ignore.
        }

        /** Not needed for this handler. **/
        public void mouseClicked(MouseEvent e)
        {
            // And this one too.
        }
    }


    /**
     * Creates a new instance of AStarApp with the specified map width and
     * height.
     **/
    public AStarApp(int w, int h) {
        if (w <= 0)
            throw new IllegalArgumentException("w must be > 0; got " + w);

        if (h <= 0)
            throw new IllegalArgumentException("h must be > 0; got " + h);

        width = w;
        height = h;

        startLoc = new Location(2, h / 2);
        finishLoc = new Location(w - 3, h / 2);
    }


    /**
     * Simple helper method to set up the Swing user interface.  This is called
     * from the Swing event-handler thread to be threadsafe.
     **/
    private void initGUI()
    {
        JFrame frame = new JFrame("Поиск пути");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        contentPane.setLayout(new BorderLayout());
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.weightx = 1;
        gbConstraints.weighty = 1;
        gbConstraints.insets.set(0, 0, 1, 1);
        JPanel mapPanel = new JPanel(gbLayout);
        mapPanel.setBackground(Color.GRAY);
        mapCells = new JMapCell[width][height];
        MapCellHandler cellHandler = new MapCellHandler();
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                mapCells[x][y] = new JMapCell();
                gbConstraints.gridx = x;
                gbConstraints.gridy = y;
                gbLayout.setConstraints(mapCells[x][y], gbConstraints);
                mapPanel.add(mapCells[x][y]);
                mapCells[x][y].addMouseListener(cellHandler);
            }
        }
        contentPane.add(mapPanel, BorderLayout.CENTER);
        JButton findPathButton = new JButton("Найти путь");
        JComboBox switchButton = new JComboBox();
        switchButton.addItem("A* алгоритм");
        switchButton.addItem("Двухлучевой алгоритм");
        switchButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switchButton.setActionCommand(switchButton.getSelectedItem().toString());
            }
        });
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand()=="A* алгоритм")
                    aStar = true;
                else
                    aStar = false;
            }
        });
        findPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { findAndShowPath(); }
        });
        contentPane.add(findPathButton, BorderLayout.SOUTH);
        contentPane.add(switchButton, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        mapCells[startLoc.xCoord][startLoc.yCoord].setEndpoint(true);
        mapCells[finishLoc.xCoord][finishLoc.yCoord].setEndpoint(true);
    }


    /** Kicks off the application.  Called from the {@link #main} method. **/
    private void start()
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { initGUI(); }
        });
    }


    /**
     * This helper method attempts to compute a path using the current map
     * state.  The implementation is rather slow; a new {@link Map2D} object is
     * created, and initialized from the current application state.  Then the A*
     * pathfinder is called, and if a path is found, the display is updated to
     * show the path that was found.  (A better solution would use the Model
     * View Controller design pattern.)
     **/
    private void findAndShowPath()
    {
        // Create a Map2D object containing the current state of the user input.

        Map2D map = new Map2D(width, height);
        map.setStart(startLoc);
        map.setFinish(finishLoc);
        if (aStar == true){
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                mapCells[x][y].setPath(false);

                if (mapCells[x][y].isPassable())
                    map.setCellValue(x, y, 0);
                else
                    map.setCellValue(x, y, Integer.MAX_VALUE);
            }
        }

        // Try to compute a path.  If one can be computed, mark all cells in the
        // path.

        Waypoint wp = AStarPathfinder.computePath(map);

        while (wp != null)
        {
            Location loc = wp.getLocation();
            mapCells[loc.xCoord][loc.yCoord].setPath(true);

            wp = wp.getPrevious();
        }return;}

        DualBeamAlgorithm pathfinder = new DualBeamAlgorithm(map.getStart(), map.getFinish());
        long twoBeamStart = System.nanoTime();
        java.util.List<Location> locations = pathfinder.schetPuti(map);
        long twoBeamEnd = System.nanoTime();
        long twoBeamDuration = twoBeamEnd - twoBeamStart;
        System.out.println("Двухлучевой алгоритм справился: " + (twoBeamDuration / 1000000L) + " мс");
        for (Location loc : locations)
        {
            mapCells[loc.xCoord][loc.yCoord].setBackground(Color.BLUE);
        }
        List<Location> intersected = pathfinder.vsePeresechenie();
        for (Location loc : intersected)
        {
            mapCells[loc.xCoord][loc.yCoord].setBackground(Color.GREEN);
        }
        mapCells[map.getStart().xCoord][map.getStart().yCoord].setBackground(Color.CYAN);
        mapCells[map.getFinish().xCoord][map.getFinish().yCoord].setBackground(Color.CYAN);
    }



    /**
     * Entry-point for the application.  No command-line arguments are
     * recognized at this time.
     **/
    public static void main(String[] args) {
        AStarApp app = new AStarApp(40, 30);
        app.start();
    }
}
