package JAVA_Test;
/**
 * 确定N个数中第k个最大者（成功）
 * @author weizhiwei
 *
 */
public class Ex_1 {
	private int[] N;
	private int k;
	public  Ex_1(int[] N,int k){
		this.N=N;
		this.k=k;
	}
	int selectK(){
		int[] first=N;
		int[] second=new int[k];
		for(int i=0;i<k;i++)
			second[i]=0;
		/*希望以递减的顺序从first中取前K个放入second，再继续读入，读完整个first后取second的第K个*/
		for(int i=0;i<first.length;i++){
			int t=i>(k-1)?(k-1):i;
			int temp=-1;
			for(int j=t;j>=0&&(second[j]<=first[i]);j--){
				temp=j;
			}
			if(temp>=0){
				for(int i1=k-1;i1>temp;i1--){
					second[i1]=second[i1-1];
				}
				second[temp]=first[i];
				sort(second);
			}
		}
		return second[k-1];
	}
	private void sort(int[] s){
		for(int i=0;i<s.length-1;i++){
			for(int j=0;j<s.length-i-1;j++){
				if(s[j]<s[j+1]){
					int t=s[j];
					s[j]=s[j+1];
					s[j+1]=t;
					break;
				}
			}
		}
	}
}
