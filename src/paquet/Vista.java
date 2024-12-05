package paquet;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;

public class Vista extends JFrame {
	static List<Process> procesos = new ArrayList<>();
	JSpinner spPrimario;
	JSpinner spSecundario;
	JSpinner spCuaternario;
	JSpinner spTerciario;
	JTextPane txtResultado;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	 /**
	  * Mètode llançador de la clase LanzarMP
	 * @param tipo tipus de proteina a calcular
	 * @param posicion nùmero de proteina a calcular dins de totes
	 */
	public static void LanzarMP(int tipo, int posicion) {
	        String className = "paquet.SimulacioMP";
	        String javaHome = System.getProperty("java.home");
	        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
	        String classpath = System.getProperty("java.class.path");

	        ArrayList<String> command = new ArrayList<>();
	        command.add(javaBin);
	        command.add("-cp");
	        command.add(classpath);
	        command.add(className);
	        command.add(String.valueOf(tipo));
	        command.add(String.valueOf(posicion));

	        ProcessBuilder builder = new ProcessBuilder(command);

	        try {
	            Process p = builder.start();
				procesos.add(p);
			
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        
	    }}
	/**
	 * Mètode que crea y executa els fils de la classe SumulacioMT
	 */
	private void CalcularMT() {
	    LocalDateTime inicio = LocalDateTime.now(); 

	    List<Thread> threads = new ArrayList<>();

	    for (int i = 0; i < (int) spPrimario.getValue(); i++) {
	        SimulacioMT op = new SimulacioMT(1, i);
	        Thread fil = new Thread(op);
	        threads.add(fil);
	        fil.start();
	    }

	    for (int i = 0; i < (int) spSecundario.getValue(); i++) {
	        SimulacioMT op = new SimulacioMT(2, i);
	        Thread fil = new Thread(op);
	        threads.add(fil);
	        fil.start();
	    }

	    for (int i = 0; i < (int) spTerciario.getValue(); i++) {
	        SimulacioMT op = new SimulacioMT(3, i);
	        Thread fil = new Thread(op);
	        threads.add(fil);
	        fil.start();
	    }

	    for (int i = 0; i < (int) spCuaternario.getValue(); i++) {
	        SimulacioMT op = new SimulacioMT(4, i);
	        Thread fil = new Thread(op);
	        threads.add(fil);
	        fil.start();
	    }

	    for (Thread thread : threads) {
	        try {
	            thread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    LocalDateTime fin = LocalDateTime.now(); 

	    Duration duracion = Duration.between(inicio, fin);
	    long duracionEnMilisegundos = duracion.toMillis();
	    long segundos = duracionEnMilisegundos / 1000;
	    long centesimas = (duracionEnMilisegundos % 1000) / 10; 
	    String diferencia = segundos + "_" + centesimas;

	    txtResultado.setText(txtResultado.getText()+"\n Tiempo MultiHilo: " + diferencia);
	}
	/**
	 * Métode que executa el mètode de LanzarMP
	 */
	private void CalcularMP() {
		procesos.clear();
	    LocalDateTime inicio = LocalDateTime.now(); 

	    for (int i = 0; i < (int) spPrimario.getValue(); i++) {
	        LanzarMP(1, i);
	    }

	    for (int i = 0; i < (int) spSecundario.getValue(); i++) {
	    	 LanzarMP(2, i);
	    }

	    for (int i = 0; i < (int) spTerciario.getValue(); i++) {
	    	 LanzarMP(3, i);
	    }

	    for (int i = 0; i < (int) spCuaternario.getValue(); i++) {
	    	 LanzarMP(4, i);
	    }
	    
	    for (Process process : procesos) {
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	    LocalDateTime fin = LocalDateTime.now(); 

	    Duration duracion = Duration.between(inicio, fin);
	    long duracionEnMilisegundos = duracion.toMillis();
	    long segundos = duracionEnMilisegundos / 1000;
	    long centesimas = (duracionEnMilisegundos % 1000) / 10; 
	    String diferencia = segundos + "_" + centesimas;
	    txtResultado.setText(txtResultado.getText()+"\n Tiempo Multiproceso: " + diferencia);

	}


	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 527);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 255, 255));
		contentPane.setForeground(new Color(0, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Calculador Proteinas Por Adrián Pons");
		lblTitulo.setBounds(146, 10, 499, 37);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(lblTitulo);
		
		JLabel lblPrimaria = new JLabel("Primaria:");
		lblPrimaria.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPrimaria.setBounds(106, 110, 103, 37);
		contentPane.add(lblPrimaria);
		
		JLabel lblTerciaria = new JLabel("Terciaria:");
		lblTerciaria.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTerciaria.setBounds(106, 209, 103, 37);
		contentPane.add(lblTerciaria);
		
		JLabel lblSecundaria = new JLabel("Secundaria:");
		lblSecundaria.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblSecundaria.setBounds(453, 110, 148, 37);
		contentPane.add(lblSecundaria);
		
		JLabel lblCuaternaria = new JLabel("Cuaternaria:");
		lblCuaternaria.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCuaternaria.setBounds(453, 209, 148, 37);
		contentPane.add(lblCuaternaria);
		
		spPrimario = new JSpinner();
		spPrimario.setFont(new Font("Tahoma", Font.PLAIN, 25));
		spPrimario.setBounds(219, 116, 103, 24);
		contentPane.add(spPrimario);
		
		spSecundario = new JSpinner();
		spSecundario.setFont(new Font("Tahoma", Font.PLAIN, 25));
		spSecundario.setBounds(609, 116, 103, 24);
		contentPane.add(spSecundario);
		
		spCuaternario = new JSpinner();
		spCuaternario.setFont(new Font("Tahoma", Font.PLAIN, 25));
		spCuaternario.setBounds(609, 215, 103, 24);
		contentPane.add(spCuaternario);
		
		spTerciario = new JSpinner();
		spTerciario.setFont(new Font("Tahoma", Font.PLAIN, 25));
		spTerciario.setBounds(219, 215, 103, 24);
		contentPane.add(spTerciario);
		
		txtResultado = new JTextPane();
		txtResultado.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtResultado.setBackground(new Color(255, 255, 128));
		txtResultado.setBounds(146, 299, 499, 140);
		contentPane.add(txtResultado);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCalcular.setBounds(655, 299, 148, 21);
		contentPane.add(btnCalcular);
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtResultado.setText("");				 
				CalcularMT();
				CalcularMP();
			}
		});
	}
}
