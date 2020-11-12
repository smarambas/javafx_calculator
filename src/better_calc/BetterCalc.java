package better_calc;

import java.math.BigDecimal;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class BetterCalc extends Application {
	
	private String appStyle = "-fx-background-color: #353535;";	//default color for the application
	private String opSymbol;	//the symbol of the operation
	private String numString = "";
	
	private TextField opField = new TextField();	//operations field
	private TextField resField = new TextField();   //result field
	
	private int buttonNum = 10;
	
	private MyNumber stackNumber = new MyNumber("0");
	private MyNumber number = new MyNumber("0");
	private MyNumber ans = new MyNumber("0");
	
	private enum Operations {SUM, SUB, MULT, DIV, NOSET}
	private Operations stackOp = Operations.NOSET;
	private Operations curOp = Operations.NOSET;
	
	private Button sumButton;
	private Button subButton;
	private Button multButton;
	private Button divButton; 
	private Button equalButton;
	private Button cancButton;
	private Button dotButton;
	private Button[] numbersButtons = createNumButtons(buttonNum); 
		
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Calculator");
		
		BorderPane bPane = new BorderPane();	//bpane as root of the scene
		bPane.setStyle("-fx-background-color: chocolate; -fx-padding: 20; -fx-font-size: 20;");
		bPane.setTop(addVBox());	//vertical box to display operation and result
		bPane.setCenter(addGrid());	//grid to display buttons
		
		Scene scene = new Scene(bPane, 342, 385);  
		stage.setScene(scene);         
		
		cancButton.requestFocus();
		equalButtonLogic(equalButton);
		
		stage.show();
	}
	
	private VBox addVBox() {
		/*
		 * Create a VBox to display the operation and result fields
		 * Inside the VBox there is a TextField for the operation 
		 * and an HBox, that contains a Text and another TextField 
		 * for the result
		 */
		
		VBox topBox = new VBox();
		topBox.setSpacing(15);	//gap between nodes
		topBox.setStyle(appStyle);
		topBox.setPadding(new Insets(10));
		
		HBox resBox = new HBox();
		resBox.setSpacing(10);
		resBox.setStyle(appStyle);
		resBox.setAlignment(Pos.CENTER_LEFT);
		
		opField.setPromptText("Operation");
		opField.setAlignment(Pos.CENTER_RIGHT);
		opField.setEditable(false);
		opField.setFocusTraversable(false);
		
		Text equalText = new Text("=");
		equalText.setFill(Color.WHITE);
				
		resField.setPromptText("Result");
		resField.setAlignment(Pos.CENTER_RIGHT);
		resField.setEditable(false);
		resField.setFocusTraversable(false);
		
		HBox.setHgrow(resField, Priority.ALWAYS);	//to resize resField when the window dimension changes
		
		resBox.getChildren().addAll(equalText, resField);
		topBox.getChildren().addAll(opField, resBox);
		
		return topBox;
	}
	
	private GridPane addGrid() {
		/*
		 * Create a grid to display all the buttons and create the buttons
		 */
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setStyle(appStyle);
	    grid.setPadding(new Insets(10, 10, 15, 10));
	    grid.setAlignment(Pos.CENTER);
		
		//first row
		grid.add(numbersButtons[7], 0, 0);
		grid.add(numbersButtons[8], 1, 0);
		grid.add(numbersButtons[9], 2, 0);
		
		sumButton = createButton("+");
		grid.add(sumButton, 3, 0);
		GridPane.setHgrow(sumButton, Priority.ALWAYS);
		GridPane.setVgrow(sumButton, Priority.ALWAYS);
	
		cancButton = createButton("CANC");
		grid.add(cancButton, 4, 0);
		GridPane.setHgrow(cancButton, Priority.ALWAYS);
		GridPane.setVgrow(cancButton, Priority.ALWAYS);
		
		//second row
		grid.add(numbersButtons[4], 0, 1);
		grid.add(numbersButtons[5], 1, 1);
		grid.add(numbersButtons[6], 2, 1);		
		
		subButton = createButton("-");
		grid.add(subButton, 3, 1);
		GridPane.setHgrow(subButton, Priority.ALWAYS);
		GridPane.setVgrow(subButton, Priority.ALWAYS);
		
		//third row
		grid.add(numbersButtons[1], 0, 2);
		grid.add(numbersButtons[2], 1, 2);
		grid.add(numbersButtons[3], 2, 2);
		
		multButton = createButton("x");
		grid.add(multButton, 3, 2);
		GridPane.setHgrow(multButton, Priority.ALWAYS);
		GridPane.setVgrow(multButton, Priority.ALWAYS);
		
		//fourth row
		grid.add(numbersButtons[0], 0, 3, 2, 1);
		
		dotButton = createButton(".");
		grid.add(dotButton, 2, 3);
		GridPane.setHgrow(dotButton, Priority.ALWAYS);
		GridPane.setVgrow(dotButton, Priority.ALWAYS);
		
		divButton = createButton("÷");
		grid.add(divButton, 3, 3);
		GridPane.setHgrow(divButton, Priority.ALWAYS);
		GridPane.setVgrow(divButton, Priority.ALWAYS);
		
		equalButton = createButton("=");
		grid.add(equalButton, 4, 1, 1, 3);
		GridPane.setHgrow(equalButton, Priority.ALWAYS);
		GridPane.setVgrow(equalButton, Priority.ALWAYS);
		
		for(int i = 0; i < buttonNum; i++) {
			GridPane.setHgrow(numbersButtons[i], Priority.ALWAYS);
			GridPane.setVgrow(numbersButtons[i], Priority.ALWAYS);
		}
		
		return grid;
	}
	
	private Button[] createNumButtons(int num) {
		/*
		 * Method to create the buttons for the numbers
		 */
		
		Button[] buttonsArray = new Button[num];
		
		for(int i = 0; i < num; i++) {
			buttonsArray[i] = new Button(String.valueOf(i));
			buttonsArray[i].setTextAlignment(TextAlignment.CENTER);
			buttonsArray[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			numButtonLogic(i, buttonsArray[i]);
		}
		
		return buttonsArray;
	}
	
	private Button createButton(String s) {
		/*
		 * Method to create an operation button
		 */
		
		Button button = new Button(s);
		button.setTextAlignment(TextAlignment.CENTER);
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		opButtonLogic(s, button);
		
		return button;
	}
	
	private void numButtonLogic(int n, Button button) {
		/*
		 * Method to define the logic behind the buttons for the numbers
		 */
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(curOp == Operations.NOSET) {	//if the user is defining the first number
					numString = numString + String.valueOf(n);
					opField.setText(numString);
					stackOp = Operations.NOSET;
				}
				else {	//if the user is defining the second number
					numString = numString + String.valueOf(n);
					opField.setText(stackNumber.getString() + opSymbol + numString);
					numbersButtons[0].setDisable(false);	//enable 0 button state (disabled by the division operation)
				}
				event.consume();
			}
		});
	}
	
	private void opButtonLogic(String s, Button button) {
		/*
		 * Method to define the logic behind the buttons for the operations
		 */
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switch (s) {
					case "+": 
						opButtonSumLogic();
						break;
					case "-": 
						opButtonSubLogic();
						break;
					case "x": 
						opButtonMultLogic();
						break;
					case "÷": 
						opButtonDivLogic();
						break;
					case ".": 
						opButtonDotLogic();
						break;
					case "CANC": 
						opButtonCancLogic();
						break;
					default:
						opField.setText("");
						resField.setText("");
				}
				event.consume();
			}
		});
	}
	
	private void opButtonLogicCommons() {
		if(numString.equals("")) {
			stackNumber.setBD(BigDecimal.ZERO);
		}
		else {
			stackNumber = new MyNumber(numString);
		}		
		numString = "";
		opField.setText(stackNumber.getString() + opSymbol);
	}
	
	private void opButtonLogicCommonsStack() {
		stackNumber = new MyNumber(ans.getString());
		opField.setText(stackNumber.getString() + opSymbol);
		stackOp = Operations.NOSET;
	}
	
	private void opButtonLogicDisable() {
		sumButton.setDisable(true);
		subButton.setDisable(true);
		multButton.setDisable(true);
		divButton.setDisable(true);
		dotButton.setDisable(false);
	}
	
	private void opButtonSumLogic() {
		/*
		 * Method to define the logic behind the press of the sum
		 * operation button
		 */
		
		opSymbol = "+";
		
		if(stackOp == Operations.NOSET) {	//if there is no previous operation
			opButtonLogicCommons();
			curOp = Operations.SUM;
		}
		else {	//if the previous operation was a sum
			opButtonLogicCommonsStack();
			curOp = Operations.SUM;
		}
		
		opButtonLogicDisable();
	}
	
	private void opButtonSubLogic() {
		/*
		 * Method to define the logic behind the press of the subtraction
		 * operation button
		 */
		
		opSymbol = "-";
		
		if(stackOp == Operations.NOSET) {	//if there is no previous operation
			opButtonLogicCommons();
			curOp = Operations.SUB;
		}
		else {	//if the previous operation was a subtraction
			opButtonLogicCommonsStack();
			curOp = Operations.SUB;
		}
		
		opButtonLogicDisable();
	}
	
	private void opButtonMultLogic() {
		/*
		 * Method to define the logic behind the press of the multiplication
		 * operation button
		 */
		
		opSymbol = "x";
		
		if(stackOp == Operations.NOSET) {	//if there is no previous operation
			opButtonLogicCommons();
			curOp = Operations.MULT;
		}
		else {	//if the previous operation was a multiplication
			opButtonLogicCommonsStack();
			curOp = Operations.MULT;
		}
		
		opButtonLogicDisable();
	}
	
	private void opButtonDivLogic() {
		/*
		 * Method to define the logic behind the press of the division
		 * operation button
		 */
		
		opSymbol = "÷";

		if(stackOp == Operations.NOSET) {	//if there is no previous operation
			opButtonLogicCommons();
			curOp = Operations.DIV;
		}
		else {	//if the previous operation was a division
			opButtonLogicCommonsStack();
			curOp = Operations.DIV;
		}

		opButtonLogicDisable();
	}
	
	private void opButtonDotLogic() {
		/*
		 * Method to define the logic behind the press of the dot button
		 */
		
		numString = numString + ".";
		dotButton.setDisable(true);
	}
	
	private void opButtonCancLogic() {
		/*
		 * Method to define the logic behind the press of the CANC button
		 */
		
		opField.setText("");
		resField.setText("");
		opSymbol = "";
		numString = "";
		
		stackNumber.setBD(BigDecimal.ZERO);
		number.setBD(BigDecimal.ZERO);
		ans.setBD(BigDecimal.ZERO);
		
		curOp = Operations.NOSET;
		stackOp = Operations.NOSET;
				
		sumButton.setDisable(false);
		subButton.setDisable(false);
		multButton.setDisable(false);
		divButton.setDisable(false);
		dotButton.setDisable(false);
		numbersButtons[0].setDisable(false);
	}
	
	private void equalButtonLogic(Button button) {
		/*
		 * Method to define the logic of the equal button
		 */
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switch (curOp) {
					case SUM: 
						equalButtonSumLogic();
						break;
					case SUB: 
						equalButtonSubLogic();
						break;
					case MULT: 
						equalButtonMultLogic();
						break;
					case DIV: 
						equalButtonDivLogic();
						break;
					default:
						resField.setText("Invalid");
				}
				event.consume();
			}
		});
	}
	
	private void equalButtonSumLogic() {
		number = new MyNumber(numString);
		ans = new MyNumber(stackNumber.sum(number));	
		stackOp = Operations.SUM;
		equalCommon();
	}
	
	private void equalButtonSubLogic() {
		number = new MyNumber(numString);
		ans = new MyNumber(stackNumber.sub(number));	
		stackOp = Operations.SUB;
		equalCommon();
	}
	
	private void equalButtonMultLogic() {
		number = new MyNumber(numString);
		ans = new MyNumber(stackNumber.mult(number));	
		stackOp = Operations.MULT;
		equalCommon();
	}
	
	private void equalButtonDivLogic() {
		number = new MyNumber(numString);
		if(!stackNumber.getBD().equals(BigDecimal.ZERO) && number.getBD().equals(BigDecimal.ZERO)) {
			resField.setText("Infinity");
			equalButtonDivCommons();
		}
		else if(stackNumber.getBD().equals(BigDecimal.ZERO) && number.getBD().equals(BigDecimal.ZERO)) {
			resField.setText("nan");
			equalButtonDivCommons();
		}
		else {
			ans = new MyNumber(stackNumber.div(number));	
			stackOp = Operations.DIV;
			equalCommon();
		}
	}
	
	private void equalButtonDivCommons() {
		curOp = Operations.NOSET;
		
		stackNumber.setBD(BigDecimal.ZERO);
		number.setBD(BigDecimal.ZERO);
		ans.setBD(BigDecimal.ZERO);
		
		numString = "";
		
		sumButton.setDisable(false);
		subButton.setDisable(false);
		multButton.setDisable(false);
		divButton.setDisable(false);
		dotButton.setDisable(false);
	}
	
	private void equalCommon() {
		/*
		 * Common operations for the equal button logic
		 */
		
		resField.setText(ans.getString());
		curOp = Operations.NOSET;
		
		
		stackNumber.setBD(BigDecimal.ZERO);
		number.setBD(BigDecimal.ZERO);
		
		numString = "";
		
		sumButton.setDisable(false);
		subButton.setDisable(false);
		multButton.setDisable(false);
		divButton.setDisable(false);
		dotButton.setDisable(false);
	}
}