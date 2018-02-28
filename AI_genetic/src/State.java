
public class State {
	private int[] configuration;
	private int size;
	private int fitnessValue;
	private float probability;
	
	public State(int[] randomlyGeneratedState,int sz){
		configuration=new int[sz];
		this.size=sz;
		for(int i=0;i<sz;i++){
			configuration[i]=randomlyGeneratedState[i];
		}
		this.fitnessValue=calculateFitnessValue();
	}

	private int calculateFitnessValue() {
		// TODO Auto-generated method stub
		int fitnessFunction = 0;
		for(int i=0;i<size;i++){
			for(int j=i+1;j<size;j++){
				if((configuration[i]==configuration[j])||isSlope45(i, configuration[i], j, configuration[j])){
					
				}
				else{
					fitnessFunction++;
				}
			}
		}
		return fitnessFunction;
		
	}

	private boolean isSlope45(int x1,int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		if(x2-x1==0){
			return false;
		}
		else{
			if((y2-y1)==(x2-x1) || (y2-y1)==(x1-x2)){
				return true;
			}
			return false;
		}
		
	}

	public int[] getConfiguration() {
		return configuration;
	}

	public void setConfiguration(int[] configuration) {
		this.configuration = configuration;
	}

	public int getFitnessValue() {
		return fitnessValue;
	}

	public void setFitnessValue(int fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}

	public void setState(int[] configuration2) {
		// TODO Auto-generated method stub
		for(int i=0;i<size;i++){
			configuration[i]=configuration2[i];
		}
		this.fitnessValue=calculateFitnessValue();
	}
	
	
}
