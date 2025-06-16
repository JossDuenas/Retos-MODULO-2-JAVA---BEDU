import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        // Datos de ejemplo
        Sucursal centro = new Sucursal("Centro", List.of(
                new Encuesta("Ana", "El tiempo de espera fue largo", 2),
                new Encuesta("Luis", null, 4),
                new Encuesta("María", null, 3)
        ));

        Sucursal norte = new Sucursal("Norte", List.of(
                new Encuesta("Carlos", "La atención fue buena, pero la limpieza puede mejorar", 3),
                new Encuesta("Sofía", null, 2),
                new Encuesta("Elena", "Excelente todo", 5)
        ));

        List<Sucursal> sucursales = List.of(centro, norte);

        // Procesamiento funcional
        sucursales.stream()
                .flatMap(sucursal ->
                        sucursal.getEncuestas().stream()
                                .filter(e -> e.getCalificacion() <= 3)
                                .flatMap(encuesta ->
                                        encuesta.getComentario()
                                                .map(com -> Stream.of("Sucursal " + sucursal.getNombre() +
                                                        ": Seguimiento a paciente con comentario: \"" + com + "\""))
                                                .orElseGet(Stream::empty)
                                )
                )
                .forEach(System.out::println);
    }
}
