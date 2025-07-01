import java.io.*;
import java.util.*;

public class task3 {
    static final String fileName = "contacts.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- Contact Manager ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete All Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addContact(sc);
                case 2 -> viewContacts();
                case 3 -> editContact(sc);
                case 4 -> deleteAllContacts();
                case 5 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addContact(Scanner sc) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Phone: ");
            String phone = sc.nextLine();
            System.out.print("Enter Email: ");
            String email = sc.nextLine();
            fw.write(name + "," + phone + "," + email + "\n");
            System.out.println("Contact saved!");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    static void viewContacts() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int count = 1;
            System.out.println("\n--- Contact List ---");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    System.out.println(count++ + ". Name: " + parts[0] + ", Phone: " + parts[1] + ", Email: " + parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("No contacts found.");
        }
    }

    static void editContact(Scanner sc) {
        List<String> contacts = new ArrayList<>();

        // Load contacts
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                contacts.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading contacts.");
            return;
        }

        if (contacts.isEmpty()) {
            System.out.println("No contacts to edit.");
            return;
        }

        // Display contacts
        System.out.println("\n--- Contact List ---");
        for (int i = 0; i < contacts.size(); i++) {
            String[] parts = contacts.get(i).split(",");
            if (parts.length == 3) {
                System.out.println((i + 1) + ". Name: " + parts[0] + ", Phone: " + parts[1] + ", Email: " + parts[2]);
            }
        }

        System.out.print("Enter the contact number to edit: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        contacts.set(index, name + "," + phone + "," + email);

        // Save updated contacts
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String c : contacts) {
                bw.write(c);
                bw.newLine();
            }
            System.out.println("Contact updated successfully!");
        } catch (IOException e) {
            System.out.println("Error saving edited contact.");
        }
    }

    static void deleteAllContacts() {
        try {
            new FileWriter(fileName, false).close(); // overwrite file with nothing
            System.out.println("All contacts deleted.");
        } catch (IOException e) {
            System.out.println("Error clearing file.");
        }
    }
}
