
public class PCB {
	private String name;
	private int[] max;
	private int[] allo;
	private int[] need;
	private PCB next;
	private boolean finish=false;
	private boolean completed=false;
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public void setNext(PCB next) {
		this.next = next;
	}
	resource rsor=new resource();
	
	public void setName(String name) {
		this.name = name;
	}
	public void setMax(int[] max) {
		this.max = max;
	}
	public void setAllo(int[] allo) {
		this.allo = allo;
	}
	public void setNeed(int[] need) {
		this.need = need;
	}
	public void setRsor(resource rsor) {
		this.rsor = rsor;
	}
	public String getName() {
		return name;
	}
	public int[] getMax() {
		return max;
	}
	public int[] getAllo() {
		return allo;
	}
	public int[] getNeed() {
		for(int i=0;i<need.length;i++){
			this.need[i]=max[i]-allo[i];
		}
		return need;
	}
	public PCB getNext() {
		return next;
	}
	public resource getRsor() {
		return rsor;
	}
	public void print(Object o){
		System.out.println(o);
	}
}


