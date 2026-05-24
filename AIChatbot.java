import java.util.HashMap;
import java.util.Scanner;

public class AIChatbot {

    // Knowledge Base
    static HashMap<String, String> knowledgeBase = new HashMap<>();

    // Train chatbot
    static void trainBot() {

        knowledgeBase.put("hello", "Hello! How can I help you?");
        knowledgeBase.put("hi", "Hi there!");
        knowledgeBase.put("how are you", "I am fine. Thanks for asking!");
        knowledgeBase.put("what is java", "Java is a powerful object-oriented programming language.");
        knowledgeBase.put("who created java", "Java was created by James Gosling.");
        knowledgeBase.put("bye", "Goodbye! Have a nice day.");
    }

    // NLP Processing
    static String getResponse(String userInput) {

        userInput = userInput.toLowerCase();

        // Keyword Matching
        for (String keyword : knowledgeBase.keySet()) {

            if (userInput.contains(keyword)) {
                return knowledgeBase.get(keyword);
            }
        }

        return "Sorry, I don't understand that yet.";
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        trainBot();

        System.out.println("===== AI Chatbot =====");
        System.out.println("Type 'exit' to stop chatting.\n");

        while (true) {

            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Bot: Goodbye!");
                break;
            }

            String response = getResponse(userInput);

            System.out.println("Bot: " + response);
        }

        scanner.close();
    }
}