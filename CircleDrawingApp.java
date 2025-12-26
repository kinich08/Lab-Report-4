import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class CircleDrawingApp extends JFrame{
    private DrawingPanel drawingPanel;
    private JButton drawButton;
    private JButton clearButton;

    public CircleDrawingApp(){
        setTitle("Circle Drawing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        layoutComponents();
    }

    private void initComponents(){
        drawingPanel =new DrawingPanel();

        drawButton =new JButton("Draw Circle");
        clearButton =new JButton("Clear");
        
        drawButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                drawingPanel.addRandomCircle();
            }
        });
        
        clearButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                drawingPanel.clearCircles();
            }
        });
    }
    
    private void layoutComponents(){
        setLayout(new BorderLayout());

        add(drawingPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel =new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(drawButton);
        buttonPanel.add(clearButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class DrawingPanel extends JPanel{
        private ArrayList<Circle> circles;
        private Random random;
        private static final int CIRCLE_DIAMETER =50;

        public DrawingPanel(){
            circles =new ArrayList<>();
            random =new Random();
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }

        public void addRandomCircle(){
            int maxX =getWidth() - CIRCLE_DIAMETER;
            int maxY =getHeight() - CIRCLE_DIAMETER;
            
            if (maxX > 0 && maxY > 0) {
                int x =random.nextInt(maxX);
                int y =random.nextInt(maxY);

                Color color = new Color(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                );
                
                circles.add(new Circle(x, y, CIRCLE_DIAMETER, color));
                repaint();
            }
        }

        public void clearCircles(){
            circles.clear();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d =(Graphics2D) g;
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                 RenderingHints.VALUE_ANTIALIAS_ON);

            for (Circle circle : circles){
                g2d.setColor(circle.getColor());
                g2d.fillOval(circle.getX(), circle.getY(), 
                            circle.getDiameter(), circle.getDiameter());
            }
        }
    }

    private class Circle{
        private int x;
        private int y;
        private int diameter;
        private Color color;

        public Circle(int x, int y, int diameter, Color color) {
            this.x = x;
            this.y = y;
            this.diameter = diameter;
            this.color = color;
        }

        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
        public int getDiameter(){
            return diameter;
        }
        public Color getColor(){
            return color;
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CircleDrawingApp app = new CircleDrawingApp();
                app.setVisible(true);
            }
        });
    }
}
