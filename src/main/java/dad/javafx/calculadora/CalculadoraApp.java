package dad.javafx.calculadora;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraApp extends Application {

	// view
	private TextField operandoReal1Text;
	private TextField operandoReal2Text;
	private TextField imaginario1Text;
	private TextField imaginario2Text;
	private ComboBox<String> operadorCombo;
	private TextField resultadoRealText;
	private TextField resultadoImaginarioText;

	// model
	private Complejo operando1 = new Complejo();
	private Complejo operando2 = new Complejo();
	private Complejo resultado = new Complejo();
	private StringProperty operacion = new SimpleStringProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {

		operandoReal1Text = new TextField("0");
		operandoReal1Text.setPrefColumnCount(4);
		operandoReal1Text.setAlignment(Pos.CENTER);

		imaginario1Text = new TextField("0");
		imaginario1Text.setPrefColumnCount(4);
		imaginario1Text.setAlignment(Pos.CENTER);

		operandoReal2Text = new TextField("0");
		operandoReal2Text.setPrefColumnCount(4);
		operandoReal2Text.setAlignment(Pos.CENTER);

		imaginario2Text = new TextField("0");
		imaginario2Text.setPrefColumnCount(4);
		imaginario2Text.setAlignment(Pos.CENTER);

		resultadoRealText = new TextField("0");
		resultadoRealText.setPrefColumnCount(4);
		resultadoRealText.setDisable(true);

		resultadoImaginarioText = new TextField("0");
		resultadoImaginarioText.setPrefColumnCount(4);
		resultadoImaginarioText.setDisable(true);

		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");

		HBox hboxNum1 = new HBox(5, operandoReal1Text, new Label("+"), imaginario1Text, new Label("i"));
		hboxNum1.setAlignment(Pos.CENTER);

		HBox hboxNum2 = new HBox(5, operandoReal2Text, new Label("+"), imaginario2Text, new Label("i"));
		hboxNum2.setAlignment(Pos.CENTER);

		HBox hboxResultado = new HBox(5, resultadoRealText, new Label("+"), resultadoImaginarioText, new Label("i"));
		hboxNum2.setAlignment(Pos.CENTER);

		VBox vCombo = new VBox(5, operadorCombo);
		vCombo.setAlignment(Pos.CENTER);

		VBox vOperaciones = new VBox(5, hboxNum1, hboxNum2, new Separator(), hboxResultado);
		vOperaciones.setAlignment(Pos.CENTER);

		HBox root = new HBox(5, vCombo, vOperaciones);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Calculadora");
		primaryStage.setScene(scene);
		primaryStage.show();

		// bindeos
		operandoReal1Text.textProperty().bindBidirectional(operando1.realProperty(), new NumberStringConverter());
		operandoReal2Text.textProperty().bindBidirectional(operando2.realProperty(), new NumberStringConverter());
		imaginario1Text.textProperty().bindBidirectional(operando1.imaginarioProperty(), new NumberStringConverter());
		imaginario2Text.textProperty().bindBidirectional(operando2.imaginarioProperty(), new NumberStringConverter());
		resultadoRealText.textProperty().bindBidirectional(resultado.realProperty(), new NumberStringConverter());
		resultadoImaginarioText.textProperty().bindBidirectional(resultado.imaginarioProperty(),
				new NumberStringConverter());
		operacion.bind(operadorCombo.getSelectionModel().selectedItemProperty());

		operacion.addListener((o, ov, nv) -> onOperacionChanged(nv));

		operadorCombo.getSelectionModel().selectFirst();

	}

	private void onOperacionChanged(String nv) {
		switch (nv) {
		case "+":
			resultado.realProperty().bind(operando1.realProperty().add(operando2.realProperty()));
			resultado.imaginarioProperty().bind(operando1.imaginarioProperty().add(operando2.imaginarioProperty()));
			;
			break;
		case "-":
			resultado.realProperty().bind(operando1.realProperty().subtract(operando2.realProperty()));
			resultado.imaginarioProperty()
					.bind(operando1.imaginarioProperty().subtract(operando2.imaginarioProperty()));
			;
			break;
		case "*":
			resultado.realProperty().bind(operando1.realProperty().multiply(operando2.realProperty())
					.subtract(operando1.imaginarioProperty().multiply(operando2.imaginarioProperty())));
			resultado.imaginarioProperty().bind(operando1.realProperty().multiply(operando2.imaginarioProperty())
					.add(operando1.imaginarioProperty().multiply(operando2.realProperty())));
			break;
		case "/":
			resultado.realProperty()
					.bind(operando1.realProperty().multiply(operando2.realProperty())
							.add(operando1.imaginarioProperty().multiply(operando2.imaginarioProperty()))
							.divide(operando2.realProperty().multiply(operando2.realProperty())
									.add(operando2.imaginarioProperty().multiply(operando2.imaginarioProperty()))));
			resultado.imaginarioProperty()
			.bind(operando1.imaginarioProperty().multiply(operando2.realProperty())
					.subtract(operando1.realProperty().multiply(operando2.imaginarioProperty()))
					.divide(operando2.realProperty().multiply(operando2.realProperty())
							.add(operando2.imaginarioProperty().multiply(operando2.imaginarioProperty()))));
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
