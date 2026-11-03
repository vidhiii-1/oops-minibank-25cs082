package practical11;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class LogAnalyzerDriver {
    public static void main(String[] args) throws IOException {
        System.out.println("=== Practical 11 - Task 2: NIO Log Analyzer ===");
        Path tempLog = Paths.get("d:/WDF____/Practical_File_25CS069/test_audit.log");
        List<String> lines = List.of(
            "INFO User login 10:00",
            "WARN Failed password attempt",
            "INFO Balance enquiry AC0001",
            "ERROR Network timeout",
            "INFO User logout 10:30"
        );
        Files.write(tempLog, lines);

        long lineCount = Files.lines(tempLog).count();
        long infoCount = Files.lines(tempLog).filter(l -> l.contains("INFO")).count();
        long size = Files.size(tempLog);

        System.out.println("Log file analyzed: " + tempLog.getFileName());
        System.out.println("Total lines : " + lineCount);
        System.out.println("Keyword 'INFO' occurrences: " + infoCount);
        System.out.println("File size (bytes): " + size);

        Files.deleteIfExists(tempLog);
    }
}
