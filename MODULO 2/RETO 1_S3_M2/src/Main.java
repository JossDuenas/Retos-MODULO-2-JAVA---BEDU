import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        List<Pedido> pedidos = List.of(
                new Pedido("Carlos", "domicilio", "555-1234"),
                new Pedido("Lucía", "local", null),
                new Pedido("María", "domicilio", null),
                new Pedido("Andrés", "domicilio", "555-5678"),
                new Pedido("Laura", "local", "555-9999")
        );

        pedidos.stream()
                .filter(p -> p.getTipoEntrega().equalsIgnoreCase("domicilio"))
                .map(Pedido::getTelefono)
                .flatMap(Optional::stream) // Descarta los vacíos
                .map(tel -> "📞 Confirmación enviada al número: " + tel)
                .forEach(System.out::println);
    }
}
