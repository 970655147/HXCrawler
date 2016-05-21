/**
 * file name : StandardHandlerParser.java
 * created at : 1:07:25 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hx.crawler.attrHandler.adapter.OneBooleanResultHandlerArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.OneStringResultHandlerArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.StringTwoIntResultHandlerArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.ThreeStringResultHandler;
import com.hx.crawler.attrHandler.adapter.ThreeStringTwoBooleanResultHandler;
import com.hx.crawler.attrHandler.adapter.TwoStringIntResultHandlerArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.TwoStringResultHandlerArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.MultiArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.OneBooleanArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.OneOrTwoStringIntArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.StringOneOrTwoIntAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.ThreeStringTwoBooleanArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.TwoOrThreeStringArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.TwoStringArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.attrHandler.interf.HandlerParser;
import com.hx.crawler.attrHandler.operation.AssertOperationAttrHandler;
import com.hx.crawler.attrHandler.operation.CompositeOperationAttrHandler;
import com.hx.crawler.attrHandler.operation.FilterOperationAttrHandler;
import com.hx.crawler.attrHandler.operation.MapOperationAttrHandler;
import com.hx.crawler.attrHandler.operation.interf.OperationAttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawler.util.WordsSeprator;

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
		handlerToResultType.put(Constants.TO_STRING, Types.String);
		handlerToResultType.put(Constants.GET_STR_IN_RANGE, Types.String);
		
		handlerToResultType.put(Constants.INDEX_OF, Types.Int);
		handlerToResultType.put(Constants.LAST_INDEX_OF, Types.Int);
		handlerToResultType.put(Constants.LENGTH, Types.Int);
		handlerToResultType.put(Constants.ADD, Types.Int);
		handlerToResultType.put(Constants.SUB, Types.Int);
		handlerToResultType.put(Constants.MUL, Types.Int);
		handlerToResultType.put(Constants.DIV, Types.Int);
		handlerToResultType.put(Constants.MOD, Types.Int);
		handlerToResultType.put(Constants.TO_INT, Types.Int);
		
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
		handlerToResultType.put(Constants.TO_BOOLEAN, Types.Boolean);
	}
	
	// 没有参数的方法, 一个字符串参数的方法, 两个字符串参数的方法, 多种选择的参数的方法
	private final static Set<String> noneOrStringArgsMap = new HashSet<>();
	private final static Set<String> oneBooleanArgsMap = new HashSet<>();
	private final static Set<String> oneOrTwoStringArgsMap = new HashSet<>();
	private final static Set<String> stringOneOrTwoIntArgsMap = new HashSet<>();
	private final static Set<String> twoOrThreeStringArgsMap = new HashSet<>();
	private final static Set<String> oneOrTwoStringIntArgsMap = new HashSet<>();
	private final static Set<String> oneBooleanTwoStringArgsMap = new HashSet<>();
	private final static Set<String> multiStringArgsMap = new HashSet<>();
	private final static Set<String> multiBooleanArgsMap = new HashSet<>();
	private final static Set<String> multiIntArgsMap = new HashSet<>();
	private final static Set<String> twoOrThreeStringTwoBooleanArgsMap = new HashSet<>();
	private final static Set<String> noneOrOneStringOneOrTwoIntArgsMap = new HashSet<>();
	static {
		// toUpperCase, toUpperCase()
		noneOrStringArgsMap.add(Constants.TO_UPPERCASE);
		noneOrStringArgsMap.add(Constants.TO_LOWERCASE);
		noneOrStringArgsMap.add(Constants.LENGTH);
		noneOrStringArgsMap.add(Constants.DO_NOTHING);
		noneOrStringArgsMap.add(Constants.TO_INT);
		noneOrStringArgsMap.add(Constants.TO_BOOLEAN);
		noneOrStringArgsMap.add(Constants.TO_STRING);
		
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
		stringOneOrTwoIntArgsMap.add(Constants.SUB_STRING);
		
		// replace(regex, replacement)
		twoOrThreeStringArgsMap.add(Constants.REPLACE);
		
		// add(arg01, arg02)
		multiIntArgsMap.add(Constants.ADD);
		multiIntArgsMap.add(Constants.SUB);
		multiIntArgsMap.add(Constants.MUL);
		multiIntArgsMap.add(Constants.DIV);
		multiIntArgsMap.add(Constants.MOD);
		
		// indexOf('abc'), indexOf('abc', 3)
		oneOrTwoStringIntArgsMap.add(Constants.INDEX_OF);
		oneOrTwoStringIntArgsMap.add(Constants.LAST_INDEX_OF);
		
		// condExp(true, truePart, falsePart), true ? truePart : falsePart
		oneBooleanTwoStringArgsMap.add(Constants.COND_EXP);
		
		// concate('abc', 'def', 'dd', ..), 'abc' + 'def' + 'dd', ..
		multiStringArgsMap.add(Constants.CONCATE);
		
		// and(true, true), true & true
		multiBooleanArgsMap.add(Constants.CUTTING_OUT_AND);
		multiBooleanArgsMap.add(Constants.CUTTING_OUT_OR);
		
		// getStrIn('abc', 'def')
		twoOrThreeStringTwoBooleanArgsMap.add(Constants.GET_STR_IN_RANGE);
		
		// trim, trim(), trim(2), trim('abc', 1), trim(2, 2), trim('acsdf', 1, 1)
		noneOrOneStringOneOrTwoIntArgsMap.add(Constants.TRIM);
		noneOrOneStringOneOrTwoIntArgsMap.add(Constants.TRIM_AS_ONE);
		noneOrOneStringOneOrTwoIntArgsMap.add(Constants.TRIM_ALL);
	}
	
	// -------------------- business method ----------------------------------
	// 各个分隔符的位置
	public OperationAttrHandler handlerParse(String handlerStr, String handlerType, Types lastOperationReturn) {
		CompositeOperationAttrHandler<OperationAttrHandler> compositeOperationAttrHandler = new CompositeOperationAttrHandler<>();
		handlerStr = Tools.trimAllSpaces(handlerStr, Constants.escapeCharMap);
		String lastOperationType = null;
		List<String> suppertedOperations = Constants.handlerTypeToHandleOperations.get(handlerType);
		
		WordsSeprator sep = new WordsSeprator(handlerStr, Constants.handlerParserSeps, Constants.escapeMap, true);
		while(sep.hasNext() ) {
			if(lastOperationReturn != null) {
				if(lastOperationReturn.isFinal ) {
					Tools.assert0("the operation : '" + lastOperationType + "' is final operation, can't take more operation !  around : " + sep.rest() );
				}
			}
			lastOperationType = sep.next();
			Tools.assert0(suppertedOperations.contains(lastOperationType), "have no this opearnd : '" + lastOperationType + "', in operation : '" + handlerType 
							+ "' ! from now on support : " + Constants.handlerTypeToHandleOperations.get(handlerType).toString() );
			String bracket = sep.next();
			Tools.assert0(Constants.bracketPair.containsKey(bracket), "expect a bracket : '" + bracket + "' ! , please check your format['" + sep.rest() + "'] ! ");
			Operand handlerOperand = getAttrHandlerContent(sep, bracket, Constants.bracketPair);
			lastOperationReturn = checkHandlerContent(sep, handlerOperand );
			checkHandler(lastOperationType, lastOperationReturn);
			OperationAttrHandler handlerNow = getOperationAttrHandler(lastOperationType, getAttrHandler(sep, handlerOperand), lastOperationReturn, compositeOperationAttrHandler );
			lastOperationReturn = handlerNow.operationReturn();
			compositeOperationAttrHandler.addHandler(handlerNow);
//			Log.log(handlerOperand );
			
			String dotOrNull = sep.next();
			if((dotOrNull == null) ) {
				break ;
			}
			Tools.assert0(".".equals(dotOrNull), "expect a dot : '.' ! , please check your format['" + sep.rest() + "'] ! ");
		}
		
		return compositeOperationAttrHandler;
	}
	public OperationAttrHandler handlerParse(String handlerStr, String handlerType) {
		return handlerParse(handlerStr, handlerType, null);
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
		if((attrHandlerContent == null) || (OperandTypes.Null == attrHandlerContent.type()) ) {
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
				Types param = checkHandlerContent(sep, operand.operand(0));
				boolean isValid = (operand.operands() == null) 
								|| ( (operand.operands().size() == 1) 
									 && (emptyOperand(operand.operand(0))) || (stringAble(param)) );
				Tools.assert0(isValid, "the operand : '" + operand.name() + "' take ([String]), 'no parameter or String', but got (" + param + ") please check it ! around : " + sep.rest(operand.pos()) );
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
			} else if(twoOrThreeStringArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				Types param03 = checkHandlerContent(sep, operand.operand(2));
				boolean isValid = (operand.operands() != null) && ((stringAble(param01)) && (stringAble(param02)) );
				if(isValid) {
					// (str, str, str)
					if(Types.Null != param03) {
						isValid = stringAble(param03);
					}
				}
				// else (str, str)
//						&& (operand.operands().size() == 2)
//						&& (stringAble(param01) && stringAble(param02) );
				assert0(isValid, operand.name(), "String, String[, String]", operand.operands().size(),  (param01 + ", " + param02 + ", " + param03), sep.rest(operand.pos()) );
			} else if(oneOrTwoStringIntArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				Types param03 = checkHandlerContent(sep, operand.operand(2));
				boolean isValid = (operand.operands() != null) && (stringAble(param01));
				if(isValid) {
					if(Types.Null != param02) {
						// (str, int)
						if(Types.Int == param02) {
							isValid = stringAble(param02);
						// (str, str) or (str, str, int)
						} else {
							if(Types.Null != param03) {
								isValid = (stringAble(param02) && (Types.Int == param03) );
							} else {
								isValid = stringAble(param02);
							}
						}
					}
					// else -> (str)
				}
				assert0(isValid, operand.name(), "String[, String, Int]", operand.operands().size(),  (param01 + ", " + param02 + ", " + param03), sep.rest(operand.pos()) );
			} else if(stringOneOrTwoIntArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				Types param03 = checkHandlerContent(sep, operand.operand(2));
				boolean isValid = (operand.operands() != null);
				if(isValid) {
					// (int, int) or (int)
					if(Types.Int == param01) {
						if(Types.Null != param02) {
							isValid = (Types.Int == param02);				
						}
					// (string, int, int) or (string, int)
					} else {
						isValid = (stringAble(param01) && (Types.Int == param02) 
								&& ((Types.Null == param03) || (Types.Int == param03) ) );
					}
				}
						
				assert0(isValid, operand.name(), "[String ,]Int[, Int]", operand.operands().size(),  (param01 + ", " + param02 + ", ..."), sep.rest(operand.pos()) );
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
			} else if(twoOrThreeStringTwoBooleanArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				Types param03 = checkHandlerContent(sep, operand.operand(2));
				Types param04 = checkHandlerContent(sep, operand.operand(3));
				Types param05 = checkHandlerContent(sep, operand.operand(4));
				boolean isValid = (operand.operands() != null)
						&& (stringAble(param01) && stringAble(param02) );
				if(isValid) {
					if(Types.Null != param03) {
						// (str, str, boolean, boolean)
						if(Types.Boolean == param03) {
							isValid = ((Types.Boolean == param03) && (Types.Boolean == param04) ); 
						// (str, str, str, boolean, boolean) or (str, str, str)
						} else {
							// (str, str, str, boolean, boolean)
							if(Types.Boolean == param04) {
								isValid = ((stringAble(param03)) && (Types.Boolean == param04) && (Types.Boolean == param05) );
							// (str, str, str)
							} else {
								isValid = ((stringAble(param03)) && (Types.Null == param04) && (Types.Null == param05) );
							}
						}
					}
					// else -> (str, str)
				}
				assert0(isValid, operand.name(), "String, String, [String, Boolean, Boolean]", operand.operands().size(),  (param01 + ", " + param02 + "," + param03 + ", " + param04 + ", " + param05), sep.rest(operand.pos()) );
			} else if(noneOrOneStringOneOrTwoIntArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				Types param03 = checkHandlerContent(sep, operand.operand(2));
				boolean isValid = (operand.operands() != null);
				if(isValid) {
					if(Types.Null != param01) {
						// (str) or (str, int) or (str, int, int)
						if(Types.String == param01) {
							if(Types.Null != param02) {
								if(Types.Null == param03) {
									isValid = (Types.Int == param02 );
								} else {
									isValid = ((Types.Int == param02) && (Types.Int == param03) );
								}
							}
						// (int), (int, int)
						} else {
							if(Types.Null != param02) {
								isValid = (Types.Int == param02 );
							}
						}
					}
					// else -> trim, trim()
				}
				assert0(isValid, operand.name(), "[String, Int, Int]", operand.operands().size(),  (param01 + ", " + param02 + "," + param03), sep.rest(operand.pos()) );
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
		// replace code from : 32 -> 1			-- add at 2016.05.21
//		// indicate pattern likes : 'abc', 12, true [so just 'new ConstantsAttrHandler']
//		// do not place here, it will truncate 'generateAttrHandler', just doNoting, there are 'ConstantsAttrHandler' for 'faultTolerant'[and for this business] 
//		if(stringAble(attrHandler.type()) ) {
//			return new ConstantsAttrHandler(attrHandler.name() );
//		}
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
			} else if(twoOrThreeStringArgsMap.contains(attrHandler.name()) ) {
				return getTwoOrThreeStringArgsHandler(sep, attrHandler);
			} else if(oneOrTwoStringIntArgsMap.contains(attrHandler.name()) ) {
				return getOneOrTwoStringIntArgsHandler(sep, attrHandler);
			} else if(stringOneOrTwoIntArgsMap.contains(attrHandler.name()) ) {
				return getStringOneOrTwoIntArgsHandler(sep, attrHandler);
			} else if(oneBooleanTwoStringArgsMap.contains(attrHandler.name()) ) {
				return getOneBooleanTwoStringArgsHandler(sep, attrHandler);
			} else if(multiStringArgsMap.contains(attrHandler.name()) ) {
				return getMultiStringArgsHandler(sep, attrHandler);
			} else if(multiBooleanArgsMap.contains(attrHandler.name()) ) {
				return getMultiBooleanArgsHandler(sep, attrHandler);
			} else if(multiIntArgsMap.contains(attrHandler.name()) ) {
				return getMultiIntArgsHandler(sep, attrHandler);
			} else if(twoOrThreeStringTwoBooleanArgsMap.contains(attrHandler.name()) ) {
				return getTwoOrThreeStringTwoBooleanArgsHandler(sep, attrHandler);
			} else if(noneOrOneStringOneOrTwoIntArgsMap.contains(attrHandler.name()) ) {
				return getNoneOrOneStringOneOrTwoIntArgsHandler(sep, attrHandler);
			} else {
				// recorvey as 'ConstantsStringValue'
				return new ConstantsAttrHandler(attrHandler.name() );
			}
		}
	private OperationAttrHandler getOperationAttrHandler(String operationType, AttrHandler attrHandler, Types operationReturn, CompositeOperationAttrHandler<OperationAttrHandler> compositeOperationAttrHandler ) {
		switch(operationType) {
			case Constants.MAP_OPERATION:
				return new MapOperationAttrHandler(attrHandler, operationReturn);
			case Constants.FILTER_OPERATION:
				return new FilterOperationAttrHandler(attrHandler, compositeOperationAttrHandler.lastReturnType() );
			case Constants.ASSERT_OPERATION:
				return new AssertOperationAttrHandler(attrHandler, compositeOperationAttrHandler.lastReturnType() );
			default:
				Tools.assert0("have no this operationType ! from now on support : " + Constants.handlerTypeToHandleOperations.keySet().toString() );
				break;
		}
		
		return null;
	}
	// 校验当前handler
	private void checkHandler(String operationType, Types returnType) {
		switch (operationType ) {
			case Constants.MAP_OPERATION:
				
				break;
			case Constants.FILTER_OPERATION:
				Tools.assert0(Types.Boolean == returnType, "operation : 'filter' just support (Boolean) as parameter ! but got : (" + returnType + ")" );
				break;
			case Constants.ASSERT_OPERATION:
				Tools.assert0(Types.Boolean == returnType, "operation : 'assert' just support (Boolean) as parameter ! but got : (" + returnType + ")" );
				break;
			default:
				Tools.assert0("have no this operationType ! from now on support : " + Constants.handlerTypeToHandleOperations.keySet().toString() );
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
					|| (isFrom(flags, isFromComp) && (Constants.STRING_CONCATE.equals(sep.seek()) || Constants.AND.equals(sep.seek()) || Constants.OR.equals(sep.seek()) || Constants.COND_EXP_COND.equals(sep.seek()) ) ) 
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
//		case Constants.TRIM:
//			return getNoneOrOneStringArgsHandler0(sep, ope, new TrimAttrHandler() );
//		case Constants.TRIM_AS_ONE:
//			return getNoneOrOneStringArgsHandler0(sep, ope, new TrimAsOneAttrHandler() );
//		case Constants.TRIM_ALL:
//			return getNoneOrOneStringArgsHandler0(sep, ope, new TrimAllAttrHandler() );
		case Constants.TO_UPPERCASE:
			return getNoneOrOneStringArgsHandler0(sep, ope, new ToUpperCaseAttrHandler() );
		case Constants.TO_LOWERCASE:
			return getNoneOrOneStringArgsHandler0(sep, ope, new ToLowerCaseAttrHandler() );
		case Constants.LENGTH:
			return getNoneOrOneStringArgsHandler0(sep, ope, new LengthAttrHandler() );
		case Constants.DO_NOTHING:
			return getNoneOrOneStringArgsHandler0(sep, ope, new DoNothingAttrHandler() );
		case Constants.TO_INT:
			return getNoneOrOneStringArgsHandler0(sep, ope, new ToIntAttrHandler() );
		case Constants.TO_BOOLEAN:
			return getNoneOrOneStringArgsHandler0(sep, ope, new ToBooleanAttrHandler() );
		case Constants.TO_STRING:
			return getNoneOrOneStringArgsHandler0(sep, ope, new ToStringAttrHandler() );
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
				return new OneStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
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
			return new OneBooleanResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
		}
	private AttrHandler getOneOrTwoStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand param01 = attrHandler.operand(0);
		Operand param02 = attrHandler.operand(1);
		switch (attrHandler.name() ) {
			case Constants.EQUALS:
					return getOneOrTwoStringArgsHandler0(sep, param01, param02, new EqualsAttrHandler() );
			case Constants.MATCHES:
					return getOneOrTwoStringArgsHandler0(sep, param01, param02, new MatchesAttrHandler() );
			case Constants.CONTAINS:
				return getOneOrTwoStringArgsHandler0(sep, param01, param02, new ContainsAttrHandler() );
			case Constants.GREATER_THAN:
				return getOneOrTwoStringArgsHandler0(sep, param01, param02, new GreaterThanAttrHandler() );
			case Constants.GREATER_EQUALS_THAN:
				return getOneOrTwoStringArgsHandler0(sep, param01, param02, new GreaterEqualsThanAttrHandler() );
			case Constants.LESS_THAN:
				return getOneOrTwoStringArgsHandler0(sep, param01, param02, new LessThanAttrHandler() );
			case Constants.LESS_EQUALS_THAN:
				return getOneOrTwoStringArgsHandler0(sep, param01, param02, new LessEqualsThanAttrHandler() );
			case Constants._EQUALS:
				return getOneOrTwoStringArgsHandler0(sep, param01, param02, new EqualsAttrHandler() );
			case Constants.NOT_EQUALS:
				return getOneOrTwoStringArgsHandler0(sep, param01, param02, new NotEqualsAttrHandler() );
			default:
				Tools.assert0("got an unknow '(String)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getOneOrTwoStringArgsHandler0(WordsSeprator sep, Operand param01, Operand param02, TwoStringArgsAttrHandler handler) {
			if(param02 == null) {
				return new TwoStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
			} else {
				return new TwoStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02) );
			}
		}
	private AttrHandler getTwoOrThreeStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand param01 = attrHandler.operand(0);
		Operand param02 = attrHandler.operand(1);
		Operand param03 = attrHandler.operand(2);
		switch (attrHandler.name() ) {
			case Constants.REPLACE:
					return getTwoOrThreeStringArgsHandler0(sep, param01, param02, param03, new ReplaceAttrHandler() );
			default :
				Tools.assert0("got an unknow '(String, String)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getTwoOrThreeStringArgsHandler0(WordsSeprator sep, Operand param01, Operand param02, Operand param03, TwoOrThreeStringArgsAttrHandler handler) {
			if(param03 == null) {
				return new ThreeStringResultHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), new ConstantsAttrHandler(param02.name()) );
			} else {
				return new ThreeStringResultHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), getAttrHandler(sep, param03) );
			}
		}
	private AttrHandler getOneOrTwoStringIntArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand param01 = attrHandler.operand(0);
		Operand param02 = attrHandler.operand(1);
		Operand param03 = attrHandler.operand(2);
		switch (attrHandler.name() ) {
			case Constants.INDEX_OF:
				return getStringOrStringIntArgsHandler0(sep, param01, param02, param03, new IndexAttrHandler() );
			case Constants.LAST_INDEX_OF:
				return getStringOrStringIntArgsHandler0(sep, param01, param02, param03, new LastIndexAttrHandler() );
			default :
				Tools.assert0("got an unknow '(String [, Int])' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getStringOrStringIntArgsHandler0(WordsSeprator sep, Operand param01, Operand param02, Operand param03, OneOrTwoStringIntArgsAttrHandler handler) {
			// (str)
			if(param02 == null) {
				return new TwoStringIntResultHandlerArgsAttrHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
			} else {
				// (str, int)
				if(OperandTypes.Int == param02.type() ) {
					return new TwoStringIntResultHandlerArgsAttrHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), getAttrHandler(sep, param02) );
				} else {
					// (str, str, int)
					if(param03 != null) {
						return new TwoStringIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), getAttrHandler(sep, param03) );
					// (str, str)
					} else {
						return new TwoStringIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
					}
				}
			}
		}
	private AttrHandler getStringOneOrTwoIntArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand param01 = attrHandler.operand(0);
		Operand param02 = attrHandler.operand(1);
		Operand param03 = attrHandler.operand(2);
		switch (attrHandler.name() ) {
			case Constants.SUB_STRING:
				return getStringOneOrTwoIntArgsHandler0(sep, param01, param02, param03, new SubStringAttrHandler() );
			default :
				Tools.assert0("got an unknow '([String ,]Int [, Int])' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getStringOneOrTwoIntArgsHandler0(WordsSeprator sep, Operand param01, Operand param02, Operand param03, StringOneOrTwoIntAttrHandler handler) {
			// (str, int, int)
			if(param03 != null) {
				return new StringTwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), getAttrHandler(sep, param03) );
			} else {
				// (str, int)
				if(OperandTypes.String == param01.type() ) {
					return new StringTwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
				} else {
					// (int)
					if(param02 == null ) {
						return new StringTwoIntResultHandlerArgsAttrHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
					// (int, int)						
					} else {
						return new StringTwoIntResultHandlerArgsAttrHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), getAttrHandler(sep, param02) );
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
					return new CondExpAttrHandler(getAttrHandler(sep, ope), getAttrHandler(sep, ope01), getAttrHandler(sep, ope02) );
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
		private AttrHandler getMultiStringArgsHandler0(WordsSeprator sep, Operand attrHandler, MultiArgsAttrHandler<AttrHandler> handler) {
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
				return getMultiBooleanArgsHandler0(sep, attrHandler, new CuttingOutAndAttrHandler<AttrHandler>(attrHandler.operands().size()) );
			case Constants.CUTTING_OUT_OR :
				return getMultiBooleanArgsHandler0(sep, attrHandler, new CuttingOutOrAttrHandler<AttrHandler>(attrHandler.operands().size()) );
			default :
				Tools.assert0("got an unknow '(String, String, ...)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getMultiBooleanArgsHandler0(WordsSeprator sep, Operand attrHandler, MultiArgsAttrHandler<AttrHandler> handler) {
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
		switch (attrHandler.name() ) {
			case Constants.ADD:
					return getMultiIntArgsHandler0(sep, attrHandler, new AddAttrHandler() );
			case Constants.SUB:
				return getMultiIntArgsHandler0(sep, attrHandler, new SubAttrHandler() );						
			case Constants.MUL:
				return getMultiIntArgsHandler0(sep, attrHandler, new MultiplyAttrHandler() );						
			case Constants.DIV:
				return getMultiIntArgsHandler0(sep, attrHandler, new DivAttrHandler() );						
			case Constants.MOD:
				return getMultiIntArgsHandler0(sep, attrHandler, new ModAttrHandler() );						
			default :
				Tools.assert0("got an unknow '(Int, Int)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getMultiIntArgsHandler0(WordsSeprator sep, Operand attrHandler, MultiArgsAttrHandler<AttrHandler> handler) {
			for(Operand operand : attrHandler.operands()) {
				if(OperandTypes.Int == operand.type() ) {
					handler.addHandler(new ConstantsAttrHandler(operand.name()) );
				} else {
					handler.addHandler(getAttrHandler(sep, operand) );
				}
			}
			
			return handler;
		}			
	private AttrHandler getTwoOrThreeStringTwoBooleanArgsHandler(WordsSeprator sep, Operand attrHandler) {
		switch (attrHandler.name() ) {
			case Constants.GET_STR_IN_RANGE:
					return getTwoOrThreeStringTwoBooleanArgsHandler0(sep, attrHandler, new GetStrInRangeHandler() );
			default :
				Tools.assert0("got an unknow '(String, String[, String, Boolean, Boolean])' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getTwoOrThreeStringTwoBooleanArgsHandler0(WordsSeprator sep, Operand attrHandler, ThreeStringTwoBooleanArgsAttrHandler handler) {
			Operand param01 = attrHandler.operand(0);
			Operand param02 = attrHandler.operand(1);
			Operand param03 = attrHandler.operand(2);
			Operand param04 = attrHandler.operand(3);
			Operand param05 = attrHandler.operand(4);
			// (str, str)
			if(null == param03) {
				return new ThreeStringTwoBooleanResultHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), getAttrHandler(sep, param02) );
			} else {
				// (str, str, boolean, boolean)
				// (param03 != null) && 
				if(OperandTypes.Boolean == param03.type() ) {
					return new ThreeStringTwoBooleanResultHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), getAttrHandler(sep, param02), getAttrHandler(sep, param03), getAttrHandler(sep, param04) );
				// (str, str, str, boolean, boolean) or (str, str, str)
				} else {
					// (str, str, str, boolean, boolean)
					if((param04 != null) && (OperandTypes.Boolean == param04.type()) ) {
						return new ThreeStringTwoBooleanResultHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), getAttrHandler(sep, param03), getAttrHandler(sep, param04), getAttrHandler(sep, param05) );
					// (str, str, str)
					} else {
						return new ThreeStringTwoBooleanResultHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), getAttrHandler(sep, param03) );
					}
				}
			}
		}
	private AttrHandler getNoneOrOneStringOneOrTwoIntArgsHandler(WordsSeprator sep, Operand attrHandler) {
		switch (attrHandler.name() ) {
			case Constants.TRIM:
					return getNoneOrOneStringOneOrTwoIntArgsHandler0(sep, attrHandler, new TrimAttrHandler() );
			case Constants.TRIM_AS_ONE:
				return getNoneOrOneStringOneOrTwoIntArgsHandler0(sep, attrHandler, new TrimAsOneAttrHandler() );
			case Constants.TRIM_ALL:
				return getNoneOrOneStringOneOrTwoIntArgsHandler0(sep, attrHandler, new TrimAllAttrHandler() );
			default :
				Tools.assert0("got an unknow '([String, Int, Int])' method : " + attrHandler.name() );
				break ;
		}
	
		return null;
	}
	private AttrHandler getNoneOrOneStringOneOrTwoIntArgsHandler0(WordsSeprator sep, Operand attrHandler, StringOneOrTwoIntAttrHandler handler) {
		Operand param01 = attrHandler.operand(0);
		Operand param02 = attrHandler.operand(1);
		Operand param03 = attrHandler.operand(2);
		// ()
		if((null == param01) || (emptyOperand(param01)) ) {
			return new StringTwoIntResultHandlerArgsAttrHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
		} else {
			if(param03 != null) {
				return new StringTwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), getAttrHandler(sep, param03) );
			} else {
				if(OperandTypes.String == param01.type() ) {
					if(param02 != null) {
						return new StringTwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), getAttrHandler(sep, param02), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
					} else {
						return new StringTwoIntResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, param01), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
					}
				} else {
					if(param02 != null) {
						return new StringTwoIntResultHandlerArgsAttrHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), getAttrHandler(sep, param02) );
					} else {
						return new StringTwoIntResultHandlerArgsAttrHandler(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), getAttrHandler(sep, param01), new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
					}
				}
			}
		}
	}
	// 判断给定的类型是否是Method
	private boolean stringAble(OperandTypes type) {
		return type == OperandTypes.String || type == OperandTypes.Int || type == OperandTypes.Boolean;
	}
	private boolean stringAble(Types type) {
		return type == Types.String || type == Types.Int || type == Types.Boolean;
	}
	private boolean emptyOperand(Operand operand) {
		return Constants.EMPTY_OPERAND_NAME.equals(operand.name());
	}
	// 判断给定的flag中是否存在对应的位
	private boolean isFrom(int flag, int mask) {
		return (flag & mask) != 0; 
	}
	// 获取一个sepToPos
//	private Map<String, Integer> newSepToPos() {
//		Map<String, Integer> sepToPos = new HashMap<>();
//		Iterator<String> it = Constants.handlerParserSeps.iterator();
//		while(it.hasNext() ) {
//			sepToPos.put(it.next(), 0);
//		}
//		return sepToPos;
//	}
	
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

