import java.util.*;

// Course Class
class Course {
    String code;
    String name;
    int credits;
    int seats;
    int totalSeats;
    Queue<String> waitingList;

    Course(String code, String name, int credits, int seats) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.seats = seats;
        this.totalSeats = seats;
        waitingList = new LinkedList<>();
    }
}

public class StudentCourseRegistrationwithPriority {

    // HashMap for course storage
    static HashMap<String, Course> courseMap = new HashMap<>();

    // LinkedList for student schedule
    static LinkedList<Course> schedule = new LinkedList<>();

    // Stack for undo registration
    static Stack<Course> undoStack = new Stack<>();

    static int MAX_CREDITS = 20;
    static int currentCredits = 0;

    public static void main(String[] args) {

        // Add Courses
        courseMap.put("CS102", new Course("CS102", "Intro to Programming", 4, 40));
        courseMap.put("MA102", new Course("MA102", "Calculus I", 4, 40));
        courseMap.put("DE102", new Course("DE102", "Design Thinking", 4, 40));
        courseMap.put("AI102", new Course("DE102", "Artificial Intelligence", 4, 40));


        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== COURSE REGISTRATION =====");
            System.out.println("1. View Courses");
            System.out.println("2. Register Course");
            System.out.println("3. Undo Registration");
            System.out.println("4. Search Course");
            System.out.println("5. View Schedule");
            System.out.println("6. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewCourses();
                    break;

                case 2:
                    System.out.print("Enter Course Code: ");
                    String code = sc.nextLine().toUpperCase();
                    registerCourse(code);
                    break;

                case 3:
                    undoRegistration();
                    break;

                case 4:
                    System.out.print("Enter course code to search: ");
                    String search = sc.nextLine().toUpperCase();
                    searchCourse(search);
                    break;

                case 5:
                    viewSchedule();
                    break;

                case 6:
                    System.out.println("Exiting Portal...");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // View courses
    static void viewCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course c : courseMap.values()) {
            System.out.println(c.code + " - " + c.name +
                    " | Seats: " + c.seats + "/" + c.totalSeats);
        }
    }

    // Register course
    static void registerCourse(String code) {

        Course c = courseMap.get(code); // O(1)

        if (c == null) {
            System.out.println("Course not found!");
            return;
        }

        if (currentCredits + c.credits > MAX_CREDITS) {
            System.out.println("Credit limit exceeded!");
            return;
        }

        if (c.seats > 0) {

            c.seats--;
            schedule.add(c); // O(1)
            undoStack.push(c); // O(1)

            currentCredits += c.credits;

            System.out.println("Registered for " + c.code);

        } else {

            c.waitingList.add("Student"); // O(1)
            System.out.println("Course Full! Added to waiting list.");
        }
    }

    // Undo registration
    static void undoRegistration() {

        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Course c = undoStack.pop(); // O(1)

        schedule.remove(c);
        c.seats++;

        currentCredits -= c.credits;

        System.out.println("Undo successful for " + c.code);
    }

    // View schedule
    static void viewSchedule() {

        if (schedule.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }

        System.out.println("\nYour Schedule:");
        for (Course c : schedule) {
            System.out.println(c.code + " - " + c.name);
        }

        System.out.println("Credits: " + currentCredits + "/" + MAX_CREDITS);
    }

    // Binary search for course
    static void searchCourse(String code) {

        List<String> courseCodes = new ArrayList<>(courseMap.keySet());
        Collections.sort(courseCodes);

        int left = 0;
        int right = courseCodes.size() - 1;

        while (left <= right) {

            int mid = (left + right) / 2;
            int compare = courseCodes.get(mid).compareTo(code);

            if (compare == 0) {
                Course c = courseMap.get(courseCodes.get(mid));
                System.out.println("Found: " + c.code + " - " + c.name);
                return;
            } else if (compare < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }

        System.out.println("Course not found.");
    }
}