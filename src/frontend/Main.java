package frontend;

import java.io.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class Main extends Application {
    private static final int WIN_X = 720;
    private static final int WIN_Y = 640;
    private static final String ROOT_DIR = new File("").getAbsolutePath().toString();

    private File imgFile;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("emoTRAINER");
        primaryStage.getIcons().add(new Image("https://cdn.onlinewebfonts.com/svg/img_529394.png"));

        primaryStage.setOnShown(actionEvent -> {
            Runtime runtime = Runtime.getRuntime();
            try {
                String filePath = ROOT_DIR + "/src/backend/init.py";

                Process process = runtime.exec("python " + filePath);

                InputStream inputStream = process.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String initCodeStr = bufferedReader.readLine();
                int initCode = Integer.parseInt(initCodeStr);
                System.out.println(initCode);

                if (initCode == 1) {
                    final int POPUP_X = 440;
                    final int POPUP_Y = 80;

                    Stage popupStage = new Stage();
                    popupStage.setTitle("Welcome!");
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.initOwner(primaryStage);

                    Image popupIcon = new Image(
                            "https://th.bing.com/th/id/R.55edc3eeae97d492b1ad8b57d201f7e1?rik=f3xjSfyY1CXZFA&riu=http%3a%2f%2fcdn.onlinewebfonts.com%2fsvg%2fdownload_440385.png&ehk=PkpTWdztaF71g%2fPkl6yo2OL8Y1kIHfjWYhT7RU7DcTo%3d&risl=&pid=ImgRaw&r=0");
                    popupStage.getIcons().add(popupIcon);

                    Text popupMessage = new Text(
                            "Welcome to emoTRAINER, I hope you find it useful in your self-training journey!");
                    Button thankYouButton = new Button("Thank you!");

                    popupMessage.setTextAlignment(TextAlignment.CENTER);
                    thankYouButton.setAlignment(Pos.CENTER);

                    VBox popupContent = new VBox(popupMessage, thankYouButton);
                    popupContent.setAlignment(Pos.CENTER);

                    thankYouButton.setOnAction(innerActionEvent -> {
                        popupStage.close();
                    });

                    Scene popupScene = new Scene(popupContent, POPUP_X, POPUP_Y);
                    popupStage.setScene(popupScene);
                    popupStage.showAndWait();
                }
            }
            catch (IOException ioException) {
                System.out.println("CRITICAL ERROR - COULD NOT INITIALIZE!");
            }
        });

        Button chooseFileButton = new Button("Select Image File");
        Button detectButton = new Button("Detect");
        Text status = new Text();

        chooseFileButton.setAlignment(Pos.CENTER);
        detectButton.setAlignment(Pos.CENTER);
        status.setTextAlignment(TextAlignment.CENTER); 

        HBox controls = new HBox(chooseFileButton, detectButton);
        VBox content = new VBox(controls, status);

        controls.setAlignment(Pos.CENTER);
        content.setAlignment(Pos.CENTER);

        chooseFileButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialDirectory(new File(ROOT_DIR + "/data/imgs"));
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.png", "*.jpeg", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("PNG files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG files", "*.jpeg", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF files", "*.gif")
            );

            this.imgFile = fileChooser.showOpenDialog(primaryStage);

            try {
                if (content.getChildren().size() == 2) {
                    ImageView imageView = new ImageView(new Image(this.imgFile.getPath()));

                    imageView.setX(WIN_X / 2);
                    imageView.setY(WIN_Y / 2);

                    imageView.setFitWidth(WIN_X * 0.75);
                    imageView.setFitHeight(WIN_Y * 0.75);
                    
                    content.getChildren().add(0, imageView);
                }
                else {
                    ImageView imageView = (ImageView) content.getChildren().get(0);
                    imageView.setImage(new Image(this.imgFile.getPath()));
                }
            }
            catch (NullPointerException nullPointerException) {
                System.out.println("EXCEPTION CAUGHT - IMAGE SELECTION CANCELLED!");
            }
        });
        detectButton.setOnAction(actionEvent -> {
            Runtime runtime = Runtime.getRuntime();
            try {
                String filePath = ROOT_DIR + "/src/backend/emotion_prediction.py";
                String command = "python " + filePath + " \"" + this.imgFile.getPath() + "\"";
                System.out.println(command);

                Process process = runtime.exec(command);

                InputStream inputStream = process.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                
                String results = "" + bufferedReader.lines().toArray()[1];
                String[] resultsList = results.split(":");
                
                String mostLikelyEmotion = resultsList[0];
                String mostLikelyEmotionProb = resultsList[1];

                final int EMOTION_REPORT_X = 440;
                final int EMOTION_REPORT_Y =60;

                Stage emotionReportStage = new Stage();
                emotionReportStage.setTitle("Emotion Report");
                emotionReportStage.initModality(Modality.APPLICATION_MODAL);
                emotionReportStage.initOwner(primaryStage);

                Image emotionReportIcon = new Image(
                        "https://www.pinclipart.com/picdir/middle/17-173384_report-clipart-report-icon-3-data-icon-noun.png");
                emotionReportStage.getIcons().add(emotionReportIcon);

                Text emotionReportMessage = new Text("The predicted emotion is " + mostLikelyEmotion + ", with a likelihood of "
                        + mostLikelyEmotionProb + "!");
                Button thankYouButton = new Button("Thank you!");

                emotionReportMessage.setTextAlignment(TextAlignment.CENTER);
                thankYouButton.setAlignment(Pos.CENTER);

                VBox emotionReportContent = new VBox(emotionReportMessage, thankYouButton);
                emotionReportContent.setAlignment(Pos.CENTER);

                thankYouButton.setOnAction(innerActionEvent -> {
                    emotionReportStage.close();
                });

                Scene emotionReportScene = new Scene(emotionReportContent, EMOTION_REPORT_X, EMOTION_REPORT_Y);
                emotionReportStage.setScene(emotionReportScene);
                emotionReportStage.showAndWait();
            }
            catch (IOException ioException) {
                System.out.println("I/O ERROR - COULD NOT RUN DETECTION PROGRAM!");
            }
            // catch (NullPointerException nullPointerException) {
            //     System.out.println("EXCEPTION CAUGHT - IMAGE FILE NOT SELECTED!");
            // }
            // catch (Exception exception) {
            //     System.out.println("SOME OTHER ERROR - DEBUG!");
            // }
        });

        Scene primaryScene = new Scene(content, WIN_X, WIN_Y);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}