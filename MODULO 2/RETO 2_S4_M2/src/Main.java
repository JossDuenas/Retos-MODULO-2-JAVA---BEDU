public class Main {
    public static void main(String[] args) throws InterruptedException {
        AeropuertoControl control = new AeropuertoControl();
        control.evaluarCondicionesDeAterrizaje();

        // Espera para que los procesos asincrónicos terminen antes de que el programa finalice
        Thread.sleep(5000); // Ajusta si alguna simulación tarda más
    }
}
