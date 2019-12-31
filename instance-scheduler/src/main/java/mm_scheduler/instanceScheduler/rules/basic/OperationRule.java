/**
 * hba
 */
package mm_scheduler.instanceScheduler.rules.basic;

import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;

/**
 * @author: hba
 * @description: 工序排序规则接口
 * @date: 2019年12月18日
 *
 */
public class OperationRule implements Comparator<Operation> {
	/**
	 * FOPNR规则，剩余工序数越少越优先
	 */
	public static final int PART_ACTION_FOPNR = 1;
	/**
	 * MOPNR规则，剩余工序数越多越优先
	 */
	public static final int PART_ACTION_MOPNR = 2;

	/**
	 * SPT规则，工时越短越优先
	 */
	public static final int PART_ACTION_SPT = 3;
	/**
	 * LPT规则，工时越长越优先
	 */
	public static final int PART_ACTION_LPT = 4;
	/**
	 * SRPT规则，剩余工时越少越优先
	 */
	public static final int PART_ACTION_SRPT = 5;
	/**
	 * LRPT规则，剩余工时越多越优先
	 */
	public static final int PART_ACTION_LRPT = 6;
	/**
	 * MINSEQ规则，准备时间越少越优先
	 */
	public static final int PART_ACTION_MINSEQ = 7;
	/**
	 * EDD规则，工件交货期越早越优先
	 */
	public static final int PART_ACTION_EDD = 8;
	/**
	 * SPT/TWK规则，选择工序加工时间与总加工时间比值最小的工件
	 */
	public static final int PART_ACTION_SPT_TWK_RATIO = 9;
	/**
	 * LPT/TWK规则，选择工序加工时间与总加工时间比值最大的工件
	 */
	public static final int PART_ACTION_LPT_TWK_RATIO = 10;
	/**
	 * SPT/TWKR规则，选择工序加工时间与剩余加工时间比值最小的工件
	 */
	public static final int PART_ACTION_SPT_TWKR_RATIO = 11;
	/**
	 * LPT/TWKR规则，选择工序加工时间与剩余加工时间比值最大的工件
	 */
	public static final int PART_ACTION_LPT_TWKR_RATIO = 12;
	/**
	 * SPT*TWK规则，选择工序加工时间与总加工时间乘积最小的工件
	 */
	public static final int PART_ACTION_SPT_TWK_MULTI = 13;
	/**
	 * LPT*TWK规则，选择工序加工时间与总加工时间乘积最大的工件
	 */
	public static final int PART_ACTION_LPT_TWK_MULTI = 14;
	/**
	 * SPT*TWKR规则，选择工序加工时间与剩余加工时间乘积最小的工件
	 */
	public static final int PART_ACTION_SPT_TWKR_MULTI = 15;
	/**
	 * LPT*TWKR规则，选择工序加工时间与剩余加工时间乘积最大的工件
	 */
	public static final int PART_ACTION_LPT_TWKR_MULTI = 16;
	/**
	 * SRM规则，选择除当前考虑工序外剩余加工时间最短的工件
	 */
	public static final int PART_ACTION_SRM = 17;
	/**
	 * LRM规则，选择除当前考虑工序外剩余加工时间最长的工件
	 */
	public static final int PART_ACTION_LRM = 18;
	/**
	 * SSO规则，选择后继工序加工时间最短的工件
	 */
	public static final int PART_ACTION_SSO = 19;
	/**
	 * LSO规则，选择后继工序加工时间最长的工件
	 */
	public static final int PART_ACTION_LSO = 20;
	/**
	 * SPT+SSO规则，选择当前工序加工时间与后继工序加工时间最短的工件
	 */
	public static final int PART_ACTION_SPT_SSO_SUM = 21;
	/**
	 * LPT+LSO规则，选择当前工序加工时间与后继工序加工时间最长的工件
	 */
	public static final int PART_ACTION_LPT_LSO_SUM = 22;
	/**
	 * S-1规则，工件剩余交货时间越少越优先（ALL）
	 */
	public static final int PART_ACTION_S1 = 23;
	/**
	 * S-2规则，工件松弛率越小越优先（CR）
	 */
	public static final int PART_ACTION_S2 = 24;
	/**
	 * S-3规则，工件待加工部分松弛率越小越优先（SL）
	 */
	public static final int PART_ACTION_S3 = 25;
	/**
	 * S-4规则，工序松弛时间越小越优先（OSL）
	 */
	public static final int PART_ACTION_S4 = 26;
	/**
	 * S-5规则，工序松弛率越小越优先（OCR）
	 */
	public static final int PART_ACTION_S5 = 27;
	/**
	 * S-6规则，每一剩余工序可用时间越小越优先（ALL/OPN）
	 */
	public static final int PART_ACTION_S6 = 28;
	/**
	 * S-7规则，每一剩余工序松弛时间越小越优先（SL/OPN）
	 */
	public static final int PART_ACTION_S7 = 29;
	/**
	 * S-8规则，每单位剩余工作量松弛时间越小越优先（SL/WKR）
	 */
	public static final int PART_ACTION_S8 = 30;
	/**
	 * GW规则，权重越大越优先
	 */
	public static final int PART_ACTION_GW = 31;
	/**
	 * LW规则，权重越小越优先
	 */
	public static final int PART_ACTION_LW = 32;
	/**
	 * WSPT规则，加权最短加工时间（权重/工时越大）优先
	 */
	public static final int PART_ACTION_WSPT = 33;
	/**
	 * WLPT规则，加权最长加工时间（权重/工时越小）优先
	 */
	public static final int PART_ACTION_WLPT = 34;

	/**
	 * OPNDD规则，工序交货期越早越优先
	 */
	public static final int PART_ACTION_OPNDD = 9;

	/**
	 * ATC规则，ATC排序指数越高越优先
	 */
	public static final int PART_ACTION_ATC = 10;

	/**
	 * 系统默认优先级规则
	 */
	public static final int PART_ACTION_SYSTEM_RULE = 0;
	/**
	 * 机床选择系统默认规则
	 */
	public static final int DEVICE_ACTION_SYSTEM_RULE = 0;
	/**
	 * LN规则，机床已分派任务数越少越优先
	 */
	public static final int DEVICE_ACTION_LN = 1;
	/**
	 * LW规则，机床已分派任务总工时越少越优先
	 */
	public static final int DEVICE_ACTION_LW = 2;

	/**
	 * LWT规则，机床已分派任务总等待时间越少越优先
	 */
	public static final int DEVICE_ACTION_LWT = 3;

	/**
	 * MFT规则，机床空闲时间越多越优先
	 */
	public static final int DEVICE_ACTION_MFT = 4;
	/**
	 * EFT规则，最早能完成工序的机床最优先
	 */
	public static final int DEVICE_ACTION_EFT = 5;
	/**
	 * LUTIL规则，机床利用率越小越优先
	 */
	public static final int DEVICE_ACTION_LUTIL = 6;
	/**
	 * 规则序号
	 */
	public int ruleID;
	/**
	 * 规则名称
	 */
	public String ruleName;

	public OperationRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OperationRule(int ruleID, String ruleName) {
		super();
		this.ruleID = ruleID;
		this.ruleName = ruleName;
	}

	public int getRuleID() {
		return ruleID;
	}

	public void setRuleID(int ruleID) {
		this.ruleID = ruleID;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Operation o1, Operation o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
