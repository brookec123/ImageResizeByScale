package packageGUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Resizer extends Application {
	static final int GAP = 10;
	static String TITLE_HEADER_FONT = "50";
	static String BUTTON_TEXT_FONT = "15";
	static String INSTR_FONT = "25";
	static float initX = -1;
	static float initY = -1;
	static float desX = -1;
	static float desY = -1;
	static float multiplier = -1;

	@Override
	public void start(Stage arg0) throws Exception
	{
		resize(arg0);

	}

	public static void resize(Stage resizeStage)
	{
	     final Clipboard clipboard = Clipboard.getSystemClipboard();
	     final ClipboardContent content = new ClipboardContent();
		//creating the vertical box for the welcome screen
		VBox vbxResize = new VBox(GAP);
		vbxResize.setPadding(new Insets(GAP, GAP, GAP, GAP));

		//creating, setting, and adding a header Label
		AnchorPane apTitleHeader = new AnchorPane();
		Label lblTitle = new Label("Lucky");
		lblTitle.setStyle("-fx-font-size: "+TITLE_HEADER_FONT);
		lblTitle.setMaxWidth(Double.MAX_VALUE);
		AnchorPane.setLeftAnchor(lblTitle, 0.0);
		AnchorPane.setRightAnchor(lblTitle, 0.0);
		lblTitle.setAlignment(Pos.CENTER);
		apTitleHeader.getChildren().add(lblTitle);


		Label lblInitialX = new Label("* Initial X Size: ");
		lblInitialX.setStyle("-fx-font-size: "+INSTR_FONT);
		TextField txtInitialX = new TextField();
		HBox hbxInitialX = new HBox(23, lblInitialX, txtInitialX);

		Label lblInitialY = new Label("* Initial Y Size: ");
		lblInitialY.setStyle("-fx-font-size: "+INSTR_FONT);
		TextField txtInitialY = new TextField();
		HBox hbxInitialY = new HBox(23, lblInitialY, txtInitialY);
		
		Button btnCopyInitialValues = new Button("Copy Initial Values");
		btnCopyInitialValues.setStyle("-fx-font-size: "+BUTTON_TEXT_FONT);
		btnCopyInitialValues.setOnAction(event -> {
			if(!txtInitialX.getText().toString().equals("") && !txtInitialY.getText().toString().equals(""))
			{
					content.putString("(X="+txtInitialX.getText().toString()+",Y="+txtInitialY.getText().toString()+")");
					clipboard.setContent(content);
			}
		});
		
		Button btnPasteInitialValues = new Button("Paste Initial Values");
		btnPasteInitialValues.setStyle("-fx-font-size: "+BUTTON_TEXT_FONT);
		btnPasteInitialValues.setOnAction(event -> {
			
			String pastedValue = clipboard.getString();
			pastedValue = pastedValue.replaceAll("\\(", "\\!");
			pastedValue = pastedValue.replaceAll("\\)", "\\?");
			System.out.println(pastedValue);
			Pattern p = Pattern.compile("\\!X=(\\d+.\\d+),Y=(\\d+.\\d+)\\?");
	        Matcher m = p.matcher(pastedValue);
	        if(m.find())
	        {
	        	txtInitialX.setText(m.group(1));
	        	txtInitialY.setText(m.group(2));
	        	System.out.println(m.group(1));
	        	System.out.println(m.group(2));
	        }
			
		});
		
		HBox hbxCopyAndPaste = new HBox(23, btnCopyInitialValues, btnPasteInitialValues);

		Label lblDesiredX = new Label("* Desired X Size: ");
		lblDesiredX.setStyle("-fx-font-size: "+INSTR_FONT);
		TextField txtDesiredX = new TextField();
		HBox hbxDesiredX = new HBox(23, lblDesiredX, txtDesiredX);

		Label lblDesiredY = new Label("* Desired Y Size: ");
		lblDesiredY.setStyle("-fx-font-size: "+INSTR_FONT);
		TextField txtDesiredY = new TextField();
		HBox hbxDesiredY = new HBox(23, lblDesiredY, txtDesiredY);
		
		Button btnCopyDesiredValues = new Button("Copy Desired Values");
		btnCopyDesiredValues.setStyle("-fx-font-size: "+BUTTON_TEXT_FONT);
		btnCopyDesiredValues.setOnAction(event -> {
			if(!txtDesiredX.getText().toString().equals("") && !txtDesiredY.getText().toString().equals(""))
			{
					content.putString("(X="+txtDesiredX.getText().toString()+",Y="+txtDesiredY.getText().toString()+")");
					clipboard.setContent(content);
			}
		});

		Label lblScalerMultiplier = new Label("* Scaler Multiplier: ");
		lblScalerMultiplier.setStyle("-fx-font-size: "+INSTR_FONT);
		TextField txtScalerMultiplier = new TextField();
		HBox hbxScalerMultiplier = new HBox(23, lblScalerMultiplier, txtScalerMultiplier);

		Button btnCalculate = new Button("Calculate");
		btnCalculate.setStyle("-fx-font-size: "+BUTTON_TEXT_FONT);
		btnCalculate.setOnAction(event -> {
			if(!txtInitialX.getText().toString().equals("") && !txtInitialY.getText().toString().equals(""))
			{
				try
				{
					initX = Float.parseFloat(txtInitialX.getText().toString());
					initY = Float.parseFloat(txtInitialY.getText().toString());
					desX = Float.parseFloat(txtDesiredX.getText().toString());
					desY = Float.parseFloat(txtDesiredY.getText().toString());
					multiplier = Float.parseFloat(txtScalerMultiplier.getText().toString());
					
				}
				catch (NumberFormatException e)
				{
					txtInitialX.setText("Error!");
					txtInitialY.setText("Error!");
					txtDesiredX.setText("Error!");
					txtDesiredY.setText("Error!");
					txtScalerMultiplier.setText("Error!");
				}


				if(!txtDesiredX.getText().toString().equals("") && txtDesiredY.getText().toString().equals("") && txtScalerMultiplier.getText().toString().equals(""))
				{
					multiplier = desX/initX;
					desY = initY*multiplier;
					txtScalerMultiplier.setText(String.valueOf(multiplier));
					txtDesiredY.setText(String.valueOf(desY));
				}
				else if(txtDesiredX.getText().toString().equals("") && !txtDesiredY.getText().toString().equals("") && txtScalerMultiplier.getText().toString().equals(""))
				{
					multiplier = desY/initY;
					desX = initX*multiplier;
					txtScalerMultiplier.setText(String.valueOf(multiplier));
					txtDesiredX.setText(String.valueOf(desX));
				}
				else if(txtDesiredX.getText().toString().equals("") && txtDesiredY.getText().toString().equals("") && !txtScalerMultiplier.getText().toString().equals(""))
				{
					desX = initX*multiplier;
					desY = initY*multiplier;
					txtDesiredX.setText(String.valueOf(desX));
					txtDesiredY.setText(String.valueOf(desY));

				}
			}
			
		});
		

		Button btnClearNew = new Button("Clear New Values");
		btnClearNew.setStyle("-fx-font-size: "+BUTTON_TEXT_FONT);
		btnClearNew.setOnAction(event -> {
			txtDesiredX.setText("");
			txtDesiredY.setText("");
			txtScalerMultiplier.setText("");
		});
		
		Button btnReset = new Button("Reset");
		btnReset.setStyle("-fx-font-size: "+BUTTON_TEXT_FONT);
		btnReset.setOnAction(event -> {
			txtInitialX.setText("");
			txtInitialY.setText("");
			txtDesiredX.setText("");
			txtDesiredY.setText("");
			txtScalerMultiplier.setText("");
		});
		vbxResize.getChildren().addAll(apTitleHeader, hbxInitialX, hbxInitialY, hbxCopyAndPaste, hbxDesiredX, hbxDesiredY, btnCopyDesiredValues, hbxScalerMultiplier, btnCalculate, btnClearNew, btnReset);

		//creating the scene
		Scene sceneResize = new Scene(vbxResize);

		//setting/showing the stage
		resizeStage.setTitle("Image Resizer Calculator");
		resizeStage.setScene(sceneResize);
		resizeStage.show();
		Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
		resizeStage.setX((screenBound.getWidth() - resizeStage.getWidth()) / 2);
		resizeStage.setY((screenBound.getHeight() - resizeStage.getHeight()) / 2);
		resizeStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent we)
			{
				System.exit(0);
			}
		});  
	}

	public static void main(String[] args) {
		launch(args);

	}


}
