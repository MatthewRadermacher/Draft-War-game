import java.util.Scanner;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class DraftWar extends JFrame {
	
	public DraftWar() {
		init();
	}
	
	private void init() {
		
		
		Scanner input = new Scanner(System.in);
		String introMessage = "************************\n"
							+ "* Welcome to Draft War *\n"
							+ "************************\n"
							+ "What is your name?";
		System.out.println(introMessage);
		
		Game game = new Game(input.nextLine());
		game.draft();
		input.close();
		
		
		add(new Board(game));
		//setSize(800, 1000);
		pack();
		setResizable(false);
		setTitle("Draft War");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			DraftWar draftWar = new DraftWar();
			draftWar.setVisible(true);
		});
	}
}