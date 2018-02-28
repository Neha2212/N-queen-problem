import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter size n");
		int n=sc.nextInt();
		GeneticAlgorithm genetic = new GeneticAlgorithm(n);
		Thread newThread = new Thread(genetic);
		newThread.start();

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future future = executor.submit(genetic);

		try {
			System.out.println("Started..");
			future.get(10, TimeUnit.SECONDS);
			System.out.println("Finished!");
		} catch (TimeoutException e) {
			future.cancel(true);
			ArrayList<State> myStates = genetic.stateList;
			int max = 0;
			int position = 0;
			for (int i = 0; i < myStates.size(); i++) {
				State myState = myStates.get(i);
				if (myState.getFitnessValue()> max) {
					max = myState.getFitnessValue();
					position = i;
				}
			}
			System.out.println("After Timeout the best possible configuration is "
					+ Arrays.toString(myStates.get(position).getConfiguration()));

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		executor.shutdownNow();
	}

}