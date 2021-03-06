package com.ClassroomSlack.main.template;

import com.ClassroomSlack.database.lists.getthreadsList;

import com.ClassroomSlack.main.windows.createNewThread.newThread;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class threads {

    public static VBox channelVB;
    public static VBox directMessageVB;
    public static BorderPane view;

    public static BorderPane chatDetails(String companyName, String userName, String currentUserMailId ){

        view = new BorderPane();

        channelVB = new VBox(0);
        directMessageVB = new VBox(0);

        VBox companyHeader = new VBox(5);
        companyHeader.setPadding(new Insets(0,0,10,0));
        companyHeader.setAlignment(Pos.TOP_LEFT);

        Label fullName = new Label(userName);
        fullName.setPadding(new Insets(10,0,0,0));
        fullName.setFont(new Font("Cambria", 25));
        fullName.setTextFill(Color.web("#000"));

        Label company = GlyphsDude.createIconLabel( FontAwesomeIcon.BANK,
                companyName,
                "20",
                "20",
                ContentDisplay.LEFT );
        company.setFont(new Font("Cambria", 12));
        company.setTextFill(Color.web("#000"));

        companyHeader.getChildren().addAll(fullName, company);

        String[][] threadsLinked = getthreadsList.getthreadsList(companyName);
        if (threadsLinked[0][0].equals("success")){
            for (int i=1;i<threadsLinked.length;++i)
                if (threadsLinked[i][0].equals(userName) && threadsLinked[i][2].equals("direct message"))
                    addThreads(companyName, threadsLinked[i][0]+" (you)", threadsLinked[i][1], threadsLinked[i][2], currentUserMailId);
                else
                    addThreads(companyName, threadsLinked[i][0], threadsLinked[i][1], threadsLinked[i][2], currentUserMailId);
        }

        ScrollPane channelScroller = new ScrollPane( channelVB );
        channelScroller.setStyle("-fx-background-color: transparent");
        channelScroller.setPadding(new Insets(0,0,15,0));
        channelScroller.setFitToWidth(true);
        channelScroller.setVvalue(1.0);
        channelScroller.vvalueProperty().bind(channelVB.heightProperty());

        ScrollPane directMessageScroller = new ScrollPane( directMessageVB );
        directMessageScroller.setStyle("-fx-background-color: transparent");
        directMessageScroller.setFitToWidth(true);
        directMessageScroller.setVvalue(1.0);
        directMessageScroller.vvalueProperty().bind(directMessageVB.heightProperty());

        BorderPane channelComplete = new BorderPane(
                channelScroller,
                addThreadTitle(companyName,"channel", currentUserMailId),
                null,
                null,
                null);

        BorderPane directMessageComplete = new BorderPane(
                directMessageScroller,
                addThreadTitle(companyName,"direct message", currentUserMailId),
                null,
                null,
                null);

        BorderPane threads = new BorderPane(
                new BorderPane(
                        directMessageComplete,
                        channelComplete,
                        null,
                        null,
                        null),
                companyHeader ,
                null,
                null,
                null);
        threads.setPrefWidth(200);
        threads.setPadding(new Insets(10,0,10,10));
        threads.setStyle("-fx-background-color: #858585");

        view.setLeft(threads);

        return view;

    }

    public static void addThreads(String companyName, String threadName, String threadMailId, String threadType, String currentUserMailId){
        Label newThread = new Label("  "+threadName);
        newThread.setAlignment(Pos.BASELINE_LEFT);
        newThread.setPadding(new Insets(5,10,5,10));
        newThread.setFont(new Font("Arial", 13));
        newThread.setTextFill(Color.web("#fff"));

        StackPane newThreadPane = new StackPane(newThread);
        newThreadPane.setStyle("-fx-background-color: transparent");
        newThreadPane.setOnMouseEntered(e-> newThreadPane.setStyle("-fx-background-color: #3CB371"));
        newThreadPane.setOnMouseExited(e-> newThreadPane.setStyle("-fx-background-color: transparent"));
        newThreadPane.setCursor(Cursor.HAND);

        newThread.setMaxWidth(200);

        if (threadType.equals("channel")){
            channelVB.getChildren().add(newThreadPane);
            newThreadPane.setOnMouseClicked(e->
                    view.setCenter(channelMessages.channelMessages(companyName, threadName, currentUserMailId))
            );
        }
        else{
            directMessageVB.getChildren().add(newThreadPane);
            newThreadPane.setOnMouseClicked(e-> {
                    view.setCenter(directMessages.directMessages(companyName, threadName, threadMailId, currentUserMailId));
            });
        }
    }

    public static Label addThreadTitle(String companyName, String threadType, String currentUserMailId){
        Label title;

        if (threadType.equals("channel")){
            title = GlyphsDude.createIconLabel( FontAwesomeIcon.PLUS_CIRCLE,
                    "CHANNELS   ",
                    "20",
                    "15",
                    ContentDisplay.RIGHT );
            channelVB.getChildren().add(title);
            newThread ob = new newThread();
            title.setCursor(Cursor.HAND);
            title.setOnMouseClicked(e-> {
                String status = ob.newThread(companyName, threadType);
                if (!status.equals(""))
                    addThreads(companyName, status,"", threadType, currentUserMailId);
            });
        }
        else{
            title = new Label("DIRECT MESSAGES");
            title.setFont(new Font("Arial", 15));
            directMessageVB.getChildren().add(title);
        }
        title.setTextFill(Color.web("#fff"));
        title.setAlignment(Pos.BASELINE_LEFT);
        title.setPadding(new Insets(8));
        title.setMaxWidth(220);

        return title;
    }

}
