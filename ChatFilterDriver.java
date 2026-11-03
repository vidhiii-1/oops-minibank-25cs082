package practical4;

import java.util.Scanner;

public class ChatFilterDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 4 - Task 2: Chat-log Filter ===");
        String[] logs = {
            "10:05 alice Hello there",
            "10:07 bob Just checking in",
            "10:08 charlie hello everyone!",
            "10:09 malformed_line_without_three_parts",
            "10:12 daisy HELLO world"
        };

        Scanner scanner = new Scanner(System.in);
        String keyword = "hello";
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                keyword = line;
            }
        }

        System.out.println("Searching chat logs for keyword: \"" + keyword + "\"");

        int matches = 0;
        StringBuilder report = new StringBuilder();

        for (String log : logs) {
            String[] parts = log.split(" ", 3);
            if (parts.length < 3) {
                // skip malformed line
                continue;
            }
            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                matches++;
                report.append(time).append(" ").append(user).append(": ").append(message).append("\n");
            }
        }

        System.out.println("Matches: " + matches);
        System.out.print(report);
    }
}
