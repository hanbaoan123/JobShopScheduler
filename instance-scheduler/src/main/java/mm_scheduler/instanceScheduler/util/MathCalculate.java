package mm_scheduler.instanceScheduler.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class MathCalculate {

	public static void main(String[] args) {

		double[] mm = { 0.1, 0.9, 0.3, 0.9, 0.1, 0.4, -0.89, 4.01 };
		int[] ss = orderLocation(mm);
		for (int i = 0; i < ss.length; i++) {
			System.out.print(ss[i]);
		}

		double[] a1 = { 0.1, 0.9 };
		double[] a2 = { 0.2, 0.8 };
		double[] a3 = { 0.3, 0.7 };
		double[] a4 = { 0.4, 0.6 };
		double[] a5 = { 0.5, 0.5 };
		double[] a6 = { 0.6, 0.4 };
		double[] a7 = { 0.7, 0.3 };
		double[] a8 = { 0.8, 0.2 };
		double[] a9 = { 0.9, 0.1 };
		double[] a10 = { 1.0, 0.0 };

		double[] a11 = { 0.15, 0.85 };
		List<double[]> list1 = new ArrayList<double[]>();
		list1.add(a6);
		list1.add(a2);
		list1.add(a3);
		list1.add(a11);

		List<double[]> list2 = new ArrayList<double[]>();
		list2.add(a1);

		list2.add(a5);

		list2.add(a7);
		list2.add(a8);
		list2.add(a9);
		list2.add(a10);
		double A_B = CValue(list1, list2);
		double B_A = CValue(list2, list1);
		System.out.print("\n" + "A_B:" + A_B + "B_A:" + B_A + "\n");

		List<List<double[]>> pppList = new ArrayList<List<double[]>>();
		pppList.add(list1);
		pppList.add(list2);
		// double[] d = hypervolumeForParetoListSameSample(pppList);

		// double[] d = NetFrontContributionForParetoList(pppList);
		double[] d = InvertedGenerationalDistance(pppList);
		String string = "";
		for (int i = 0; i < d.length; i++) {
			string = string + d[i] + ",";
		}
		System.out.print(string);
		// List<double[]> dList = removeSame(list);
		// System.out.print("qian");
		// for (double[] a : list) {
		// String string = "";
		// for (int i = 0; i < a.length; i++) {
		// string = string + a[i] + ",";
		// }
		// System.out.print(string);
		// System.out.print("\n");
		// }
		//
		// double r[] = netFrontContribution(list1, list2);
		//
		// System.out.print("qian");
		// for (double[] a : list) {
		// String string = "";
		// for (int i = 0; i < a.length; i++) {
		// string = string + a[i] + ",";
		// }
		// System.out.print(string);
		// System.out.print("\n");
		// }
		// System.out.print("hou");
		// list = dominateFliter(list);
		// for (double[] a : list) {
		// String string = "";
		// for (int i = 0; i < a.length; i++) {
		// string = string + a[i] + ",";
		// }
		// System.out.print(string);
		// System.out.print("\n");
		// }
		//
		// list=removeSame(list);
		// System.out.print("remove");
		// for (double[] a : list) {
		// String string="";
		// for (int i = 0; i < a.length; i++) {
		// string=string+a[i]+",";
		// }
		// System.out.print(string);
		// System.out.print("\n");
		// }
		// double[]newArr={0.6,0.0,0.2};
		// double[]oldArr={0.7,0.0,0.2};
		// int m=dominateCompare(newArr, oldArr);
		//
		// List<Double> newList=new ArrayList<Double>();
		// List<Double> oldList=new ArrayList<Double>();
		// for (int i = 0; i < newArr.length; i++) {
		// newList.add(newArr[i]);
		// }
		// for (int i = 0; i < oldArr.length; i++) {
		// oldList.add(oldArr[i]);
		// }
		// int dominate=dominateCompare(newList,oldList);
		// System.out.print(dominate);
		// int size = 20;
		// int neighbour = 50;
		// List<int[]> combineList = new ArrayList<int[]>();
		// Random random = new Random();
		// for (int i = 0; i < neighbour; i++) {
		// int num = random.nextInt(size);
		// int[] totalRandom = getRandom(size);
		// int[] selectPre = new int[num];
		// for (int j = 0; j < num; j++) {
		// selectPre[j] = totalRandom[j];
		// }
		// combineList.add(selectPre);
		// }
		//
		// for (int i = 0; i < combineList.size(); i++) {
		// for (int j = 0; j < combineList.get(i).length; j++) {
		// System.out.print(combineList.get(i)[j]);
		// System.out.print(",");
		// }
		//
		// System.out.print("////");
		// }
		// double[][] mm = new double[9][6];
		// for (int i = 0; i < mm.length; i++) {
		// for (int j = 0; j < mm[i].length; j++) {
		// mm[i][j] = (i + 1) * 2 + j * 3;
		// }
		// }
		//
		// for (int i = 0; i < mm.length; i++) {
		// for (int j = 0; j < mm[i].length; j++) {
		// System.out.print("(" + mm[i][j] + ")");
		// }
		// System.out.print("\n");
		// }
		// mm = uniformByRow(mm);
		// for (int i = 0; i < mm.length; i++) {
		// for (int j = 0; j < mm[i].length; j++) {
		// System.out.print("(" + mm[i][j] + ")");
		// }
		// System.out.print("\n");
		// }
		//
		// double[]w=new double[6];
		// for (int i = 0; i < w.length; i++) {
		// w[i]=1.0/w.length;
		// }
		// System.out.print("权重数量为"+w.length);
		// System.out.print("权重为");
		// for (int i = 0; i < w.length; i++) {
		// System.out.print("("+w[i]+")");
		// }
		// double[]util=new double[9];
		//
		// util=weightMultiply(mm,w);
		// System.out.print("最终为");
		// for (int i = 0; i < util.length; i++) {
		// System.out.print("("+util[i]+")");
		// }

	}

	public static double[][] uniformByRow(double[][] m) {
		double[] max = new double[m[0].length];
		double[] min = new double[m[0].length];
		for (int i = 0; i < m[0].length; i++) {
			double maxValue = m[0][i];
			double minValue = m[0][i];
			for (int j = 1; j < m.length; j++) {
				if (m[j][i] > maxValue) {
					maxValue = m[j][i];
				}
				if (m[j][i] < minValue) {
					minValue = m[j][i];
				}
			}
			max[i] = maxValue;
			min[i] = minValue;
		}

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (max[j] - min[j] == 0) {
					m[i][j] = 1;
				} else {
					m[i][j] = 1 - (m[i][j] - min[j]) / (max[j] - min[j]);
				}
			}

		}

		return m;

	}

	public static double[] weightMultiply(double[][] matrix, double[] weight) {
		double[] util = new double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			double c = 0;
			for (int j = 0; j < matrix[i].length; j++) {
				c = matrix[i][j] * weight[j] + c;
			}
			util[i] = c;
		}

		return util;
	}

	/**
	 * 
	 * @author: hba
	 * @description:
	 * @param total
	 * @return
	 * @date: 2019年12月24日
	 *
	 */
	public static int[] getRandom(int total) {
		int[] array = new int[total];
		for (int i = 0; i < total; i++) {
			array[i] = i;
		}
		Random random = new Random();
		int temp2;
		int end = total;
		for (int i = 0; i < total; i++) {
			int temp = random.nextInt(end);
			temp2 = array[temp];
			array[temp] = array[end - 1];
			array[end - 1] = temp2;
			end--;
		}

		return array;
	}

	public static List<int[]> getRandomCombine(int size, int neighbour) {

		List<int[]> combineList = new ArrayList<int[]>();
		Random random = new Random();
		for (int i = 0; i < neighbour; i++) {
			int num = random.nextInt(size);
			if (size > 5) {
				num = random.nextInt(5);
			} else {
				num = random.nextInt(size);
			}

			if (num == 0) {
				num = 1;
			}
			int[] totalRandom = getRandom(size);
			int[] selectPre = new int[num];
			for (int j = 0; j < num; j++) {
				selectPre[j] = totalRandom[j];
			}
			combineList.add(selectPre);
		}
		return combineList;
	}

	/**
	 * new>old -1 new<old 1 new=old 2 new indiff old 0
	 * 这里的大于号代表是数值上的大于，并不是支配关系，因为支配关系与优化的方向有关
	 * 
	 * @param newList
	 * @param oldList
	 * @return
	 */
	public static int dominateCompare(double[] newList, double[] oldList) {
		int dominate = 0;
		int greatNum = 0;
		int smallNum = 0;
		int equalNum = 0;
		double compare1 = 0.0;
		double compare2 = 0.0;
		for (int i = 0; i < newList.length; i++) {
			compare1 = newList[i];
			compare2 = oldList[i];
			if (compare1 >= compare2) {
				greatNum = greatNum + 1;
			}
			if (compare1 <= compare2) {
				smallNum = smallNum + 1;
			}
			if (compare1 == compare2) {
				equalNum = equalNum + 1;
			}
		}
		if (greatNum == newList.length) {
			dominate = -1;
		}
		if (smallNum == newList.length) {
			dominate = 1;
		}
		if (equalNum == newList.length) {
			dominate = 2;
		}
		return dominate;

	}

	/**
	 * new>old -1 new<old 1 new=old 2 new indiff old 0
	 * 
	 * @param newList
	 * @param oldList
	 * @return
	 */
	public static int dominateCompare(List<Double> newList, List<Double> oldList) {
		int dominate = 0;
		int greatNum = 0;
		int smallNum = 0;
		int equalNum = 0;
		double compare1 = 0.0;
		double compare2 = 0.0;
		for (int i = 0; i < newList.size(); i++) {
			compare1 = newList.get(i);
			compare2 = oldList.get(i);
			if (compare1 >= compare2) {
				greatNum = greatNum + 1;
			}
			if (compare1 <= compare2) {
				smallNum = smallNum + 1;
			}
			if (compare1 == compare2) {
				equalNum = equalNum + 1;
			}
		}
		if (greatNum == newList.size()) {
			dominate = -1;
		}
		if (smallNum == newList.size()) {
			dominate = 1;
		}
		if (equalNum == newList.size()) {
			dominate = 2;
		}
		return dominate;

	}

	public static double[] netFrontContribution(List<double[]> list1, List<double[]> list2) {
		List<double[]> allList = new ArrayList<double[]>();
		list1 = removeSame(list1);
		list2 = removeSame(list2);
		allList.addAll(list1);
		allList.addAll(list2);
		allList = dominateFliter(allList);
		list1 = dominateFliter(list1);
		list2 = dominateFliter(list2);
		allList = removeSame(allList);
		double contriNum1 = 0;
		for (double[] a : allList) {
			for (double[] a1 : list1) {
				if (dominateCompare(a, a1) == 2) {
					contriNum1 = contriNum1 + 1;
				}
			}
		}

		double contriNum2 = 0;
		for (double[] a : allList) {
			for (double[] a2 : list2) {
				if (dominateCompare(a, a2) == 2) {
					contriNum2 = contriNum2 + 1;
				}
			}
		}
		contriNum1 = contriNum1 / allList.size();
		contriNum2 = contriNum2 / allList.size();

		// System.out.print("contriNum1:" + contriNum1);
		// System.out.print("contriNum2:" + contriNum2);
		double[] result = new double[2];
		result[0] = contriNum1;
		result[1] = contriNum2;
		return result;
	}

	public static List<double[]> dominateFliter(List<double[]> list) {
		Map<Integer, List<double[]>> nonDominatedSortMap = new HashMap<Integer, List<double[]>>();
		// 用于记录每个解多支配的其他解集合
		Map<double[], List<double[]>> dominateMap = new HashMap<double[], List<double[]>>();
		// 用于记录每个解被支配的次数
		Map<double[], Integer> soluDominateNumMap = new HashMap<double[], Integer>();
		// 处于第一层级前沿的解集
		List<double[]> F1 = new ArrayList<double[]>();
		// 处于第i层级前沿的解集
		List<double[]> Fi = F1;
		for (int i = 0; i < list.size(); i++) {
			double[] p = list.get(i);
			List<double[]> Sp = new ArrayList<double[]>();
			// p被支配的数量
			int np = 0;
			for (int j = 0; j < list.size(); j++) {
				double[] q = list.get(j);
				if (!p.equals(q)) {
					// 如果p支配了q
					if (dominateCompare(p, q) == 1) {
						Sp.add(q);
					}
					// 如果q支配了p，也就是说p被支配
					if (dominateCompare(p, q) == -1) {
						np = np + 1;
					}
				}
			}
			dominateMap.put(p, Sp);
			soluDominateNumMap.put(p, np);
			if (np == 0) {
				F1.add(p);
			}
		}
		nonDominatedSortMap.put(1, F1);
		return F1;
	}

	public static List<double[]> removeSame(List<double[]> list) {
		List<double[]> newList = new ArrayList<double[]>();
		for (int i = 0; i < list.size(); i++) {
			double[] a = list.get(i);
			boolean add = true;
			for (double[] b : newList) {
				if (dominateCompare(a, b) == 2) {
					add = false;
					break;
				}
			}
			if (add) {
				newList.add(a);
				add = true;
			}

		}
		return newList;
	}

	public static double[] hypervolume(List<double[]> list1, List<double[]> list2) {

		List<double[]> allList = new ArrayList<double[]>();
		list1 = removeSame(list1);
		list2 = removeSame(list2);
		allList.addAll(list1);
		allList.addAll(list2);
		allList = removeSame(allList);
		allList = dominateFliter(allList);
		list1 = dominateFliter(list1);
		list2 = dominateFliter(list2);

		Map<Integer, Double> minValueMap = new HashMap<Integer, Double>();
		Map<Integer, Double> maxValueMap = new HashMap<Integer, Double>();
		int obSize = allList.get(0).length;
		for (int i = 0; i < obSize; i++) {
			double min = allList.get(0)[0];
			double max = allList.get(0)[0];
			for (double[] dd : allList) {

				min = Math.min(min, dd[i]);
				max = Math.max(max, dd[i]);

			}
			minValueMap.put(i, min);
			maxValueMap.put(i, max);
		}
		Random random = new Random();
		List<double[]> sampleList = new ArrayList<double[]>();
		for (int i = 0; i < 100000; i++) {
			double[] p = new double[obSize];
			for (int j = 0; j < p.length; j++) {
				double minValue = minValueMap.get(j);
				double maxValue = maxValueMap.get(j);
				double rvalue = minValue + random.nextDouble() * (maxValue);
				p[j] = rvalue;

			}
			sampleList.add(p);
		}

		double dominateSize1 = 0.0;
		double dominateSize2 = 0.0;
		for (double[] s : sampleList) {
			boolean dominate1 = false;
			boolean dominate2 = false;
			for (double[] o1 : list1) {
				if (dominateCompare(s, o1) == -1) {
					dominate1 = true;
					break;
				}
			}
			for (double[] o2 : list2) {
				if (dominateCompare(s, o2) == -1) {
					dominate2 = true;
					break;
				}
			}

			if (dominate1) {
				dominateSize1 = dominateSize1 + 1;
			}
			if (dominate2) {
				dominateSize2 = dominateSize2 + 1;
			}
		}

		dominateSize1 = dominateSize1 / sampleList.size();
		dominateSize2 = dominateSize2 / sampleList.size();

		// System.out.print("contriNum1:" + contriNum1);
		// System.out.print("contriNum2:" + contriNum2);
		double[] result = new double[2];
		result[0] = dominateSize1;
		result[1] = dominateSize2;
		return result;
	}

	/**
	 * 输入为一系列的paerto解，最后针对每个解给出其超空间算子
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static double[] hypervolumeForParetoList(List<List<double[]>> list) {
		double[] hyperValue = new double[list.size()];
		// 对list中所有的解集并起来，求最终的paerto解集
		List<double[]> finalParetoList = new ArrayList<double[]>();

		for (List<double[]> l : list) {
			finalParetoList.addAll(l);
		}
		finalParetoList = removeSame(finalParetoList);
		finalParetoList = dominateFliter(finalParetoList);
		// 然后用每一个进行对比
		for (int i = 0; i < hyperValue.length; i++) {
			List<double[]> l = list.get(i);
			double[] compare = hypervolume(l, finalParetoList);
			hyperValue[i] = compare[0];
		}
		return hyperValue;
	}

	/**
	 * 输入为一系列的paerto解，最后针对每个解给出其超空间算子，基于相同的采样点
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static double[] hypervolumeForParetoListSameSample(List<List<double[]>> list) {
		double[] hyperValue = new double[list.size()];
		// 对list中所有的解集并起来，求最终的paerto解集
		List<double[]> finalParetoList = new ArrayList<double[]>();

		for (List<double[]> l : list) {
			l = removeSame(l);
			l = dominateFliter(l);
			finalParetoList.addAll(l);
		}
		finalParetoList = removeSame(finalParetoList);
		// finalParetoList = dominateFliter(finalParetoList);

		Map<Integer, Double> minValueMap = new HashMap<Integer, Double>();
		Map<Integer, Double> maxValueMap = new HashMap<Integer, Double>();
		int obSize = finalParetoList.get(0).length;
		for (int i = 0; i < obSize; i++) {
			double min = finalParetoList.get(0)[0];
			double max = finalParetoList.get(0)[0];
			for (double[] dd : finalParetoList) {

				min = Math.min(min, dd[i]);
				max = Math.max(max, dd[i]);

			}
			minValueMap.put(i, min);
			maxValueMap.put(i, max);
		}
		Random random = new Random();
		List<double[]> sampleList = new ArrayList<double[]>();
		for (int i = 0; i < 5000; i++) {
			double[] p = new double[obSize];
			for (int j = 0; j < p.length; j++) {
				double minValue = minValueMap.get(j);

				double maxValue = maxValueMap.get(j);
				double rvalue = minValue + random.nextDouble() * (maxValue);
				p[j] = rvalue;

			}
			sampleList.add(p);
		}
		// 所有的pareto解集，对每一个进行计算
		for (int i = 0; i < hyperValue.length; i++) {
			List<double[]> ll = list.get(i);
			double dominateSize = 0.0;
			for (double[] s : sampleList) {
				boolean dominate = false;

				for (double[] o1 : ll) {
					if (dominateCompare(s, o1) == -1) {
						dominate = true;
						break;
					}
				}

				if (dominate) {
					dominateSize = dominateSize + 1;
				}

			}
			dominateSize = dominateSize / sampleList.size();
			hyperValue[i] = dominateSize;
		}

		return hyperValue;
	}

	/**
	 * 输入为一系列的paerto解，最后针对每个解给出前沿贡献
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static double[] NetFrontContributionForParetoList(List<List<double[]>> list) {
		double[] nfcValue = new double[list.size()];
		// 对list中所有的解集并起来，求最终的paerto解集
		List<double[]> finalParetoList = new ArrayList<double[]>();

		for (List<double[]> l : list) {
			l = removeSame(l);
			l = dominateFliter(l);
			finalParetoList.addAll(l);
		}
		finalParetoList = removeSame(finalParetoList);
		finalParetoList = dominateFliter(finalParetoList);

		// 需要计算每个解集在整个pareto解集中包含了多少个
		for (int i = 0; i < nfcValue.length; i++) {
			List<double[]> compareSoluSet = list.get(i);
			int contains = 0;
			for (double[] f : finalParetoList) {
				for (double[] c : compareSoluSet) {
					if (dominateCompare(c, f) == 2) {
						contains = contains + 1;
					}
				}
			}
			double nfc = (double) contains / (double) finalParetoList.size();
			nfcValue[i] = nfc;
		}

		return nfcValue;
	}

	public static double[] box() {
		return null;
	}

	/**
	 * 用于对一个double数组排序，返回位置
	 * 
	 * @param a
	 * @return
	 */
	public static int[] orderLocation(double[] a) {
		double[] b = new double[a.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = 0 - a[i];
		}

		Arrays.sort(b);
		for (int i = 0; i < b.length; i++) {
			b[i] = 0 - b[i];
		}
		List<Integer> loList = new ArrayList<Integer>();
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (b[i] == a[j]) {
					if (!loList.contains(j)) {
						loList.add(j);
						break;
					}
				}
			}
		}
		int[] result = new int[loList.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = loList.get(i);
		}

		return result;
	}

	/**
	 * 输入为一系列的paerto解，最后InvertedGenerationalDistance
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static double[] InvertedGenerationalDistance(List<List<double[]>> list) {
		double[] IGDValue = new double[list.size()];
		// 对list中所有的解集并起来，求最终的paerto解集
		List<double[]> allObjectList = new ArrayList<double[]>();

		for (List<double[]> l : list) {
			l = removeSame(l);
			l = dominateFliter(l);
			allObjectList.addAll(l);
		}

		// 用来记录最大最小值
		Map<Integer, Double> minValueMap = new HashMap<Integer, Double>();
		Map<Integer, Double> maxValueMap = new HashMap<Integer, Double>();
		int obSize = allObjectList.get(0).length;

		for (int i = 0; i < obSize; i++) {
			double min = allObjectList.get(0)[0];
			double max = allObjectList.get(0)[0];
			for (double[] dd : allObjectList) {

				min = Math.min(min, dd[i]);
				max = Math.max(max, dd[i]);

			}
			minValueMap.put(i, min);
			maxValueMap.put(i, max);
		}

		List<List<double[]>> normalizedList = new ArrayList<List<double[]>>();

		for (List<double[]> l : list) {
			List<double[]> newList = new ArrayList<double[]>();
			for (double[] o : l) {
				double[] newobvalue = new double[o.length];
				for (int i = 0; i < o.length; i++) {
					double minValue = minValueMap.get(i);
					double maxValue = maxValueMap.get(i);
					double normalValue = 0.0;
					if (maxValue != minValue) {
						normalValue = (o[i] - minValue) / (maxValue - minValue);
					}
					newobvalue[i] = normalValue;
				}
				newList.add(newobvalue);
			}
			normalizedList.add(newList);
		}

		List<double[]> finalParetoList = new ArrayList<double[]>();

		for (List<double[]> nl : normalizedList) {
			nl = removeSame(nl);
			nl = dominateFliter(nl);
			finalParetoList.addAll(nl);
		}
		finalParetoList = removeSame(finalParetoList);
		finalParetoList = dominateFliter(finalParetoList);

		for (int i = 0; i < IGDValue.length; i++) {
			List<double[]> cacuSet = normalizedList.get(i);
			double IGD = 0.0;
			for (double[] a : finalParetoList) {
				IGD = IGD + minDistancePointToParetoFront(a, cacuSet);
			}
			IGD = IGD / finalParetoList.size();
			IGDValue[i] = IGD;
		}

		return IGDValue;
	}

	// 计算欧式距离
	public static double EuclideanDistance(double[] a, double[] b) {
		double distance = 0.0;
		for (int i = 0; i < b.length; i++) {
			distance = distance + (a[i] - b[i]) * (a[i] - b[i]);
		}
		distance = Math.sqrt(distance);
		return distance;
	}

	// 计算一点离一个集合的最小距离
	public static double minDistancePointToParetoFront(double[] a, List<double[]> set) {
		double minDistance = 999999999.0;
		for (double[] p : set) {
			double distance = EuclideanDistance(a, p);
			if (minDistance > distance) {
				minDistance = distance;
			}
		}
		return minDistance;
	}

	/**
	 * 计算C(A,B)
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static double CValue(List<double[]> A, List<double[]> B) {
		A = removeSame(A);
		B = removeSame(B);
		A = dominateFliter(A);
		B = dominateFliter(B);
		// 计算A支配了多少个B中的数量
		double num = 0.0;
		for (double[] b : B) {
			for (double[] a : A) {
				if (dominateCompare(a, b) == 1 || dominateCompare(a, b) == 2) {
					num = num + 1;
					break;
				}
			}
		}

		return num / B.size();

	}
}
