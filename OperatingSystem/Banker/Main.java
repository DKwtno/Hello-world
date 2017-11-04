
import java.util.Scanner;

public class Main {
	static PCB ready=new PCB();
	static PCB fst;
	static PCB second;
	static boolean end=false;
	static int completed_length=0;
	static int num;
	static int task;
	static int[] rqst;
	static int[][]work;
	static PCB[] pcb;
	static PCB[] safe;
	static String[] completed;
	public static int[] ava;
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		input(sc);
		while (!end) {
			int count=0;
			for(int i=0;i<task;i++){
				if(pcb[i].isCompleted())
					count++;
			}
			if(count==task){
				print("已完成所有任务");
				display();
				break;
			}
			menu(sc);
		}
		print("完美结束。");
		sc.close();
	}
	static void input(Scanner sc){
		resource rs=new resource();
		print("请输入资源个数：");
		num=sc.nextInt();
		rs.setNum(num);
		print("请输入进程个数：");
		task=sc.nextInt();
		safe=new PCB[task];
		completed=new String[task];
		pcb=new PCB[task];
		rqst=new int[num];
		work=new int[task+1][num];
		print("请输入进程名和要求资源数（maximum，allocated）：");
		for(int i=0;i<task;i++){
			print("NO."+i+":");
			fst=new PCB();
			int[] max=new int[num];
			int[] allo=new int[num];
			fst.setName(sc.next());
			for(int j=0;j<num;j++)
				max[j]=sc.nextInt();
			fst.setMax(max);
			for(int j=0;j<num;j++)
				allo[j]=sc.nextInt();
			fst.setAllo(allo);
			int[] need=new int[num];
			for(int j=0;j<num;j++){
				need[j]=max[j]-allo[j];
			}
			fst.setNeed(need);
			pcb[i]=fst;
		}
		print("请输入各个资源的数量（available）：");
		ava=new int[num];
		for(int i=0;i<num;i++){
			ava[i]=sc.nextInt();
		}
		rs.setRsor(ava);//对资源的初始化
	}
	static void menu(Scanner sc){
		int flag;
		print("1、系统安全性检测");
		print("2、请求安全性检测");
		print("3、退出");
		if(!sc.hasNextInt()){
			sc.next();
			print("输入错误，请重新输入！");
			return;
		}
		flag=sc.nextInt();
		switch(flag){
		case 1: {
			security();
			display();
			System.out.print("可用资源available为：");
			for(int o=0;o<num;o++){
				System.out.print(ava[o]+" ");
			}
			print("");
			break;
			}
		case 2: {
			print("请输入进程名：");
			String name=sc.next();
			int i=0;
			while(!pcb[i].getName().equals(name)){
				i++;
				if(i>=task){
					print("输入名错误！");
					print("请重新输入进程名：");
					name=sc.next();
					i=0;
				}
			}
			print("请输入request：");
			for(int k=0;k<num;k++){
				rqst[k]=sc.nextInt();
			}
			request(pcb[i].getName(), rqst,i);
			System.out.print("可用资源available为：");
			for(int o=0;o<num;o++){
				System.out.print(ava[o]+" ");
			}
			print("");
			System.out.print("已完成的进程为：");
			for(int o=0;o<completed_length;o++){
				System.out.print(completed[o]+" ");
			}
			print("");
			}
		break;
		case 3:{end=true;
		break;}
		default: break;
		}
	}
	static void display(){
		if(completed_length==task)
			return;
		print("Name \tWork \tNeed \tAllocation\tWork+Allocation\tFinish");
		for(int i=0;i<task-completed_length;i++){
			System.out.print(safe[i].getName()+"\t");
			for(int j=0;j<num;j++)
				System.out.print(work[i][j]+" ");
			System.out.print("\t");
			for(int j=0;j<num;j++)
				System.out.print(safe[i].getNeed()[j]+" ");
			System.out.print("\t");
			for(int j=0;j<num;j++)
				System.out.print(safe[i].getAllo()[j]+" ");
			System.out.print("\t\t");
			for(int j=0;j<num;j++)
				System.out.print((safe[i].getAllo()[j]+work[i][j])+" ");
			System.out.print("\t\t");
			System.out.print("T");
			print("");
		}
	
	}
	static boolean security(){
		finishInitial();
		boolean flag=false;
		boolean coin=false;
		for (int i = 0; i < task; i++) {
			for (int p = 0; p < num; p++) {
				work[i][p] = ava[p];
			} 
		}
		int i=0;
		boolean br=false;
		for(i=0;i<task-completed_length;){
			for(int j=0;j<task;j++){
				if(pcb[j].isCompleted()||pcb[j].isFinish()){
					continue;
				}
				for(int k=0;k<num;k++){
					if(pcb[j].getNeed()[k]>work[i][k])//needi要求<=work
					{
						flag=true;
						break;
					}
				}
				if (!flag) {
					pcb[j].setFinish(true);
					safe[i] = pcb[j];
					for (int k = 0; k < num; k++) {
						work[i+1][k] = work[i][k]+pcb[j].getAllo()[k];
					} 
					i++;
					br=true;
				}
				flag=false;
			}
			if(!br)
				i++;
		}
		for(int k=0;k<task;k++){
			if(pcb[k].isCompleted())
				continue;
			if(!pcb[k].isFinish()){
				coin=true;
			}
		}
		if(coin){
			coin=false;
			print("系统不安全！");
			return false;
		}
		else{
			print("系统安全！");
			return true;
		}
	}
	static void finishInitial(){
		for(int i=0;i<task;i++){
			pcb[i].setFinish(false);
		}
	}
	static void request(String name,int[] r,int n){
		for(int i=0;i<num;i++){
			if(r[i]>pcb[n].getNeed()[i]){
				print("资源请求超过"+name+"的需求！");
				print(name+"无法获得资源!");
				return;
			}
			
			if(r[i]>ava[i]){
				print("资源请求超过available!");
				print(name+"未分配");
				return;
			}
		}
		
		//试探分配
		int[] temp_setallo=new int[num];
		int[] temp_setneed=new int[num];
		for(int i=0;i<num;i++){
			ava[i]-=r[i];
			temp_setallo[i]=pcb[n].getAllo()[i]+r[i];
			temp_setneed[i]-=r[i];
		}
		pcb[n].setAllo(temp_setallo);
		pcb[n].setNeed(temp_setneed);
		
		if(security()){
			display();
			print(name+"可以获得资源");
			finishInitial();
			pcb[n].setCompleted(true);
			for(int i=0;i<num;i++){
				if(pcb[n].getNeed()[i]!=0)
					pcb[n].setCompleted(false);
			}
			if(pcb[n].isCompleted()){
				int[] temp_allo=new int[num];
				for (int i = 0; i < num; i++) {
					temp_allo[i] = 0;
				}
				for(int i=0;i<num;i++){
					ava[i]+=pcb[n].getAllo()[i];
				}
				pcb[n].setAllo(temp_allo);
				pcb[n].setNeed(temp_allo);
				print(pcb[n].getName()+"进程已完成，资源已回收！");
				completed[completed_length]=pcb[n].getName();
				completed_length++;
				if(completed_length<task)
					safe=new PCB[task-completed_length];
				else{
					end=true;
					return;
				}
			}
		}
		else{
			for(int i=0;i<num;i++){
				ava[i]+=r[i];
				pcb[n].getAllo()[i]-=r[i];
				pcb[n].getNeed()[i]+=r[i];
			}
			print(name+"不能及时获得资源");
			finishInitial();
		}
	}
	static void print(Object t){
		System.out.println(t);
	}
	
}
