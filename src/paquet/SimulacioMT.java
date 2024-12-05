package paquet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulacioMT implements Runnable {
    int tipus;
    int ordre;

    public SimulacioMT(int tipus, int ordre) {
        this.tipus = tipus;
        this.ordre = ordre;
    }

    /**
     * Mètode que calcula la proteina especificada
     * @param type tipus de proteina a calcular
     * @return el resultat de la proteina indicada
     */
    public static double simulation(int type) {
        double calc = 0.0;
        double simulationTime = Math.pow(5, type);
        double startTime = System.currentTimeMillis();
        double endTime = startTime + simulationTime;

        while (System.currentTimeMillis() < endTime) {
            calc = Math.sin(Math.pow(Math.random(), 2));
        }
        return calc;
    }

    @Override
    public void run() {
        String texto = "";
        LocalDateTime inicio = LocalDateTime.now();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
        String horaInicio = inicio.format(formato);

        String nombre = "PROT_MT_" + tipus + "_n" + ordre + "_" + horaInicio + ".sim";
        File f = new File(nombre);

        double resultado = simulation(tipus);

        LocalDateTime fin = LocalDateTime.now();
        String horaFin = fin.format(formato);
        Duration duracion = Duration.between(inicio, fin);
        long duracionEnMilisegundos = duracion.toMillis();

        long segundos = duracionEnMilisegundos / 1000;
        long centesimas = (duracionEnMilisegundos % 1000) / 10; 
        String diferencia = segundos + "_" + centesimas;

        texto = horaInicio + "\n" + horaFin + "\n" + diferencia + "\n" + resultado;

        try (FileWriter fr = new FileWriter(f)) {
            fr.write(texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
