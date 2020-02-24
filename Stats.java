import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Stats {

	double tempMax_1 = 0;
	double tempMin_1 = 999;

	static ConcurrentLinkedQueue<WeatherData> maxList = new ConcurrentLinkedQueue<WeatherData>();

	static ConcurrentLinkedQueue<WeatherData> minList = new ConcurrentLinkedQueue<WeatherData>();

	ConcurrentLinkedQueue<WeatherData> temp = new ConcurrentLinkedQueue<WeatherData>();

	public ConcurrentLinkedQueue<WeatherData> findMax(ConcurrentLinkedQueue<Future<Task>> queue, int startYear,
			int endYear, int startMonth, int endMonth) {
		while (true) {
			Future<Task> item = queue.poll();
			if (item == null) {
				break;
			}

			try {

				temp = item.get().getMinMaxOfFile();
				while (true) {
					WeatherData itemWeather = temp.poll();
					if (itemWeather == null) {
						break;
					}
					if (itemWeather.value > tempMax_1) {
						tempMax_1 = itemWeather.value;
						maxList.add(itemWeather);
					}
				}

				while (maxList.size() > 5) {
					maxList.poll();
					if (maxList.poll() == null) {
						break;
					}
				}

				printWeatherData(maxList);

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		return maxList;

	}

	public ConcurrentLinkedQueue<WeatherData> findMin(ConcurrentLinkedQueue<Future<Task>> queue, int startYear,
			int endYear, int startMonth, int endMonth) {
		while (true) {
			Future<Task> item = queue.poll();
			if (item == null) {
				break;
			}

			try {

				temp = item.get().getMinMaxOfFile();
				while (true) {
					WeatherData itemWeather = temp.poll();
					if (itemWeather == null) {
						break;
					}
					if (itemWeather.value < tempMin_1) {
						tempMin_1 = itemWeather.value;
						minList.add(itemWeather);
					}
				}

				while (minList.size() > 5) {
					minList.poll();
					if (minList.poll() == null) {
						break;
					}
				}

				printWeatherData(minList);

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		return maxList;

	}

	public void printWeatherData(ConcurrentLinkedQueue<WeatherData> w) {
		while (true) {
			WeatherData itemWeatherTop5 = w.poll();
			if (itemWeatherTop5 == null) {
				break;
			}
			System.out.println("id=" + itemWeatherTop5.id + " " + "year=" + itemWeatherTop5.year + " " + "month= "
					+ itemWeatherTop5.month + " " + "day=" + itemWeatherTop5.day + " " + "element="
					+ itemWeatherTop5.element + " " + "value=" + itemWeatherTop5.value + " " + "qflag="
					+ itemWeatherTop5.qflag + " ");
		}
	}

	// public void

}
