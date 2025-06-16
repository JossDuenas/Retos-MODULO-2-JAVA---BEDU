public class Main {
    public static void main(String[] args) throws InterruptedException {
        MovilidadApp app = new MovilidadApp();
        app.procesarSolicitud();

        // Espera para que los procesos asincrónicos terminen antes de que el main acabe
        Thread.sleep(5000);
    }
}
