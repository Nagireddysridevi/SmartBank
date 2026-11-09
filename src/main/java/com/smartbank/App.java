
package com.smartbank;

import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class App extends Application {

    private Stage stage;
    private String accountNumber = "";

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showLogin();
    }

    // Apply the external CSS file to a scene
    private void applyStyles(Scene scene) {
        var css = getClass().getResource("/styles/App.css");

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.out.println("App.css was not found!");
        }
    }

    // Animate pages when they appear
    private void animatePage(Node node) {
        FadeTransition fade = new FadeTransition(
            Duration.millis(400), node
        );
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(
            Duration.millis(400), node
        );
        slide.setFromY(15);
        slide.setToY(0);

        fade.play();
        slide.play();
    }

    // LOGIN SCREEN
    private void showLogin() {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("login-shell");

        VBox hero = new VBox(25);
        hero.getStyleClass().add("hero-panel");
        hero.setPadding(new Insets(45));
        hero.setAlignment(Pos.CENTER_LEFT);

        Label brand = new Label("SMARTBANK");
        brand.getStyleClass().add("hero-brand");

        Label heroTitle = new Label(
            "Banking that\nmoves with you."
        );
        heroTitle.getStyleClass().add("hero-title");

        Label heroText = new Label(
            "A simpler, smarter and more secure way\n" +
            "to manage your money."
        );
        heroText.getStyleClass().add("hero-description");

        VBox bankCard = new VBox(18);
        bankCard.getStyleClass().add("visual-card");
        bankCard.setMaxWidth(310);

        Label cardName = new Label("SMARTBANK");
        cardName.getStyleClass().add("visual-card-brand");

        Label chip = new Label("▦");
        chip.getStyleClass().add("card-chip");

        Label cardNumber = new Label("••••    ••••    ••••    2048");
        cardNumber.getStyleClass().add("visual-card-number");

        Label cardHolder = new Label("YOUR EVERYDAY BANKING PARTNER");
        cardHolder.getStyleClass().add("visual-card-footer");

        bankCard.getChildren().addAll(
            cardName, chip, cardNumber, cardHolder
        );

        hero.getChildren().addAll(
            brand, heroTitle, heroText, bankCard
        );

        VBox loginCard = new VBox(18);
        loginCard.getStyleClass().add("login-card");
        loginCard.setMaxWidth(390);
        loginCard.setAlignment(Pos.CENTER_LEFT);

        Label welcome = new Label("Welcome back");
        welcome.getStyleClass().add("login-title");

        Label subtitle = new Label(
            "Sign in to continue to your account"
        );
        subtitle.getStyleClass().add("login-subtitle");

        Label accountLabel = new Label("Account Number");
        accountLabel.getStyleClass().add("field-label");

        TextField accountField = new TextField();
        accountField.setPromptText("Enter your account number");
        accountField.getStyleClass().add("input-field");
        accountField.setMaxWidth(Double.MAX_VALUE);

        Label pinLabel = new Label("PIN");
        pinLabel.getStyleClass().add("field-label");

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Enter your PIN");
        pinField.getStyleClass().add("input-field");
        pinField.setMaxWidth(Double.MAX_VALUE);

        Button loginButton = new Button("SIGN IN");
        loginButton.getStyleClass().add("primary-button");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        Label note = new Label(
            "Demo frontend: any non-empty account number and PIN can be used."
        );
        note.getStyleClass().add("small-note");
        note.setWrapText(true);

        loginButton.setOnAction(e -> {
            String enteredAccount = accountField.getText().trim();
            String enteredPin = pinField.getText().trim();

            if (enteredAccount.isEmpty() || enteredPin.isEmpty()) {
                showAlert("Please enter your account number and PIN.");
            } else {
                accountNumber = enteredAccount;
                showDashboard();
            }
        });

        pinField.setOnAction(e -> loginButton.fire());

        loginCard.getChildren().addAll(
            welcome,
            subtitle,
            accountLabel,
            accountField,
            pinLabel,
            pinField,
            loginButton,
            note
        );

        StackPane loginArea = new StackPane(loginCard);
        loginArea.getStyleClass().add("login-area");
        loginArea.setPadding(new Insets(35));

        root.setLeft(hero);
        root.setCenter(loginArea);

        Scene scene = new Scene(root, 1150, 720);
        applyStyles(scene);

        stage.setTitle("SmartBank | Welcome");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        animatePage(loginCard);
    }

    // MAIN DASHBOARD
    private void showDashboard() {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-background");

        VBox sidebar = new VBox(12);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(28, 18, 25, 18));
        sidebar.setPrefWidth(235);

        Label logo = new Label("✦  SMARTBANK");
        logo.getStyleClass().add("sidebar-logo");

        Label menuLabel = new Label("MAIN MENU");
        menuLabel.getStyleClass().add("sidebar-section");

        Button home = menuButton("⌂   Dashboard");
        Button account = menuButton("◉   My Account");
        Button deposit = menuButton("＋   Deposit");
        Button withdraw = menuButton("−   Withdraw");
        Button transfer = menuButton("⇄   Transfer Money");
        Button history = menuButton("◷   Transactions");
        Button statements = menuButton("▤   Statements");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logout = menuButton("↪   Logout");
        logout.getStyleClass().add("logout-button");

        List<Button> menuButtons = List.of(
            home, account, deposit, withdraw,
            transfer, history, statements
        );

        sidebar.getChildren().addAll(
            logo, menuLabel, home, account, deposit,
            withdraw, transfer, history, statements,
            spacer, logout
        );

        // Top navigation bar
        BorderPane topbar = new BorderPane();
        topbar.getStyleClass().add("topbar");
        topbar.setPadding(new Insets(18, 30, 18, 30));

        Label pageName = new Label("Overview");
        pageName.getStyleClass().add("topbar-title");

        Label user = new Label("Welcome  |  " + accountNumber);
        user.getStyleClass().add("user-label");

        topbar.setLeft(pageName);
        topbar.setRight(user);

        home.setOnAction(e -> {
            selectMenu(home, menuButtons);
            pageName.setText("Overview");
            showOverview(root);
        });

        account.setOnAction(e -> {
            selectMenu(account, menuButtons);
            pageName.setText("My Account");
            showAccount(root);
        });

        deposit.setOnAction(e -> {
            selectMenu(deposit, menuButtons);
            pageName.setText("Deposit");
            showMoneyPage(root, "Deposit");
        });

        withdraw.setOnAction(e -> {
            selectMenu(withdraw, menuButtons);
            pageName.setText("Withdraw");
            showMoneyPage(root, "Withdraw");
        });

        transfer.setOnAction(e -> {
            selectMenu(transfer, menuButtons);
            pageName.setText("Transfer Money");
            showTransfer(root);
        });

        history.setOnAction(e -> {
            selectMenu(history, menuButtons);
            pageName.setText("Transactions");
            showHistory(root);
        });

        statements.setOnAction(e -> {
            selectMenu(statements, menuButtons);
            pageName.setText("Statements");
            showStatements(root);
        });

        logout.setOnAction(e -> {
            showLogin();
        });

        root.setLeft(sidebar);
        root.setTop(topbar);

        selectMenu(home, menuButtons);
        showOverview(root);

        Scene scene = new Scene(root, 1150, 720);
        applyStyles(scene);

        stage.setTitle("SmartBank | Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    // SIDEBAR BUTTON
    private Button menuButton(String name) {
        Button button = new Button(name);
        button.getStyleClass().add("menu-button");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        return button;
    }

    // SELECT ACTIVE SIDEBAR BUTTON
    private void selectMenu(Button selected, List<Button> buttons) {
        for (Button button : buttons) {
            button.getStyleClass().remove("active-menu");
        }

        if (!selected.getStyleClass().contains("active-menu")) {
            selected.getStyleClass().add("active-menu");
        }
    }

    // PAGE HEADING
    private Label heading(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("page-title");
        return label;
    }

    private Label description(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("page-description");
        return label;
    }

    // DASHBOARD OVERVIEW
    private void showOverview(BorderPane root) {

        VBox content = new VBox(24);
        content.getStyleClass().add("page-content");
        content.setPadding(new Insets(32));

        Label welcome = heading("Good day!");
        Label intro = description(
            "Here is your account overview."
        );

        VBox welcomeBox = new VBox(7, welcome, intro);

        HBox cards = new HBox(18);
        cards.getChildren().addAll(
            createCard("AVAILABLE BALANCE", "₹25,000.00", "◈"),
            createCard("ACCOUNT TYPE", "Savings", "▣"),
            createCard("ACCOUNT STATUS", "Active", "✓")
        );

        Label quickTitle = heading("Quick Services");

        HBox services = new HBox(15);

        Button deposit = new Button("＋  Deposit");
        deposit.getStyleClass().add("primary-button");

        Button withdraw = new Button("−  Withdraw");
        withdraw.getStyleClass().add("secondary-button");

        Button transfer = new Button("⇄  Transfer");
        transfer.getStyleClass().add("secondary-button");

        deposit.setOnAction(e -> showMoneyPage(root, "Deposit"));
        withdraw.setOnAction(e -> showMoneyPage(root, "Withdraw"));
        transfer.setOnAction(e -> showTransfer(root));

        services.getChildren().addAll(deposit, withdraw, transfer);

        VBox recentBox = new VBox(12);
        recentBox.getStyleClass().add("content-card");

        Label recentTitle = heading("Recent Activity");

        recentBox.getChildren().addAll(
            recentTitle,
            transactionRow("Salary Credit", "09 Nov 2026", "+ ₹5,000"),
            transactionRow("Online Payment", "08 Nov 2026", "- ₹2,000"),
            transactionRow("Money Transfer", "07 Nov 2026", "- ₹1,000")
        );

        content.getChildren().addAll(
            welcomeBox, cards, quickTitle, services, recentBox
        );

        setPage(root, content);
    }

    // DASHBOARD CARDS
    private VBox createCard(String title, String value, String symbol) {

        Label icon = new Label(symbol);
        icon.getStyleClass().add("card-icon");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("card-heading");

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("card-value");

        VBox card = new VBox(13, icon, titleLabel, valueLabel);
        card.getStyleClass().add("account-card");
        card.setPrefWidth(260);
        HBox.setHgrow(card, Priority.ALWAYS);

        return card;
    }

    // TRANSACTION ROW
    private HBox transactionRow(
        String descriptionText, String date, String amount
    ) {
        Label name = new Label(descriptionText);
        name.getStyleClass().add("transaction-name");

        Label dateLabel = new Label(date);
        dateLabel.getStyleClass().add("transaction-date");

        Label amountLabel = new Label(amount);
        amountLabel.getStyleClass().add("transaction-amount");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(15, name, dateLabel, spacer, amountLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("transaction-row");

        return row;
    }

    // MY ACCOUNT
    private void showAccount(BorderPane root) {

        VBox content = new VBox(18);
        content.getStyleClass().add("page-content");
        content.setPadding(new Insets(32));

        VBox details = new VBox(18);
        details.getStyleClass().add("content-card");

        details.getChildren().addAll(
            heading("Account Details"),
            detailRow("Account Holder", "SmartBank Customer"),
            detailRow("Account Number", accountNumber),
            detailRow("Account Type", "Savings Account"),
            detailRow("Branch", "Main Branch"),
            detailRow("Account Status", "Active")
        );

        content.getChildren().addAll(
            heading("My Account"),
            description("View your banking information."),
            details
        );

        setPage(root, content);
    }

    private HBox detailRow(String labelText, String valueText) {
        Label label = new Label(labelText);
        label.getStyleClass().add("detail-label");

        Label value = new Label(valueText);
        value.getStyleClass().add("detail-value");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(15, label, spacer, value);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("detail-row");

        return row;
    }

    // DEPOSIT AND WITHDRAW
    private void showMoneyPage(BorderPane root, String type) {

        VBox content = new VBox(20);
        content.getStyleClass().add("page-content");
        content.setPadding(new Insets(32));

        VBox form = new VBox(15);
        form.getStyleClass().add("content-card");
        form.setMaxWidth(550);

        Label amountLabel = new Label("Amount (₹)");
        amountLabel.getStyleClass().add("field-label");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.getStyleClass().add("input-field");

        Button submit = new Button(type);
        submit.getStyleClass().add("primary-button");

        submit.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(
                    amountField.getText().trim()
                );

                if (!Double.isFinite(amount) || amount <= 0) {
                    showAlert("Enter an amount greater than zero.");
                } else {
                    showAlert(
                        type + " request received for ₹" +
                        String.format("%.2f", amount) +
                        ". Backend processing is not connected yet."
                    );
                    amountField.clear();
                }

            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid amount.");
            }
        });

        form.getChildren().addAll(
            amountLabel, amountField, submit
        );

        content.getChildren().addAll(
            heading(type),
            description("Enter the amount for your " + type.toLowerCase() + " request."),
            form
        );

        setPage(root, content);
    }

    // TRANSFER MONEY
    private void showTransfer(BorderPane root) {

        VBox content = new VBox(20);
        content.getStyleClass().add("page-content");
        content.setPadding(new Insets(32));

        VBox form = new VBox(15);
        form.getStyleClass().add("content-card");
        form.setMaxWidth(550);

        Label receiverLabel = new Label("Receiver Account Number");
        receiverLabel.getStyleClass().add("field-label");

        TextField receiverField = new TextField();
        receiverField.setPromptText("Enter receiver account number");
        receiverField.getStyleClass().add("input-field");

        Label amountLabel = new Label("Amount (₹)");
        amountLabel.getStyleClass().add("field-label");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.getStyleClass().add("input-field");

        Button transferButton = new Button("Continue Transfer");
        transferButton.getStyleClass().add("primary-button");

        transferButton.setOnAction(e -> {
            String receiver = receiverField.getText().trim();

            try {
                double amount = Double.parseDouble(
                    amountField.getText().trim()
                );

                if (receiver.isEmpty() || receiver.equals(accountNumber)) {
                    showAlert("Enter a valid receiver account number.");
                } else if (!Double.isFinite(amount) || amount <= 0) {
                    showAlert("Enter an amount greater than zero.");
                } else {
                    showAlert(
                        "Transfer request received for ₹" +
                        String.format("%.2f", amount) +
                        ". Backend processing is not connected yet."
                    );
                    receiverField.clear();
                    amountField.clear();
                }

            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid amount.");
            }
        });

        form.getChildren().addAll(
            receiverLabel, receiverField,
            amountLabel, amountField, transferButton
        );

        content.getChildren().addAll(
            heading("Transfer Money"),
            description("Send money to another account."),
            form
        );

        setPage(root, content);
    }

    // TRANSACTION HISTORY
    private void showHistory(BorderPane root) {

        VBox content = new VBox(20);
        content.getStyleClass().add("page-content");
        content.setPadding(new Insets(32));

        VBox table = new VBox(0);
        table.getStyleClass().add("content-card");

        table.getChildren().addAll(
            heading("All Transactions"),
            transactionRow("Salary Credit", "09 Nov 2026", "+ ₹5,000"),
            transactionRow("Online Payment", "08 Nov 2026", "- ₹2,000"),
            transactionRow("Money Transfer", "07 Nov 2026", "- ₹1,000"),
            transactionRow("Cash Deposit", "06 Nov 2026", "+ ₹3,000")
        );

        content.getChildren().addAll(
            heading("Transaction History"),
            description("Review your recent account activity."),
            table
        );

        setPage(root, content);
    }

    // STATEMENTS
    private void showStatements(BorderPane root) {

        VBox content = new VBox(20);
        content.getStyleClass().add("page-content");
        content.setPadding(new Insets(32));

        VBox statementCard = new VBox(18);
        statementCard.getStyleClass().add("content-card");

        Label info = description(
            "Your account statements and downloadable reports will appear here."
        );

        Button downloadButton = new Button("Download Statement");
        downloadButton.getStyleClass().add("primary-button");

        downloadButton.setOnAction(e ->
            showAlert("Statement download will be connected with the backend.")
        );

        statementCard.getChildren().addAll(info, downloadButton);

        content.getChildren().addAll(
            heading("Account Statements"),
            statementCard
        );

        setPage(root, content);
    }

    // CHANGE THE MAIN PAGE AND ANIMATE IT
    private void setPage(BorderPane root, Node content) {
        root.setCenter(content);
        animatePage(content);
    }

    // ALERT
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SmartBank");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
