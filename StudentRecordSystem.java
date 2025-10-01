import java.io.*;
import java.util.Scanner;

class Student {
    int rollNo;
    String name;
    String course;
    Student next;

    public Student(int rollNo, String name, String course) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        this.next = null;
    }

    @Override
    public String toString() {
        return rollNo + "," + name + "," + course;
    }
}

class StudentLinkedList {
    private Student head;

    public void addStudent(int rollNo, String name, String course) {
        Student newStudent = new Student(rollNo, name, course);
        if (head == null) {
            head = newStudent;
        } else {
            Student temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newStudent;
        }
        System.out.println(" Student added successfully!");
    }

    public void displayStudents() {
        if (head == null) {
            System.out.println(" No student records found!");
            return;
        }
        Student temp = head;
        System.out.println("\n--- Student Records ---");
        while (temp != null) {
            System.out.println("Roll No: " + temp.rollNo + ", Name: " + temp.name + ", Course: " + temp.course);
            temp = temp.next;
        }
    }

    public void searchStudent(int rollNo) {
        Student temp = head;
        while (temp != null) {
            if (temp.rollNo == rollNo) {
                System.out.println(" Found: Roll No: " + temp.rollNo + ", Name: " + temp.name + ", Course: " + temp.course);
                return;
            }
            temp = temp.next;
        }
        System.out.println(" Student not found.");
    }

    public void updateStudent(int rollNo, String newName, String newCourse) {
        Student temp = head;
        while (temp != null) {
            if (temp.rollNo == rollNo) {
                temp.name = newName;
                temp.course = newCourse;
                System.out.println(" Student updated successfully!");
                return;
            }
            temp = temp.next;
        }
        System.out.println(" Student not found.");
    }

    public void deleteStudent(int rollNo) {
        if (head == null) {
            System.out.println(" No records found!");
            return;
        }
        if (head.rollNo == rollNo) {
            head = head.next;
            System.out.println(" Student deleted.");
            return;
        }
        Student temp = head;
        while (temp.next != null) {
            if (temp.next.rollNo == rollNo) {
                temp.next = temp.next.next;
                System.out.println(" Student deleted.");
                return;
            }
            temp = temp.next;
        }
        System.out.println(" Student not found.");
    }

    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Student temp = head;
            while (temp != null) {
                bw.write(temp.toString());
                bw.newLine();
                temp = temp.next;
            }
            System.out.println(" Records saved to " + filename);
        } catch (IOException e) {
            System.out.println(" Error saving records: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int roll = Integer.parseInt(parts[0]);
                String name = parts[1];
                String course = parts[2];
                addStudent(roll, name, course);
            }
            System.out.println(" Records loaded from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println(" No existing file found. Starting fresh.");
        } catch (IOException e) {
            System.out.println(" Error loading records: " + e.getMessage());
        }
    }
}

public class StudentRecordSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentLinkedList list = new StudentLinkedList();
        String filename = "students.txt";

        list.loadFromFile(filename);

        int choice;
        do {
            System.out.println("\n===== Student Record System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Save Records");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();
                    list.addStudent(roll, name, course);
                    break;

                case 2:
                    list.displayStudents();
                    break;

                case 3:
                    System.out.print("Enter Roll No: ");
                    int searchRoll = sc.nextInt();
                    list.searchStudent(searchRoll);
                    break;

                case 4:
                    System.out.print("Enter Roll No: ");
                    int updateRoll = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter New Course: ");
                    String newCourse = sc.nextLine();
                    list.updateStudent(updateRoll, newName, newCourse);
                    break;

                case 5:
                    System.out.print("Enter Roll No: ");
                    int deleteRoll = sc.nextInt();
                    list.deleteStudent(deleteRoll);
                    break;

                case 6:
                    list.saveToFile(filename);
                    break;

                case 7:
                    list.saveToFile(filename);
                    System.out.println(" Exiting... Records saved.");
                    break;

                default:
                    System.out.println(" Invalid choice.");
            }
        } while (choice != 7);

        sc.close();
    }
}
