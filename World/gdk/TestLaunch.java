import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestLaunch extends JFrame{
	public TestLaunch() {

		initUI();
	}

	public void initUI() {
		setSize(1000, 600);
		setTitle("World test!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
        	@Override
        	public void run() {
        		TestLaunch tl = new TestLaunch();
        		tl.setVisible(true);
        	}
        });
	}
}
