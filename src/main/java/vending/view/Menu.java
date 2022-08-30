package vending.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static class Option {
        private final String text;
        private final Command command;

        public Option(final String text, final Command command) {
            this.text = text;
            this.command = command;
        }

        public String getText() {
            return text;
        }

        public Command getCommand() {
            return command;
        }

        @Override
        public String toString() {
            return "MenuOption{" +
                    "text='" + text + '\'' +
                    ", command=" + command +
                    '}';
        }
    }

    private final String title;
    private final List<Option> options;
    private final ConsoleWriter writer;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(final String title, final List<Option> options, final ConsoleWriter consoleWriter) {
        this.title = title;
        this.options = new ArrayList<>(options);
        this.writer = consoleWriter;
    }

    public String getTitle() {
        return title;
    }

    public List<Option> getOptions() {
        return options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Menu menu)) {
            return false;
        }

        if (getTitle() != null ? !getTitle().equals(menu.getTitle()) : menu.getTitle() != null) {
            return false;
        }

        return getOptions() != null ? getOptions().equals(menu.getOptions()) : menu.getOptions() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getOptions() != null ? getOptions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "title='" + title + '\'' +
                ", options=" + options +
                '}';
    }

    public void show() {
        while (true) {
            writer.printMessage(String.format("\n%s%n", title));
            for (int i = 0; i < options.size(); i++) {
                writer.printMessage(String.format("%2d. %s%n", i + 1, options.get(i).getText()));
            }

            int choice = -1;

            while (choice < 1 || choice > options.size()) {
                writer.printMessage(String.format("Please select option (1 - %s):", options.size()));
                var choiceStr = scanner.nextLine();
                try {
                    choice = Integer.parseInt(choiceStr);
                    if (choice > options.size() || choice <= 0) {
                        writer.printlnMessage("Please select option in given range.");
                        choice = Integer.parseInt(choiceStr);
                    }
                } catch (NumberFormatException ex) {
                    writer.printlnMessage("Invalid choice. Please enter a valid number between 1 and " + options.size());
                }
            }

            String result = options.get(choice - 1).getCommand().execute();
            writer.printlnMessage(result);

            if (choice == options.size()) {
                break;
            }
        }
    }
}
