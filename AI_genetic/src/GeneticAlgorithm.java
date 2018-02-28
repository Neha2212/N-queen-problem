import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GeneticAlgorithm implements Runnable {

	private int sizeOfChessboard;
	Random random;
	ArrayList<State> stateList = new ArrayList<>();
	int kRandomStates;
	int[] solutionConfiguration;
	
	public GeneticAlgorithm(int sz){
		sizeOfChessboard=sz;
		random = new Random();
	}
	public void run() {
		InitialGenerateKRandomStates();
		while(!calculateFitnessFunctionAndProbability() && !selectionAndCrossOver()){
			mutation();
		}
		System.out.println(Arrays.toString(solutionConfiguration));
		State solution=new State(solutionConfiguration, sizeOfChessboard);
		System.out.println("Fitness value is "+solution.getFitnessValue());
	}
	private void mutation() {
		// TODO Auto-generated method stub
		double randomProbabilityThreashold = 25;
		for(State myState : stateList){
			int generatedProbability = random.nextInt(100);
			if(generatedProbability>randomProbabilityThreashold){
				int index = random.nextInt(sizeOfChessboard);
				int value = random.nextInt(sizeOfChessboard)+1;
				int[] configuration = myState.getConfiguration();
				configuration[index] = value;
				myState.setState(configuration);
			}
		}
	}
	private boolean selectionAndCrossOver() {
		// TODO Auto-generated method stub
		int randomThreshold = random.nextInt(1);
		ArrayList<State> selectionThreshold = new ArrayList<>();
		for(State myState : stateList){
			if(myState.getProbability()>=randomThreshold){
				selectionThreshold.add(myState);
			}
		}
		
		System.out.println("The size of the List for selection is " + selectionThreshold.size());
		if(selectionThreshold.size()==0){
			int max = 0;
			int position = 0;
			for (int i = 0; i < stateList.size(); i++) {
				State myState = stateList.get(i);
				if (myState.getFitnessValue() > max) {
					max = myState.getFitnessValue();
					position = i;
				}
			}
			solutionConfiguration = stateList.get(position).getConfiguration();
			System.out.println("The best possible configuration is ");
			return true;
		}
		
		stateList.clear();
		stateList.addAll(selectionThreshold);
		selectionThreshold.clear();
		
		for(int i=0;i<kRandomStates;i++){
			int index1 = random.nextInt(stateList.size());
			int index2 = random.nextInt(stateList.size());
			int crossOver = random.nextInt(sizeOfChessboard);
			
			int[] newConfiguration = new int[sizeOfChessboard];
			int[] parentMale = stateList.get(index1).getConfiguration();
			int[] parentFemale = stateList.get(index2).getConfiguration();
			for(int j=0;j<crossOver;j++){
				newConfiguration[j] = parentMale[j];
			}
			for(int j=crossOver;j<sizeOfChessboard;j++){
				newConfiguration[j] = parentFemale[j];
			}
			State newState = new State(newConfiguration,sizeOfChessboard);
			if(newState.getFitnessValue()==(this.sizeOfChessboard*(this.sizeOfChessboard-1))/2){
				solutionConfiguration = newState.getConfiguration();
				return true;
			}
			selectionThreshold.add(newState);
		}
		
		stateList.clear();
		stateList.addAll(selectionThreshold);
		return false;
		
	}
	private boolean calculateFitnessFunctionAndProbability() {
		// TODO Auto-generated method stub
		int total=0;
		for(State s : stateList){
			total=total+s.getFitnessValue();
			if(s.getFitnessValue()==(this.sizeOfChessboard*(this.sizeOfChessboard-1))/2){
				solutionConfiguration = s.getConfiguration();
				return true;
			}
		}
		for(State myState : stateList){
			int percent = (myState.getFitnessValue()/total);
			myState.setProbability(percent);
		}
		System.out.println("Successfully evaluated fitness and Probability " + total  + " and size is " + stateList.size());
		return false;
	}
	private void InitialGenerateKRandomStates() {
		// TODO Auto-generated method stub
		kRandomStates = random.nextInt(10)+100;
		for (int i = 0; i < kRandomStates; i++) {
			
			int[] configuration = new int[sizeOfChessboard];
			for (int j = 0; j < sizeOfChessboard; j++) {
				configuration[j] = random.nextInt(sizeOfChessboard) + 1;
			}
			//myState.setState(configuration);
			State myState = new State(configuration,sizeOfChessboard);
			stateList.add(myState);
		}
		System.out.println("Successfully Generated Random States " + kRandomStates);
	
	}
	

}