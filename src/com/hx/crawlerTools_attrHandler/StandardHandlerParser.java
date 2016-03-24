/**
 * file name : StandardHandlerParser.java
 * created at : 1:07:25 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.interf.HandlerParser;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawler.util.WordsSeprator;
import com.hx.crawlerTools_attrHandler.adapter.OneBooleanResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.OneStringResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.StringIntResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.TwoIntResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.TwoStringResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.MultiArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneBooleanArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.StringIntArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.TwoIntArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.TwoStringArgsAttrHandler;

// Std 标准的处理handler字符串的解析器
public class StandardHandlerParser extends HandlerParser {

	// 增加一个方法
	// 1. 向handlerToResultType, nonArgsMap/ oneStringArgsMap/ twoStringArgsMap注册
	// 2. 添加该方法对应的校验逻辑
	// 3. 添加该方法对应的创建AttrHandler的逻辑
	// 4. 测试, 运行
	
	// -------------------- business config ----------------------------------
	// 支持的方法, 到其返回值的映射
	private static Map<String, Types> handlerToResultType = new HashMap<>();
	static {
		handlerToResultType.put(Constants.CONCATE, Types.String);
		handlerToResultType.put(Constants.REPLACE, Types.String);
		handlerToResultType.put(Constants.TRIM, Types.String);
		handlerToResultType.put(Constants.TRIM_AS_ONE, Types.String);
		handlerToResultType.put(Constants.TRIM_ALL, Types.String);
		handlerToResultType.put(Constants.SUB_STRING, Types.String);
		handlerToResultType.put(Constants.TO_UPPERCASE, Types.String);
		handlerToResultType.put(Constants.TO_LOWERCASE, Types.String);
		handlerToResultType.put(Constants.DO_NOTHING, Types.String);
		handlerToResultType.put(Constants.COND_EXP, Types.String);
		
		handlerToResultType.put(Constants.INDEX_OF, Types.Int);
		handlerToResultType.put(Constants.LAST_INDEX_OF, Types.Int);
		handlerToResultType.put(Constants.LENGTH, Types.Int);
		handlerToResultType.put(Constants.ADD, Types.Int);
		handlerToResultType.put(Constants.SUB, Types.Int);
		
		handlerToResultType.put(Constants.EQUALS, Types.Boolean);
		handlerToResultType.put(Constants.MATCHES, Types.Boolean);
		handlerToResultType.put(Constants.CONTAINS, Types.Boolean);
		handlerToResultType.put(Constants.NOT, Types.Boolean);
		handlerToResultType.put(Constants.GREATER_THAN, Types.Boolean);
		handlerToResultType.put(Constants.GREATER_EQUALS_THAN, Types.Boolean);
		handlerToResultType.put(Constants.LESS_THAN, Types.Boolean);
		handlerToResultType.put(Constants.LESS_EQUALS_THAN, Types.Boolean);
		handlerToResultType.put(Constants._EQUALS, Types.Boolean);
		handlerToResultType.put(Constants.NOT_EQUALS, Types.Boolean);
		handlerToResultType.put(Constants.CUTTING_OUT_AND, Types.Boolean);
		handlerToResultType.put(Constants.CUTTING_OUT_OR, Types.Boolean);
	}
	
	// 没有参数的方法, 一个字符串参数的方法, 两个字符串参数的方法, 多种选择的参数的方法
	public final static Set<String> noneOrStringArgsMap = new HashSet<>();
	public final static Set<String> oneBooleanArgsMap = new HashSet<>();
	public final static Set<String> oneOrTwoStringArgsMap = new HashSet<>();
	public final static Set<String> oneOrTwoIntArgsMap = new HashSet<>();
	public final static Set<String> twoStringArgsMap = new HashSet<>();
	public final static Set<String> stringOrStringIntArgsMap = new HashSet<>();
	public final static Set<String> oneBooleanTwoStringArgsMap = new HashSet<>();
	public final static Set<String> multiStringArgsMap = new HashSet<>();
	public final static Set<String> multiBooleanArgsMap = new HashSet<>();
	public final static Set<String> multiIntArgsMap = new HashSet<>();
	static {
		// trim, trim()
		noneOrStringArgsMap.add(Constants.TRIM);
		noneOrStringArgsMap.add(Constants.TRIM_AS_ONE);
		noneOrStringArgsMap.add(Constants.TRIM_ALL);
		noneOrStringArgsMap.add(Constants.TO_UPPERCASE);
		noneOrStringArgsMap.add(Constants.TO_LOWERCASE);
		noneOrStringArgsMap.add(Constants.LENGTH);
		noneOrStringArgsMap.add(Constants.DO_NOTHING);
		
		// !(true), not(true)
		oneBooleanArgsMap.add(Constants.NOT);
		
		// equals('abc'), equals('adb', 'abc')
		oneOrTwoStringArgsMap.add(Constants.EQUALS);
		oneOrTwoStringArgsMap.add(Constants.MATCHES);
		oneOrTwoStringArgsMap.add(Constants.CONTAINS);
		oneOrTwoStringArgsMap.add(Constants.GREATER_THAN);
		oneOrTwoStringArgsMap.add(Constants.GREATER_EQUALS_THAN);
		oneOrTwoStringArgsMap.add(Constants.LESS_THAN);
		oneOrTwoStringArgsMap.add(Constants.LESS_EQUALS_THAN);
		oneOrTwoStringArgsMap.add(Constants._EQUALS);
		oneOrTwoStringArgsMap.add(Constants.NOT_EQUALS);
		
		// subString(1), subString(1, 4)
		oneOrTwoIntArgsMap.add(Constants.SUB_STRING);
		
		// replace(regex, replacement)
		twoStringArgsMap.add(Constants.REPLACE);
		
		// add(arg01, arg02)
		multiIntArgsMap.add(Constants.ADD);
		multiIntArgsMap.add(Constants.SUB);
		
		// indexOf('abc'), indexOf('abc', 3)
		stringOrStringIntArgsMap.add(Constants.INDEX_OF);
		stringOrStringIntArgsMap.add(Constants.LAST_INDEX_OF);
		
		// condExp(true, truePart, falsePart), true ? truePart : falsePart
		oneBooleanTwoStringArgsMap.add(Constants.COND_EXP);
		
		// concate('abc', 'def', 'dd', ..), 'abc' + 'def' + 'dd', ..
		multiStringArgsMap.add(Constants.CONCATE);
		
		multiBooleanArgsMap.add(Constants.CUTTING_OUT_AND);
		multiBooleanArgsMap.add(Constants.CUTTING_OUT_OR);
		
	}
	
	// -------------------- business method ----------------------------------
	// 各个分隔符的位置
	public AttrHandler handlerParse(String handler) {
		CompositeAttrHandler hanlder = new CompositeAttrHandler();
		handler = Tools.trimAllSpaces(handler, Constants.escapeCharMap);
		Types lastOperationReturn = null;
		String lastOperationType = null;
		
		WordsSeprator sep = new WordsSeprator(handler, newSepToPos(), Constants.escapeMap, true);
		while(sep.hasNext() ) {
			if(lastOperationReturn != null) {
				if(lastOperationReturn.isFinal ) {
					Tools.assert0("the operation : '" + lastOperationType + "' is final operation, can't take more operation !  around : " + sep.rest() );
				}
			}
			lastOperationType = sep.next();
			Tools.assert0(Constants.handlerSet.contains(lastOperationType), "have no this opearnd : '" + lastOperationType + "' ! from now on support : " + Constants.handlerSet.toString() );
			String bracket = sep.next();
			Tools.assert0(Constants.bracketPair.containsKey(bracket), "expect a bracket : '" + bracket + "' ! , please check your format['" + sep.rest() + "'] ! ");
			Operand handlerOperand = getAttrHandlerContent(sep, bracket, Constants.bracketPair);
			lastOperationReturn = checkHandlerContent(sep, handlerOperand );
			AttrHandler handlerNow = getAttrHandler(sep, handlerOperand);
				handlerNow.operationType(lastOperationType);
				handlerNow.operationReturn(lastOperationReturn);
			checkHandler(handlerNow);
			hanlder.addHandler(handlerNow);
//			Log.log(handlerOperand );
			
			String dotOrNull = sep.next();
			if((dotOrNull == null) ) {
				break ;
			}
			Tools.assert0(".".equals(dotOrNull), "expect a dot : '.' ! , please check your format['" + sep.rest() + "'] ! ");
		}
		
		return hanlder;
	}
	
	// 获取当前的AttrHandler的各个数据结构
	private Operand getAttrHandlerContent(WordsSeprator sep, String bracket, Map<String, String> bracketpair) {
		Operand handlerOperand = null;
		if(sep.hasNext() ) {
			handlerOperand = getAttrHandlerOperand(sep, bracket, bracketpair, isFromNone);
			Tools.assert0(bracketpair.get(bracket).equals(sep.next() ), "expect a rightBracket : ')' ! , please check your format['" + sep.rest() + "'] ! ");
		}
		
		return handlerOperand;
	}
	// 校验给定的handlerContent
	private Types checkHandlerContent(WordsSeprator sep, Operand attrHandlerContent) {
		if(attrHandlerContent == null) {
			return Types.Null;
		}
		if(attrHandlerContent.type() == OperandTypes.String) {
			try {
				Integer.parseInt(attrHandlerContent.name() );
				attrHandlerContent.type(OperandTypes.Int);
				return Types.Int;
			} catch(Exception e) {
				if(Constants.TRUE.equals(attrHandlerContent.name()) || Constants.FALSE.equals(attrHandlerContent.name()) ) {
					attrHandlerContent.type(OperandTypes.Boolean);
					return Types.Boolean;
				}
				
				return Types.String;
			}
		}
//		if("length".equals(attrHandlerContent.name()) ) {
//			Log.horizon();
//		}
		Types curType = Types.String;
		Operand lastOperand = null, operand = attrHandlerContent;
		
		while(true ) {
			Tools.assert0(((operand != null) && (handlerToResultType.containsKey(operand.name()) || Constants.ANONY_OPERAND_NAME.equals(operand.name()) ) ), "have no this opearnd : '" + operand.name() + "' ! from now on support : " + handlerToResultType.keySet().toString() );
			if(curType.isFinal) {
				Tools.assert0("the operation : '" + lastOperand.name() + "' is final operation, can't take more operation !  around : " + sep.rest(operand.pos()) );
			}
			// nonArgsOperator, works like : "map(length ), map(length() )"
			if(Constants.ANONY_OPERAND_NAME.equals(operand.name()) ) {
				Tools.assert0(((operand.operands() != null) && (operand.operands().size() == 1) ), "anonyOperand can only take one parameter, please check it ! around : " + sep.rest(operand.pos()) );
				curType = checkHandlerContent(sep, attrHandlerContent.operand(0) );
			} else if(noneOrStringArgsMap.contains(operand.name()) ) {
				boolean isValid = (operand.operands() == null) 
								|| ( (operand.operands().size() == 1) 
									 && (Constants.EMPTY_OPERAND_NAME.equals(operand.operand(0).name())) || (stringAble(operand.operand(0).type())) );
				Tools.assert0(isValid, "the operand : '" + operand.name() + "' take ([String]), 'no parameter or String' , please check it ! around : " + sep.rest(operand.pos()) );
			} else if(oneBooleanArgsMap.contains(operand.name()) ) {
				Types param = checkHandlerContent(sep, operand.operand(0));
				boolean isValid = (operand.operands() != null) 
						&& (operand.operands().size() == 1) && (Types.Boolean  == param ) ;
				assert0(isValid, operand.name(), "Boolean", operand.operands().size(),  (param + ", ..."), sep.rest(operand.pos()) );
			} else if(oneOrTwoStringArgsMap.contains(operand.name()) ) {
				Types param = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				boolean isValid = (operand.operands() != null) 
						&& ((operand.operands().size() == 1) || ((operand.operands().size() == 2)) ) 
						&& (stringAble(param) 
						&& (stringAble(param02) || (Types.Null == param02) ) ) ;
				assert0(isValid, operand.name(), "String [, String]", operand.operands().size(),  (param + ", ..."), sep.rest(operand.pos()) );
			} else if(twoStringArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				boolean isValid = (operand.operands() != null)
						&& (operand.operands().size() == 2)
						&& (stringAble(param01) && stringAble(param02) );
				assert0(isValid, operand.name(), "String, String", operand.operands().size(),  (param01 + ", " + param02 + ", ..."), sep.rest(operand.pos()) );
			} else if(stringOrStringIntArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				boolean isValid = (operand.operands() != null)
						&& ((operand.operands().size() == 1) || (operand.operands().size() == 2) )
						&& stringAble(param01)
						&& (Types.Int == param02 || Types.Null == param02);
				assert0(isValid, operand.name(), "String[, Int]", operand.operands().size(),  (param01 + ", " + param02 + ", ..."), sep.rest(operand.pos()) );
			} else if(oneOrTwoIntArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				boolean isValid = (operand.operands() != null)
						&& ((operand.operands().size() == 1) || (operand.operands().size() == 2) )
						&& Types.Int == param01
						&& (Types.Int == param02 || Types.Null == param02);
				assert0(isValid, operand.name(), "Int[, Int]", operand.operands().size(),  (param01 + ", " + param02 + ", ..."), sep.rest(operand.pos()) );
			} else if(oneBooleanTwoStringArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				Types param03 = checkHandlerContent(sep, operand.operand(2));
				boolean isValid = (operand.operands() != null)
						&& ((operand.operands().size() == 3) )
						&& (Types.Boolean == param01 && stringAble(param02) && stringAble(param03) );
				assert0(isValid, operand.name(), "Boolean, String, String", operand.operands().size(),  (param01 + ", " + param02 + ", " + param03 + ", ..."), sep.rest(operand.pos()) );
			} else if(multiStringArgsMap.contains(operand.name()) ) {
				boolean isValid = (operand.operands() != null) && (operand.operands().size() > 0);
				StringBuilder typeError = new StringBuilder();
				if(isValid) {
					for(Operand ope : operand.operands() ) {
						Types opeType = checkHandlerContent(sep, ope);
						if(! stringAble(opeType) ) {
							isValid = false;
						}
						typeError.append(opeType + ", ");
					}
					typeError.delete(typeError.length()-2, typeError.length() );
				}
				assert0(isValid, operand.name(), "String, String, ...", operand.operands().size(), typeError.toString(), sep.rest(operand.pos()) );
			} else if(multiBooleanArgsMap.contains(operand.name()) ) {
				boolean isValid = (operand.operands() != null) && (operand.operands().size() > 0);
				StringBuilder typeError = new StringBuilder();
				if(isValid) {
					for(Operand ope : operand.operands() ) {
						Types opeType = checkHandlerContent(sep, ope);
						if(Types.Boolean != opeType ) {
							isValid = false;
						}
						typeError.append(opeType + ", ");
					}
					typeError.delete(typeError.length()-2, typeError.length() );
				}
				assert0(isValid, operand.name(), "Boolean, Boolean, ...", operand.operands().size(), typeError.toString(), sep.rest(operand.pos()) );
			} else if(multiIntArgsMap.contains(operand.name()) ) {
				boolean isValid = (operand.operands() != null) && (operand.operands().size() > 0);
				StringBuilder typeError = new StringBuilder();
				if(isValid) {
					for(Operand ope : operand.operands() ) {
						Types opeType = checkHandlerContent(sep, ope);
						if(Types.Int != opeType ) {
							isValid = false;
						}
						typeError.append(opeType + ", ");
					}
					typeError.delete(typeError.length()-2, typeError.length() );
				}
				assert0(isValid, operand.name(), "Int, Int, ...", operand.operands().size(), typeError.toString(), sep.rest(operand.pos()) );				
			} else {
				// can't got there !
				Tools.assert0("unknow operand : '" + operand.name() + "', please check it !   around : " + sep.rest(operand.pos()) );
			}
			
			if(! Constants.ANONY_OPERAND_NAME.equals(operand.name()) ) {
				curType = handlerToResultType.get(operand.name() );
			}
			if(operand.hasNext()) {
				lastOperand = operand;
				operand = operand.next;
			} else {
				break ;
			}
		}
		
		return curType;
	}
	private void assert0(boolean isValid, String operandName, String needTypes, int got, String gotTypes, String around ) {
		String errorMsg = String.format("the operand : '%s' need '%s' as parameter, but got %d params: '(%s)',  around : %s", operandName, needTypes, got, gotTypes, around);
		Tools.assert0(isValid, errorMsg);
	}
	// 根据attrHandlerContent, 获取一个AttrHandler
	private AttrHandler getAttrHandler(WordsSeprator sep, Operand attrHandler) {
		if(! attrHandler.hasNext() ) {
			return getAttrHandler0(sep, attrHandler);
		}
		CompositeAttrHandler attrHandlerChain = new CompositeAttrHandler();
		Operand operand = attrHandler;
		attrHandlerChain.addHandler(getAttrHandler0(sep, operand) );
		while(operand.hasNext() ) {
			operand = operand.next();
			attrHandlerChain.addHandler(getAttrHandler0(sep, operand) );
		}
		
		return attrHandlerChain;
	}
	private AttrHandler getAttrHandler0(WordsSeprator sep, Operand attrHandler) {
		if(Constants.ANONY_OPERAND_NAME.equals(attrHandler.name()) ) {
			return getAttrHandler(sep, attrHandler.operand(0) );
		}
		if(noneOrStringArgsMap.contains(attrHandler.name()) ) {
			return getNoneOrStringArgsHandler(sep, attrHandler);
		} else if(oneBooleanArgsMap.contains(attrHandler.name()) ) {
			return getOneBooleanArgsHandler(sep, attrHandler);
		} else if(oneOrTwoStringArgsMap.contains(attrHandler.name()) ) {
			return getOneOrTwoStringArgsHandler(sep, attrHandler);
		} else if(twoStringArgsMap.contains(attrHandler.name()) ) {
			return getTwoStringArgsHandler(sep, attrHandler);
		} else if(stringOrStringIntArgsMap.contains(attrHandler.name()) ) {
			return getStringOrStringIntArgsHandler(sep, attrHandler);
		} else if(oneOrTwoIntArgsMap.contains(attrHandler.name()) ) {
			return getOneOrTwoIntArgsHandler(sep, attrHandler);
		} else if(oneBooleanTwoStringArgsMap.contains(attrHandler.name()) ) {
			return getOneBooleanTwoStringArgsHandler(sep, attrHandler);
		} else if(multiStringArgsMap.contains(attrHandler.name()) ) {
			return getMultiStringArgsHandler(sep, attrHandler);
		} else if(multiBooleanArgsMap.contains(attrHandler.name()) ) {
			return getMultiBooleanArgsHandler(sep, attrHandler);
		} else if(multiIntArgsMap.contains(attrHandler.name()) ) {
			return getMultiIntArgsHandler(sep, attrHandler);
		} else {
			// recorvey as 'ConstantsStringValue'
			return new ConstantsAttrHandler(attrHandler.name() );
		}
	}
	// 校验当前handler
	private void checkHandler(AttrHandler handlerNow) {
		switch (handlerNow.operationType() ) {
		case Constants.MAP:
			
			break;
		case Constants.FILTER:
			Tools.assert0(Types.Boolean == handlerNow.operationReturn(), "operation : 'filter' just support (Boolean) as parameter !");
			break;
		default:
			Tools.assert0("have no this operationType ! from now on support : " + Constants.handlerSet.toString() );
			break;
		}
	}

	// -------------------- assit method ----------------------------------
	private static int isFromConcate = 1;
	private static int isFromCuttingOut = isFromConcate << 1;
	private static int isFromCondExp = isFromCuttingOut << 1;
	private static int isFromComp = isFromCondExp << 1;
	private static int isFromAll = isFromConcate | isFromCuttingOut | isFromCondExp | isFromComp;
	private static int isFromNone = ~isFromAll;
	// 获取一个操作数
		// 允许三种情况
		// 1. methodName(param1, param2)
		// 2. str1 + str2
		// 3. trim().subString(1, 3)
		// 4. 'str1' + str2 / subString('sd')
	private Operand getAttrHandlerOperand(WordsSeprator sep, String bracket, Map<String, String> bracketpair, int flags)	{
		if(bracketpair.get(bracket).equals(sep.seek()) ) {
			return Operand.emptyOperand;
		}
		String method = sep.seek();
//		Tools.assert0((allAttrHandler.contains(method) || (sep.seek().equals(Constants.STRING_CONCATE)) || (sep.seek().equals(Constants.PARAM_SEP)) ), "unknow method : " + method + ",  please check your format['" + handler + "'] !" );
		// incase of "map((trim()) )"
		if(bracketpair.containsKey(method) ) {
			method = Constants.ANONY_OPERAND_NAME;
		// incase of "! equals('abc')"
		} else if(Constants.STRING_NOT.equals(method)) {
			sep.next();
			Operand operand = new Operand(Constants.NOT, OperandTypes.Method, sep.lastNextPos() );
			operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromNone) );
			return operand;
		// common case : "trim()" or "'Hello' + $this"
		} else {
			sep.next();
		}
		Operand operand = new Operand(method, sep.lastNextPos() );
		operand.type(OperandTypes.String);
		// incase of "map(length )", mark length as 'Method'	
		if(noneOrStringArgsMap.contains(operand.name()) ) {
			operand.type(OperandTypes.Method);
		}
		
		while(sep.hasNext() ) {
			// 			  		  |idx			   |
			// incase of "(param01, xx)", "(param01)"
			if(Constants.PARAM_SEP.equals(sep.seek()) 
					|| bracketpair.get(bracket).equals(sep.seek())
					|| (isFrom(flags, isFromConcate) && (Constants.STRING_CONCATE.equals(sep.seek())) || Constants.COND_EXP_BRANCH.equals(sep.seek() ) ) 
					|| (isFrom(flags, isFromCuttingOut) && Constants.STRING_CONCATE.equals(sep.seek()) ) 
					|| (isFrom(flags, isFromComp) && (Constants.STRING_CONCATE.equals(sep.seek()) || Constants.AND.equals(sep.seek()) || Constants.OR.equals(sep.seek())) ) 
					|| (isFrom(flags, isFromCondExp) && Constants.COND_EXP_BRANCH.equals(sep.seek()) ) 
					) {
				return operand;
			}
			
			String next = sep.next();
			//					 |
			// incase of "indexOf($this, abc)"
			if(bracket.equals(next) ) {
				operand.type(OperandTypes.Method);
				Operand ope = getAttrHandlerOperand(sep, bracket, bracketpair, isFromNone);
				operand.addOperand(ope );
				while(sep.hasNext() ) {
					String dotCommaOrNot = sep.next();
					if(Constants.SUB_HANDLER_CALL.equals(dotCommaOrNot) ) {
						ope.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromNone) );
					} else if(Constants.PARAM_SEP.equals(dotCommaOrNot) ) {
						ope = getAttrHandlerOperand(sep, bracket, bracketpair, isFromNone);
						operand.addOperand(ope );
					} else {
						Tools.assert0(bracketpair.get(bracket).equals(dotCommaOrNot), "expect a  : '" + bracketpair.get(bracket) + "' ! , please check your format['" + sep.rest() + "'] ! ");
						break ;
					}
				}
			}
			if(Constants.SUB_HANDLER_CALL.equals(next) ) {
				Operand ope = getAttrHandlerOperand(sep, bracket, bracketpair, isFromNone);
				operand.setNext(ope);
			}
			
			// 处理语法糖, 拼串, 短路与/ 或, 条件表达式, 比较运算符
			// 			    | |
			// incase of "a + b"
			if(Constants.STRING_CONCATE.equals(next) ) {
				Operand oldOperand = operand;
				operand = new Operand(Constants.CONCATE, OperandTypes.Method, sep.lastNextPos() );
				operand.addOperand(oldOperand );
					
				boolean isFirstConcate = true;
				do {
					if(! isFirstConcate) {
						sep.next();
					}
					isFirstConcate = false;
					operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromConcate) );
				} while(Constants.STRING_CONCATE.equals(sep.seek()) );
			// 规约拼串的优先级高于 短路与/ 或[&&, ||]
			} else if(Constants.AND.equals(next) || Constants.OR.equals(next) ) {
				String curSymbol = next;
				Tools.assert0(curSymbol.equals(sep.seek() ), "expect a : " + curSymbol + " ! aoround : " + sep.rest() );
				sep.next();
				Operand oldOperand = operand;
				if(Constants.AND.equals(curSymbol) ) {
					operand = new Operand(Constants.CUTTING_OUT_AND, OperandTypes.Method, sep.lastNextPos() );
				} else {
					operand = new Operand(Constants.CUTTING_OUT_OR, OperandTypes.Method, sep.lastNextPos() );
				}
				operand.addOperand(oldOperand );
					
				boolean isFirstConcate = true;
				do {
					if(! isFirstConcate) {
						sep.next();
						Tools.assert0(curSymbol.equals(sep.seek() ), "expect a : " + curSymbol + " ! aoround : " + sep.rest() );
						sep.next();
					}
					isFirstConcate = false;
					operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromCuttingOut) );
				} while(curSymbol.equals(sep.seek()) );
				// 规约拼串的优先级高于比较[>, <, ..]
//				 a + b == c + d
//					   ||
//					   \/
//				  ab   ==  cd				
			} else if(Constants.GT.equals(next) || Constants.LT.equals(next) ) {
				Operand oldOperand = operand;
				if(Constants.GT.equals(next) ) {
					if(! Constants.EQ.equals(sep.seek()) ) {
						operand = new Operand(Constants.GREATER_THAN, OperandTypes.Method, sep.lastNextPos() );
					} else {
						sep.next();
						operand = new Operand(Constants.GREATER_EQUALS_THAN, OperandTypes.Method, sep.lastNextPos() );
					}
				} else {
					if(! Constants.EQ.equals(sep.seek()) ) {
						operand = new Operand(Constants.LESS_THAN, OperandTypes.Method, sep.lastNextPos() );
					} else {
						sep.next();
						operand = new Operand(Constants.LESS_EQUALS_THAN, OperandTypes.Method, sep.lastNextPos() );
					}
				}
				operand.addOperand(oldOperand);
				operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromComp) );
			} else if(Constants.EQ.equals(next) || Constants.STRING_NOT.equals(next) ) {
				Tools.assert0(Constants.EQ.equals(sep.seek() ), "expect a : " + Constants.EQ + " ! aoround : " + sep.rest() );
				sep.next();
				Operand oldOperand = operand;
				if(Constants.EQ.equals(next) ) {
					operand = new Operand(Constants._EQUALS, OperandTypes.Method, sep.lastNextPos() );
				} else {
					operand = new Operand(Constants.NOT_EQUALS, OperandTypes.Method, sep.lastNextPos() );
				}
				operand.addOperand(oldOperand);
				operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromComp) );
			} else if(Constants.COND_EXP_COND.equals(next) ) {
				Operand oldOperand = operand;
				operand = new Operand(Constants.COND_EXP, OperandTypes.Method, sep.lastNextPos() );
				operand.addOperand(oldOperand );
				
				operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromCondExp) );
				Tools.assert0(Constants.COND_EXP_BRANCH.equals(sep.seek() ), "expect a : " + Constants.COND_EXP_BRANCH + " ! aoround : " + sep.rest() );
				sep.next();
				operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, isFromCondExp) );
			}
		}
		
		return operand;
	}

	// 获取一个没有参数的/ 存在两个字符串参数的/ 存在一个字符串参数的 AttrHandler
	private AttrHandler getNoneOrStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		switch (attrHandler.name() ) {
		case Constants.TRIM:
			return getNoneOrOneStringArgsHandler0(sep, ope, new TrimAttrHandler() );
		case Constants.TRIM_AS_ONE:
			return getNoneOrOneStringArgsHandler0(sep, ope, new TrimAsOneAttrHandler() );
		case Constants.TRIM_ALL:
			return getNoneOrOneStringArgsHandler0(sep, ope, new TrimAllAttrHandler() );
		case Constants.TO_UPPERCASE:
			return getNoneOrOneStringArgsHandler0(sep, ope, new ToUpperCaseAttrHandler() );
		case Constants.TO_LOWERCASE:
			return getNoneOrOneStringArgsHandler0(sep, ope, new ToLowerCaseAttrHandler() );
		case Constants.LENGTH:
			return getNoneOrOneStringArgsHandler0(sep, ope, new LengthAttrHandler() );
		case Constants.DO_NOTHING:
			return getNoneOrOneStringArgsHandler0(sep, ope, new DoNothingAttrHandler() );
		default:
			Tools.assert0("got an unknow 'noArgs' method : " + attrHandler.name() );
			return null;
		}
	}
		private AttrHandler getNoneOrOneStringArgsHandler0(WordsSeprator sep, Operand ope, NoneOrOneStringArgsAttrHandler handler) {
			// null & 'emptyOperand'
			if((ope == null) || (ope.type() == OperandTypes.Null) ) {
				return new OneStringResultHandlerArgsAttrHandler(handler, Constants.HANDLER_UNDEFINED);
			} else {
				if(stringAble(ope.type()) ) {
					return new OneStringResultHandlerArgsAttrHandler(handler, ope.name() );
				} else {
					return new OneStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
				}
			}
		}
	private AttrHandler getOneBooleanArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		switch (attrHandler.name() ) {
			case Constants.NOT:
					return getOneBooleanArgsHandler0(sep, ope, new NotAttrHandler() );
			default:
				Tools.assert0("got an unknow '(Boolean)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getOneBooleanArgsHandler0(WordsSeprator sep, Operand ope, OneBooleanArgsAttrHandler handler) {
			if(OperandTypes.Boolean == ope.type() ) {
				return new OneBooleanResultHandlerArgsAttrHandler(handler, Boolean.parseBoolean(ope.name()) );
			} else if(OperandTypes.Method == ope.type() ) {
				return new OneBooleanResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
			} else {
				// OperandTypes.Null
				return null;
			}
		}
	private AttrHandler getOneOrTwoStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		Operand ope02 = attrHandler.operand(1);
		switch (attrHandler.name() ) {
			case Constants.EQUALS:
					return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new EqualsAttrHandler() );
			case Constants.MATCHES:
					return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new MatchesAttrHandler() );
			case Constants.CONTAINS:
				return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new ContainsAttrHandler() );
			case Constants.GREATER_THAN:
				return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new GreaterThanAttrHandler() );
			case Constants.GREATER_EQUALS_THAN:
				return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new GreaterEqualsThanAttrHandler() );
			case Constants.LESS_THAN:
				return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new LessThanAttrHandler() );
			case Constants.LESS_EQUALS_THAN:
				return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new LessEqualsThanAttrHandler() );
			case Constants._EQUALS:
				return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new EqualsAttrHandler() );
			case Constants.NOT_EQUALS:
				return getOneOrTwoStringArgsHandler0(sep, ope, ope02, new NotEqualsAttrHandler() );
			default:
				Tools.assert0("got an unknow '(String)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getOneOrTwoStringArgsHandler0(WordsSeprator sep, Operand ope, Operand ope02, TwoStringArgsAttrHandler handler) {
			if(ope02 == null) {
				if(stringAble(ope.type()) ) {
					return new TwoStringResultHandlerArgsAttrHandler(handler, ope.name() );
				} else {
					return new TwoStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
				}
			} else {
				if(stringAble(ope.type()) ) {
					if(stringAble(ope02.type()) ) {
						return new TwoStringResultHandlerArgsAttrHandler(handler, ope.name(), ope02.name() );
					} else {
						return new TwoStringResultHandlerArgsAttrHandler(handler, ope.name(), getAttrHandler(sep, ope02) );
					}
				} else {
					if(stringAble(ope02.type()) ) {
						return new TwoStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), ope02.name() );
					} else {
						return new TwoStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), getAttrHandler(sep, ope02) );
					}
				}
			}
		}
	private AttrHandler getTwoStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		Operand ope02 = attrHandler.operand(1);
		switch (attrHandler.name() ) {
			case Constants.REPLACE:
					return getTwoStringArgsHandler0(sep, ope, ope02, new ReplaceAttrHandler() );
			default :
				Tools.assert0("got an unknow '(String, String)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getTwoStringArgsHandler0(WordsSeprator sep, Operand ope, Operand ope02, TwoStringArgsAttrHandler handler) {
			Tools.assert0((ope02 != null), "handler : " + handler.name() + " need another 'String' as parameter ! around : " + sep.rest(ope.pos()) );
			if(stringAble(ope.type()) ) {
				if(stringAble(ope02.type()) ) {
					return new TwoStringResultHandlerArgsAttrHandler(handler, ope.name(), ope02.name() );
				} else {
					return new TwoStringResultHandlerArgsAttrHandler(handler, ope.name(), getAttrHandler(sep, ope02) );
				}
			} else {
				if(stringAble(ope02.type()) ) {
					return new TwoStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), ope02.name() );
				} else {
					return new TwoStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), getAttrHandler(sep, ope02) );
				}
			}
		}
	private AttrHandler getStringOrStringIntArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		Operand ope02 = attrHandler.operand(1);
		switch (attrHandler.name() ) {
			case Constants.INDEX_OF:
				return getStringOrStringIntArgsHandler0(sep, ope, ope02, new IndexAttrHandler() );
			case Constants.LAST_INDEX_OF:
				return getStringOrStringIntArgsHandler0(sep, ope, ope02, new LastIndexAttrHandler() );
			default :
				Tools.assert0("got an unknow '(String [, Int])' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getStringOrStringIntArgsHandler0(WordsSeprator sep, Operand ope, Operand ope02, StringIntArgsAttrHandler handler) {
			if(ope02 == null) {
				if(stringAble(ope.type()) ) {
					return new StringIntResultHandlerArgsAttrHandler(handler, ope.name() );
				} else {
					return new StringIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
				}
			} else {
				if(stringAble(ope.type()) ) {
					// be checked by stage 'Check'
					if(OperandTypes.Int == ope02.type() ) {
						return new StringIntResultHandlerArgsAttrHandler(handler, ope.name(), Integer.parseInt(ope02.name()) );
					} else {
						return new StringIntResultHandlerArgsAttrHandler(handler, ope.name(), getAttrHandler(sep, ope02) );
					}
				} else {
					if(OperandTypes.Int == ope02.type() ) {
						return new StringIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), Integer.parseInt(ope02.name()) );
					} else {
						return new StringIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), getAttrHandler(sep, ope02) );
					}
				}
			}
		}
	private AttrHandler getOneOrTwoIntArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		Operand ope02 = attrHandler.operand(1);
		switch (attrHandler.name() ) {
			case Constants.SUB_STRING:
				return getOneOrTwoIntArgsHandler0(sep, ope, ope02, new SubStringAttrHandler() );
			default :
				Tools.assert0("got an unknow '(Int [, Int])' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getOneOrTwoIntArgsHandler0(WordsSeprator sep, Operand ope, Operand ope02, TwoIntArgsAttrHandler handler) {
			if(ope02 == null) {
				// be checked by stage 'Check'
				if(OperandTypes.Int == ope.type() ) {
					handler.setArgs(Integer.parseInt(ope.name() ), Constants.TARGET_UNDEFINED);
					return handler;
				} else {
					return new TwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
				}
			} else {
				if(OperandTypes.Int == ope.type() ) {
					if(OperandTypes.Int == ope02.type() ) {
						handler.setArgs(Integer.parseInt(ope.name() ), Integer.parseInt(ope02.name()) );
						return handler;
					} else {
						return new TwoIntResultHandlerArgsAttrHandler(handler, Integer.parseInt(ope.name() ), getAttrHandler(sep, ope02) );
					}
				} else {
					if(OperandTypes.Int == ope02.type() ) {
						return new TwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), Integer.parseInt(ope02.name()) );
					} else {
						return new TwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope), getAttrHandler(sep, ope02) );
					}
				}
			}
		}
	private AttrHandler getOneBooleanTwoStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		switch (attrHandler.name() ) {
			case Constants.COND_EXP :
				{
					Operand ope = attrHandler.operand(0);
					Operand ope01 = attrHandler.operand(1);
					Operand ope02 = attrHandler.operand(2);
					if(stringAble(ope.type()) ) {
						if(stringAble(ope01.type()) ) {
							if(stringAble(ope02.type()) ) {
								return new CondExpAttrHandler(ope.name(), ope01.name(), ope02.name() );
							} else {
								return new CondExpAttrHandler(new ConstantsAttrHandler(ope.name()), ope01.name(), getAttrHandler(sep, ope02) );
							}
						} else {
							if(stringAble(ope02.type()) ) {
								return new CondExpAttrHandler(new ConstantsAttrHandler(ope.name()), getAttrHandler(sep, ope01), ope02.name() );
							} else {
								return new CondExpAttrHandler(new ConstantsAttrHandler(ope.name()), getAttrHandler(sep, ope01), getAttrHandler(sep, ope02) );
							}
						}
					} else {
						if(stringAble(ope01.type()) ) {
							if(stringAble(ope02.type()) ) {
								return new CondExpAttrHandler(getAttrHandler(sep, ope), ope01.name(), ope02.name() );
							} else {
								return new CondExpAttrHandler(getAttrHandler(sep, ope), ope01.name(), getAttrHandler(sep, ope02) );
							}
						} else {
							if(stringAble(ope02.type()) ) {
								return new CondExpAttrHandler(getAttrHandler(sep, ope), getAttrHandler(sep, ope01), ope02.name() );
							} else {
								return new CondExpAttrHandler(getAttrHandler(sep, ope), getAttrHandler(sep, ope01), getAttrHandler(sep, ope02) );
							}
						}
					}
				}
			default :
				Tools.assert0("got an unknow '(String, String, ...)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
	private AttrHandler getMultiStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		switch (attrHandler.name() ) {
			case Constants.CONCATE :
				return getMultiStringArgsHandler0(sep, attrHandler, new ConcateAttrHandler(attrHandler.operands().size()) );
			default :
				Tools.assert0("got an unknow '(String, String, ...)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getMultiStringArgsHandler0(WordsSeprator sep, Operand attrHandler, MultiArgsAttrHandler handler) {
			for(Operand operand : attrHandler.operands()) {
				if(stringAble(operand.type()) ) {
					handler.addHandler(new ConstantsAttrHandler(operand.name()) );
				} else {
					handler.addHandler(getAttrHandler(sep, operand) );
				}
			}
			
			return handler;
		}
	private AttrHandler getMultiBooleanArgsHandler(WordsSeprator sep, Operand attrHandler) {
		switch (attrHandler.name() ) {
			case Constants.CUTTING_OUT_AND :
				return getMultiBooleanArgsHandler0(sep, attrHandler, new CuttingOutAndAttrHandler(attrHandler.operands().size()) );
			case Constants.CUTTING_OUT_OR :
				return getMultiBooleanArgsHandler0(sep, attrHandler, new CuttingOutOrAttrHandler(attrHandler.operands().size()) );
			default :
				Tools.assert0("got an unknow '(String, String, ...)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getMultiBooleanArgsHandler0(WordsSeprator sep, Operand attrHandler, MultiArgsAttrHandler handler) {
			for(Operand operand : attrHandler.operands()) {
				if(OperandTypes.Boolean == operand.type() ) {
					handler.addHandler(new ConstantsAttrHandler(operand.name()) );
				} else {
					handler.addHandler(getAttrHandler(sep, operand) );
				}
			}
			
			return handler;
		}
		private AttrHandler getMultiIntArgsHandler(WordsSeprator sep, Operand attrHandler) {
			Operand ope = attrHandler.operand(0);
			Operand ope02 = attrHandler.operand(1);
			switch (attrHandler.name() ) {
				case Constants.ADD:
						return getMultiIntArgsHandler0(sep, attrHandler, new AddAttrHandler() );
				case Constants.SUB:
					return getMultiIntArgsHandler0(sep, attrHandler, new SubAttrHandler() );						
				default :
					Tools.assert0("got an unknow '(Int, Int)' method : " + attrHandler.name() );
					break ;
			}
			
			return null;
		}
			private AttrHandler getMultiIntArgsHandler0(WordsSeprator sep, Operand attrHandler, MultiArgsAttrHandler handler) {
				for(Operand operand : attrHandler.operands()) {
					if(OperandTypes.Int == operand.type() ) {
						handler.addHandler(new ConstantsAttrHandler(operand.name()) );
					} else {
						handler.addHandler(getAttrHandler(sep, operand) );
					}
				}
				
				return handler;
			}			
		
	// 判断给定的类型是否是Method
	private boolean stringAble(OperandTypes type) {
		return type == OperandTypes.String || type == OperandTypes.Int;
	}
	private boolean stringAble(Types type) {
		return type == Types.String || type == Types.Int || type == Types.Boolean;
	}
	// 判断给定的flag中是否存在对应的位
	private boolean isFrom(int flag, int mask) {
		return (flag & mask) != 0; 
	}
	// 获取一个sepToPos
	private Map<String, Integer> newSepToPos() {
		Map<String, Integer> sepToPos = new HashMap<>();
		Iterator<String> it = Constants.handlerParserSeps.iterator();
		while(it.hasNext() ) {
			sepToPos.put(it.next(), 0);
		}
		return sepToPos;
	}
	
	// -------------------- business Types ----------------------------------
	// 操作数[可能为复合的符号]
	static class Operand {
		private String opeName;
		private OperandTypes type;
		private int pos;
		private List<Operand> operands;
		private Operand next;
		
		// 空的操作数
		public final static Operand emptyOperand = new Operand(Constants.EMPTY_OPERAND_NAME, OperandTypes.Null, 0);
		
		// 初始化
		public Operand(String opeName, int pos) {
			this(opeName, null, pos);
		}
		public Operand(String opeName, OperandTypes type, int pos) {
			this.opeName = opeName;
			this.type = type;
			this.pos = pos;
			operands = null;
			next = null;			
		}
		public Operand(Operand ope, int pos) {
			this(ope.opeName, ope.type, pos);
			this.operands = ope.operands;
			this.next = ope.next;
		}
		
		// 添加操作数
		public void addOperand(Operand operand) {
			if(operands == null) {
				operands = new ArrayList<>(Constants.OPERAND_DEFAULT_CAP);
			}
			operands.add(operand);
		}
		public void name(String other) {
			opeName = other;
		}
		public String name() {
			return opeName;
		}
		public void type(OperandTypes type) {
			this.type = type;
		}
		public OperandTypes type() {
			return type;
		}
		public int pos() {
			return pos;
		}
		public void setNext(Operand operand) {
			next = operand;
		}
		public boolean hasNext() {
			return next != null;
		}
		public Operand next() {
			return next;
		}
		public List<Operand> operands() {
			if(operands == null) {
				return null;
			}
			return Collections.unmodifiableList(operands);
		}
		public Operand operand(int idx) {
			if((operands == null) || (idx < 0) || (idx >= operands.size()) ) {
				return null;
			}
			return operands.get(idx);
		}
		
		// for debug 
		public String toString() {
			return new net.sf.json.JSONObject().element("name", name() ).element("type", type).element("operands", String.valueOf(operands) ).element("next", String.valueOf(next) ).toString();	
		}
	}
	
	// 各个方法的可能返回类型
	public enum Types {
		String(false), Int(true), Boolean(true), Null(false);
		
		// 之后是否不能存在下一个链式调用[Int, Boolean 不行]
		public boolean isFinal;
		
		// 初始化
		private Types(boolean isFinal) {
			this.isFinal = isFinal;
		}
	}
	// 各个操作数的类型
	enum OperandTypes {
		Method, String, Boolean, Int, Null;
	}
	
}

