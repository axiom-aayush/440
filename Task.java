import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Task implements Callable {

	public ConcurrentLinkedQueue<WeatherData> minMaxOfFile = new ConcurrentLinkedQueue<WeatherData>();
	int minMax;
	File file;

	public Task(File file, int minMax) {
		this.file = file;
		this.minMax = minMax;

	}

	public Task call() throws Exception {

		Scanner scanner = new Scanner(file);

		String readLine = "";

		int j = 0;

		while (scanner.hasNextLine() && !(j == 10)) {
			readLine = scanner.nextLine();
			j++;
			if (j >= 3) {
				processLine(readLine);
			}
		}

		return this;

	}

	public ConcurrentLinkedQueue<WeatherData> getMinMaxOfFile() {
		return minMaxOfFile;
	}

	public void processLine(String thisLine) {
		String id = thisLine.substring(0, 11);
		int year = Integer.valueOf(thisLine.substring(11, 15).trim());
		int month = Integer.valueOf(thisLine.substring(15, 17).trim());
		String element = thisLine.substring(17, 21);
		int days = (thisLine.length() - 21) / 8; // Calculate the number of days in the line

		if ((minMax == 1) && (element.equals("TMAX")) || (minMax == 0) && (element.equals("TMIN"))) {
			for (int i = 0; i < days; i++) { // Process each day in the line.
				WeatherData wd = new WeatherData();
				wd.day = i + 1;
				int value = Integer.valueOf(thisLine.substring(21 + 8 * i, 26 + 8 * i).trim());
				double v2 = value / 10.0;
				if (v2 > 100 || v2 < -100) {
					break;
				}
				String qflag = thisLine.substring(27 + 8 * i, 28 + 8 * i);
				wd.id = id;
				wd.year = year;
				wd.month = month;
				wd.element = element;
				wd.value = v2;
				wd.qflag = qflag;
				// adding weatherData in the Concurrent queue
				minMaxOfFile.add(wd);

			}
		}
	}
}
