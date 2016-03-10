import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Test3 {
	/**
	 * ��ϣ���
	 */
	public int arr_len = 1000;
	/**
	 * ��ʼ������
	 */
	public int[] array = new int[arr_len];

	/**
	 * ��ϣ����
	 * 
	 * @param key
	 * @return
	 */
	public int hash(int key) {
		int hashVal = key % arr_len;
		if (array[hashVal] == 0) {
			return hashVal;
		} else {
			return collision(hashVal);
		}
	}

	/**
	 * ����̽����ɢ��
	 * 
	 * @param hashVal
	 * @return
	 */
	public int collision(int hashVal) {
		int rehashVal = (hashVal + 1) % arr_len;
		if (array[rehashVal] == 0) {
			return rehashVal;
		} else {
			return collision(rehashVal);
		}
	}

	/**
	 * ��ʼ������
	 * 
	 * @param array
	 * @param filePath
	 * @throws IOException
	 */
	public void initArray(int[] array, String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String line = null;
		while ((line = br.readLine()) != null) {
			int num = Integer.parseInt(line);
			int position = hash(num);
			array[position] = num;
		}
	}

	/**
	 * ��ʾ�������е�����Ԫ��
	 * 
	 * @param array
	 */
	public void showArray(int[] array) {
		int nullCount = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				nullCount++;
				System.out.print(i + "|" + "��" + "\t");
			} else {
				System.out.print(i + "|" + array[i] + "\t");
			}
			if (i % 10 == 9) {
				System.out.println();
			}
		}
		System.out.println("��λ�ã�" + nullCount + "��");
	}

	/**
	 * ��ȡ�����õ�ָ�����ȵ�����
	 * 
	 * @param testArrayLen
	 * @return
	 */
	public int[] getTestArray(int testArrayLen) {
		if (testArrayLen != 0) {
			int[] testArray = new int[testArrayLen];
			Random rand = new Random();
			Set<Integer> testArraySet = new HashSet<Integer>();
			while (testArraySet.size() != testArrayLen) {
				int n = rand.nextInt(arr_len);
				if (array[n] != 0) {
					testArraySet.add(array[n]);
				}
			}
			Iterator<Integer> it = testArraySet.iterator();
			int idx = 0;
			while(it.hasNext()){
				testArray[idx++] = it.next();
			}
			return testArray;
		} else {
			return null;
		}

	}

	public void testHash() throws IOException {
		// Random ran = new Random();
		// for (int i = 0; i < 80; i++) {
		// int key = ran.nextInt(arr_len);
		// int hashVal = hash(key);
		// array[hashVal] = key;
		// }
		initArray(array, "D:/study/testData/num2.txt");
		showArray(array);
	}

	/**
	 * �õ�ָ��ֵ�ڹ�ϣ���е�λ��
	 * 
	 * @param val
	 * @return
	 */
	public int getPosition(int val) {
		int position = val % arr_len;
		int timer = 0;
		while (array[position] != val) {
			timer++;
			if (timer == arr_len) {
				return -1;
			}
			if (position == 999) {
				position = 0;
			} else {
				position++;
			}
		}
		return position;
	}

	/**
	 * ˳�����ָ�����ֵ�λ��
	 * 
	 * @param val
	 * @return
	 */
	public int seqFind(int val) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == val) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * �����ϣ����ʱ��
	 * @param testArray
	 * @return
	 */
	public long calTime1(int[] testArray) {
		long beginTime = System.nanoTime();
		for(int val : testArray){
			this.getPosition(val);
//			System.out.println(this.getPosition(val));
		}
		long endTime = System.nanoTime();
		return endTime - beginTime;
	}
	/**
	 * ����˳�����ʱ��
	 * @param testArray
	 * @return
	 */
	public long calTime2(int[] testArray) {
		long beginTime = System.nanoTime();
		for(int val : testArray){
			this.seqFind(val);
//			System.out.println(this.seqFind(val));
			
		}
		long endTime = System.nanoTime();
		return endTime - beginTime;
	}

	public static void main(String[] args) throws IOException {
		Test3 test = new Test3();
		test.testHash();
		long totalTime1 = 0;
		long totalTime2 = 0;
		int pCount = 0;
		int nCount = 0;
		int eCount = 0;
		for(int i = 0;i < 100;i++){			
			int[] testArray = test.getTestArray(30);// ѡȡ����Ϊ10�Ĳ���������
			test.showArray(testArray);
			long t1 = test.calTime1(testArray);
			long t2 = test.calTime2(testArray);
			if(t1 - t2 > 0){
				totalTime1 += t1 - t2;
				pCount++;
			}else if(t1 - t2 < 0){
				totalTime2 += t2 - t1;
				nCount++;
			}else{
				eCount++;
			}
		}
		System.out.println("����0 " + pCount + " ��" + " ƽ��ʱ�䣺" + (totalTime1 / pCount));
		System.out.println("С��0 " + nCount + " ��" + " ƽ��ʱ�䣺" + (totalTime2 / nCount));
		System.out.println("����0 " + eCount + " ��");
	}
}
