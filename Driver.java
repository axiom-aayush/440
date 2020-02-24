
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Driver {

	// Maximum number of threads in thread pool
	static final int MAX_T = 6;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ExecutorService executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

		ConcurrentLinkedQueue<Future<Task>> fileListFuturesTask = new ConcurrentLinkedQueue<Future<Task>>();

//		Scanner reader = new Scanner(System.in); // Reading from System.in
//		System.out.println("Weather Program running ");
//		System.out.println("Enter Start year : ");
//		int startYear = reader.nextInt();
//		// Scans the next token of the input as an int.
//		System.out.println("Enter Ending year : ");
//		int endYear = reader.nextInt();
//
//		System.out.println("Enter Start Month : ");
//		int startMonth = reader.nextInt(); // Scans the next token of the input as an int.
//		System.out.println("Enter Ending Month : ");
////		int endMonth = reader.nextInt();
//
//		int minMax = 2;
//
//		while (!(minMax == 1) && !(minMax == 0)) {
//
//			System.out.println("Would you like to print maximum or minimum ? (1 for maximum and 0 for minimum : ");
//			minMax = reader.nextInt();
//
//		}
//
//		reader.close();

		int minMax = 0;
		int startYear, endYear, startMonth, endMonth;
		startYear = endYear = startMonth = endMonth = 0;
		// getting the current directory
		String cwd = System.getProperty("user.dir");
		String c = cwd + "/weather_dataTest";
		File folder = new File(c);

		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				Callable<Task> callable = new Task(listOfFiles[i], minMax);

				Future<Task> future = executor.submit(callable);

				fileListFuturesTask.add(future);

			}

		}

		Stats stat = new Stats();

		if (minMax == 1) {
			ConcurrentLinkedQueue<WeatherData> max = stat.findMax(fileListFuturesTask, startYear, endYear, startMonth,
					endMonth);

		} else {
			stat.findMin(fileListFuturesTask, startYear, endYear, startMonth, endMonth);
		}

	}

}
