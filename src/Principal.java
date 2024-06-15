import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.RoundingMode;

public class Principal {

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 20), new BigDecimal("2000.00"), "Operador"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1985, 7, 15), new BigDecimal("3000.00"), "Administrativo"));
        funcionarios.add(new Funcionario("Ana", LocalDate.of(1995, 10, 10), new BigDecimal("4000.00"), "Gerente"));
        funcionarios.add(new Funcionario("José", LocalDate.of(1992, 12, 5), new BigDecimal("1500.00"), "Operador"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.of(1988, 1, 30), new BigDecimal("2500.00"), "Administrativo"));

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        funcionarios.forEach(funcionario -> {
            String dataFormatada = funcionario.getDataNascimento().format(formatter);
            String salarioFormatado = String.format("%,.2f", funcionario.getSalario());
            System.out.println(funcionario.getNome() + ", " + dataFormatada + ", " + salarioFormatado + ", " + funcionario.getFuncao());
        });

        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(BigDecimal.valueOf(1.10));
            funcionario.setSalario(novoSalario);
        });

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        funcionariosPorFuncao.forEach((funcao, listaFuncionarios) -> {
            System.out.println("Função: " + funcao);
            listaFuncionarios.forEach(System.out::println);
        });

        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(System.out::println);

        Funcionario funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (funcionarioMaisVelho != null) {
            int idade = LocalDate.now().getYear() - funcionarioMaisVelho.getDataNascimento().getYear();
            System.out.println("Funcionário mais velho: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade);
        }

        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total dos salários: " + String.format("%,.2f", totalSalarios));

        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
        	BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }
}
