import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class grafon extends JFrame {
    public static final int INF = 999999999;
    ArrayList<Point> point;
    ArrayList<Integer> grafPath= new ArrayList<Integer>();
    long elapsedTime;
    int size;
    int n;
    int[][] oldD;



    grafon(int size, String path) throws IOException {
        intil(path);
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        elapsedTime = end - start;
        this.size = size;


        final JTextField t = new JTextField(5);
        final JTextField t1 = new JTextField(5);
        JLabel l;

        l = new JLabel("хуй с говном");
        JButton b;

        b = new JButton("проложить");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long start = System.currentTimeMillis();
                long end = System.currentTimeMillis();
                johnson k= new johnson(n);
                k.johnson(Integer.parseInt(t.getText()),oldD);
                System.out.println(k.getDistances()[Integer.parseInt(t1.getText())]);
                System.out.println("затраченное время " + (start - end));
            }
        });

        JPanel p = new JPanel();


        p.add(t,BorderLayout.NORTH);
        p.add(t1,BorderLayout.WEST);

        p.add(b,BorderLayout.SOUTH);
        p.add(l,BorderLayout.EAST);
        this.add(p,BorderLayout.NORTH);
        p.setVisible(true);

        this.setVisible(true);

        setTitle("johnson");
        setSize(size,size);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    public class texted implements ActionListener{
        public JTextField t,t1,t2,t3;
        public texted(JTextField t){
            this.t = t;
            this.t1 = t1;

        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(null, t.getText());
            JOptionPane.showMessageDialog(null, t1.getText());
            JOptionPane.showMessageDialog(null, t2.getText());
            JOptionPane.showMessageDialog(null, t3.getText());
        }
    }


    @Override
    public void paint(Graphics g) {
        int R = size / 2 - size / 5;
        int X = size / 2, Y = size / 2;
        point = new ArrayList<Point>();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));

        double angle = 360.0 / n;
        for (int i = 0; i < n; i++) {
            int x = (int) (X + R * Math.cos(Math.toRadians(angle * i)));
            int y = (int) (Y + R * Math.sin(Math.toRadians(angle * i)));
            point.add(new Point(x, y));
        }

        for (int i = 0; i < n; i++){
            for(int j = i; j < n; j++){
                if(i != j && oldD[i][j] != INF) {
                    int x1 = point.get(i).x;
                    int y1 = point.get(i).y;
                    int x2 = point.get(j).x;
                    int y2 = point.get(j).y;
                    g.setColor(Color.PINK);
                    g.drawLine(x1, y1, x2, y2);
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(oldD[i][j]), (x1 + x2) / 2, (y1 + y2) / 2);
                }
            }
        }


        for (int i = 0; i < grafPath.size(); i++) {
            g.setColor(Color.GREEN);
            int x1 = point.get(grafPath.get(i)).x;
            int y1 = point.get(grafPath.get(i)).y;
            int x2 = point.get(grafPath.get(i+1)).x;
            int y2 = point.get(grafPath.get(i+1)).y;
            g.drawLine(x1, y1, x2, y2);
        }

        int l = 0;
        for (Point p : point) {
            g.setColor(Color.BLUE);
            g.fillOval(p.x - 25, p.y - 25, 50, 50);
            g.setColor(Color.BLACK);
            g.drawOval(p.x - 25, p.y - 25, 50, 50);
            g.setColor(Color.ORANGE);
            g.drawString(String.valueOf(l + 1), p.x - 5, p.y + 5);
            l++;
        }
    }
    private void intil(String string) throws IOException {
        Path path = Paths.get(string);
        List<String> contents = Files.readAllLines(path);
        n = contents.size();

        oldD = new int[n][n];

        for(int i = 0; i < n; i++){
            String[] row = contents.get(i).split(" ");
            for(int j = 0; j < n; j++){
                if (i == j){
                    oldD[i][j] = 0;
                }
                else{
                    oldD[i][j] = Integer.parseInt(row[j]);
                }
            }
        }
    }




    public long getElapsedTime() {
        return elapsedTime;
    }


}