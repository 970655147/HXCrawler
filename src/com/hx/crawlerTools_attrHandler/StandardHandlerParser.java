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
import com.hx.crawlerTools_attrHandler.adapter.OneStringResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.StringIntResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.TwoIntResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.TwoStringResultHandlerArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;
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
		
		handlerToResultType.put(Constants.INDEX_OF, Types.Int);
		handlerToResultType.put(Constants.LAST_INDEX_OF, Types.Int);
		handlerToResultType.put(Constants.LENGTH, Types.Int);
		
		handlerToResultType.put(Constants.EQUALS, Types.Boolean);
		handlerToResultType.put(Constants.MATCHES, Types.Boolean);
		handlerToResultType.put(Constants.CONTAINS, Types.Boolean);
	}
	
	// 没有参数的方法, 一个字符串参数的方法, 两个字符串参数的方法, 多种选择的参数的方法
	public final static Set<String> nonArgsMap = new HashSet<>();
	public final static Set<String> oneStringArgsMap = new HashSet<>();
	public final static Set<String> twoStringArgsMap = new HashSet<>();
	public final static Set<String> multiChoiceArgsMap = new HashSet<>();
	static {
		nonArgsMap.add(Constants.TRIM);
		nonArgsMap.add(Constants.TRIM_AS_ONE);
		nonArgsMap.add(Constants.TRIM_ALL);
		nonArgsMap.add(Constants.TO_UPPERCASE);
		nonArgsMap.add(Constants.TO_LOWERCASE);
		nonArgsMap.add(Constants.LENGTH);
		nonArgsMap.add(Constants.DO_NOTHING);
		
		oneStringArgsMap.add(Constants.EQUALS);
		oneStringArgsMap.add(Constants.MATCHES);
		oneStringArgsMap.add(Constants.CONTAINS);
		
		twoStringArgsMap.add(Constants.REPLACE);
		
		multiChoiceArgsMap.add(Constants.INDEX_OF);
		multiChoiceArgsMap.add(Constants.CONCATE);
		multiChoiceArgsMap.add(Constants.LAST_INDEX_OF);
		multiChoiceArgsMap.add(Constants.SUB_STRING);
	}
	
	// -------------------- business method ----------------------------------
	// 各个分隔符的位置
	public AttrHandler handlerParse(String handler) {
		CompositeAttrHandler hanlder = new CompositeAttrHandler();
		handler = Tools.trimAllSpaces(handler, Constants.escapeCharMap);
		WordsSeprator sep = new WordsSeprator(handler, newSepToPos(), Constants.escapeMap, true);
		while(sep.hasNext() ) {
			Tools.assert0(sep.next(), "map", "around : " + sep.rest() );
			String bracket = sep.next();
			Tools.assert0(Constants.bracketPair.containsKey(bracket), "expect a bracket : '" + bracket + "' ! , please check your format['" + sep.rest() + "'] ! ");
			Operand handlerOperand = getAttrHandlerContent(sep, bracket, Constants.bracketPair);
			checkHandlerContent(sep, handlerOperand );
			hanlder.addHandler(getAttrHandler(sep, handlerOperand) );
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
			handlerOperand = getAttrHandlerOperand(sep, bracket, bracketpair, false);
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
			} else if(nonArgsMap.contains(operand.name()) ) {
				boolean isValid = (operand.operands() == null) 
								|| ( (operand.operands().size() == 1) 
									 && (Constants.EMPTY_OPERAND_NAME.equals(operand.operand(0).name())) );
				Tools.assert0(isValid, "the operand : '" + operand.name() + "' take no parameter, please check it ! around : " + sep.rest(operand.pos()) );
			} else if(oneStringArgsMap.contains(operand.name()) ) {
				Types param = checkHandlerContent(sep, operand.operand(0));
				boolean isValid = (operand.operands() != null) 
						&& (operand.operands().size() == 1) && (stringAble(param) ) ;
				assert0(isValid, operand.name(), "String", operand.operands().size(),  (param + ", ..."), sep.rest(operand.pos()) );
			} else if(twoStringArgsMap.contains(operand.name()) ) {
				Types param01 = checkHandlerContent(sep, operand.operand(0));
				Types param02 = checkHandlerContent(sep, operand.operand(1));
				boolean isValid = (operand.operands() != null)
						&& (operand.operands().size() == 2)
						&& stringAble(param01)
						&& stringAble(param02);
				assert0(isValid, operand.name(), "String, String", operand.operands().size(),  (param01 + ", " + param02 + ", ..."), sep.rest(operand.pos()) );
			} else if(multiChoiceArgsMap.contains(operand.name()) ) {
				if((Constants.INDEX_OF.equals(operand.name()) ) || (Constants.LAST_INDEX_OF.equals(operand.name()) ) ) {
					Types param01 = checkHandlerContent(sep, operand.operand(0));
					Types param02 = checkHandlerContent(sep, operand.operand(1));
					boolean isValid = (operand.operands() != null)
							&& ((operand.operands().size() == 1) || (operand.operands().size() == 2) )
							&& stringAble(param01)
							&& (Types.Int == param02 || Types.Null == param02);
					assert0(isValid, operand.name(), "String[, Int]", operand.operands().size(),  (param01 + ", " + param02 + ", ..."), sep.rest(operand.pos()) );
				} else if(Constants.SUB_STRING.equals(operand.name()) ) {
					Types param01 = checkHandlerContent(sep, operand.operand(0));
					Types param02 = checkHandlerContent(sep, operand.operand(1));
					boolean isValid = (operand.operands() != null)
							&& ((operand.operands().size() == 1) || (operand.operands().size() == 2) )
							&& Types.Int == param01
							&& (Types.Int == param02 || Types.Null == param02);
					assert0(isValid, operand.name(), "Int[, Int]", operand.operands().size(),  (param01 + ", " + param02 + ", ..."), sep.rest(operand.pos()) );
				} else if(Constants.CONCATE.equals(operand.name()) ) {
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
				} else {
					// can't got there !
					Tools.assert0("unknow operand : '" + operand.name() + "', please check it !   around : " + sep.rest(operand.pos()) );					
				}
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
			return getAttrHandler0(sep, attrHandler.operand(0) );
		}
		if(nonArgsMap.contains(attrHandler.name()) ) {
			return getNoArgsHandler(sep, attrHandler);
		} else if(oneStringArgsMap.contains(attrHandler.name()) ) {
			return getOneStringArgsHandler(sep, attrHandler );
		} else if(twoStringArgsMap.contains(attrHandler.name()) ) {
			return getTwoStringArgsHandler(sep, attrHandler);
		} else if(multiChoiceArgsMap.contains(attrHandler.name()) ) {
			return getMultiChoiceArgsHandler(sep, attrHandler);
		} else {
			// recorvey as 'ConstantsStringValue'
			return new ConstantsAttrHandler(attrHandler.name() );
		}
	}

	// -------------------- assit method ----------------------------------
	// 获取一个操作数
		// 允许三种情况
		// 1. methodName(param1, param2)
		// 2. str1 + str2
		// 3. trim().subString(1, 3)
		// 4. 'str1' + str2 / subString('sd')
	private Operand getAttrHandlerOperand(WordsSeprator sep, String bracket, Map<String, String> bracketpair, boolean isFromConcate)	{
		if(bracketpair.get(bracket).equals(sep.seek()) ) {
			return Operand.emptyOperand;
		}
		String method = sep.seek();
//		Tools.assert0((allAttrHandler.contains(method) || (sep.seek().equals(Constants.STRING_CONCATE)) || (sep.seek().equals(Constants.PARAM_SEP)) ), "unknow method : " + method + ",  please check your format['" + handler + "'] !" );
		// incase of "map((trim()) )"
		if(bracketpair.containsKey(method) ) {
			method = Constants.ANONY_OPERAND_NAME;
		// common case : "trim()" or "'Hello' + $this"
		} else {
			sep.next();
		}
		Operand operand = new Operand(method, sep.lastNextPos() );
		operand.type(OperandTypes.String);
		// incase of "map(length )", mark length as 'Method'	
		if(nonArgsMap.contains(operand.name()) ) {
			operand.type(OperandTypes.Method);
		}
		
		while(sep.hasNext() ) {
			// 			  		  |idx			   |
			// incase of "(param01, xx)", "(param01)"
			if(Constants.PARAM_SEP.equals(sep.seek()) || (isFromConcate && Constants.STRING_CONCATE.equals(sep.seek())) || bracketpair.get(bracket).equals(sep.seek()) ) {
				return operand;
			}
			
			String next = sep.next();
			//					 |
			// incase of "indexOf($this, abc)"
			if(bracket.equals(next) ) {
				operand.type(OperandTypes.Method);
				Operand ope = getAttrHandlerOperand(sep, bracket, bracketpair, false);
				operand.addOperand(ope );
				while(sep.hasNext() ) {
					String dotCommaOrNot = sep.next();
					if(Constants.SUB_HANDLER_CALL.equals(dotCommaOrNot) ) {
						ope.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, false) );
					} else if(Constants.PARAM_SEP.equals(dotCommaOrNot) ) {
						ope = getAttrHandlerOperand(sep, bracket, bracketpair, false);
						operand.addOperand(ope );
					} else {
						Tools.assert0(bracketpair.get(bracket).equals(dotCommaOrNot), "expect a  : ')' ! , please check your format['" + sep.rest() + "'] ! ");
						break ;
					}
				}
			}
			if(Constants.SUB_HANDLER_CALL.equals(next) ) {
				Operand ope = getAttrHandlerOperand(sep, bracket, bracketpair, false);
				operand.setNext(ope);
			}
			
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
					operand.addOperand(getAttrHandlerOperand(sep, bracket, bracketpair, true) );
				} while(Constants.STRING_CONCATE.equals(sep.seek()) );
			}
		}
		
		return operand;
	}

	// 获取一个没有参数的/ 存在两个字符串参数的/ 存在一个字符串参数的 AttrHandler
	private AttrHandler getNoArgsHandler(WordsSeprator sep, Operand attrHandler) {
		switch (attrHandler.name() ) {
		case Constants.TRIM:
			return new TrimAttrHandler();
		case Constants.TRIM_AS_ONE:
			return new TrimAsOneAttrHandler();
		case Constants.TRIM_ALL:
			return new TrimAllAttrHandler();
		case Constants.TO_UPPERCASE:
			return new ToUpperCaseAttrHandler();
		case Constants.TO_LOWERCASE:
			return new ToLowerCaseAttrHandler();
		case Constants.LENGTH:
			return new LengthAttrHandler();
		case Constants.DO_NOTHING:
			return new DoNothingAttrHandler();
		default:
			Tools.assert0("got an unknow 'noArgs' method : " + attrHandler.name() );
			return null;
		}
	}
	private AttrHandler getOneStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		switch (attrHandler.name() ) {
			case Constants.EQUALS:
				{
					OneStringArgsAttrHandler handler = new EqualsAttrHandler();
					return getOneStringArgsHandler0(sep, ope, handler);
				}
			case Constants.MATCHES:
				{
					OneStringArgsAttrHandler handler = new MatchesAttrHandler();
					return getOneStringArgsHandler0(sep, ope, handler);
				}
			case Constants.CONTAINS:
			{
				OneStringArgsAttrHandler handler = new ContainsAttrHandler();
				return getOneStringArgsHandler0(sep, ope, handler);
			}				
			default:
				Tools.assert0("got an unknow '(String)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
		private AttrHandler getOneStringArgsHandler0(WordsSeprator sep, Operand ope, OneStringArgsAttrHandler handler) {
			if(stringAble(ope.type()) ) {
				return new OneStringResultHandlerArgsAttrHandler(handler, ope.name() );
			} else if(OperandTypes.Method == ope.type() ) {
				return new OneStringResultHandlerArgsAttrHandler(handler, getAttrHandler(sep, ope) );
			} else {
				// OperandTypes.Null
				return null;
			}
		}
	private AttrHandler getTwoStringArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		Operand ope02 = attrHandler.operand(1);
		switch (attrHandler.name() ) {
			case Constants.REPLACE:
				{
					TwoStringArgsAttrHandler handler = new ReplaceAttrHandler();
					return getTwoStringArgsHandler0(sep, ope, ope02, handler);
				}
			default :
				Tools.assert0("got an unknow '(String, String)' method : " + attrHandler.name() );
				break ;
		}
		
		return null;
	}
	private AttrHandler getTwoStringArgsHandler0(WordsSeprator sep, Operand ope, Operand ope02, TwoStringArgsAttrHandler handler) {
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
	private AttrHandler getMultiChoiceArgsHandler(WordsSeprator sep, Operand attrHandler) {
		Operand ope = attrHandler.operand(0);
		switch (attrHandler.name() ) {
			case Constants.INDEX_OF :
				return getStringIntOrStringArgsHandler0(sep, attrHandler, new IndexAttrHandler() );
			case Constants.LAST_INDEX_OF :
				return getStringIntOrStringArgsHandler0(sep, attrHandler, new LastIndexAttrHandler() );
			case Constants.SUB_STRING : 
				{
					Operand ope02 = attrHandler.operand(1);
					TwoIntArgsAttrHandler handler = new SubStringAttrHandler();
					return getTwoIntOrOneArgsHandler0(sep, ope, ope02, handler);
				}
			case Constants.CONCATE :
				ConcateAttrHandler handler = new ConcateAttrHandler(attrHandler.operands().size() );
				for(Operand operand : attrHandler.operands()) {
					if(stringAble(operand.type()) ) {
						handler.addHandler(new ConstantsAttrHandler(operand.name()) );
					} else {
						handler.addHandler(getAttrHandler(sep, operand) );
					}
				}
				return handler;
		}
		
		return null;
	}
		private AttrHandler getStringIntOrStringArgsHandler0(WordsSeprator sep, Operand attrHandler, StringIntArgsAttrHandler handler) {
			Operand ope = attrHandler.operand(0);
			Operand ope02 = attrHandler.operand(1);
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
		private AttrHandler getTwoIntOrOneArgsHandler0(WordsSeprator sep, Operand ope, Operand ope02, TwoIntArgsAttrHandler handler) {
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
	// 判断给定的类型是否是Method
	private boolean stringAble(OperandTypes type) {
		return type == OperandTypes.String || type == OperandTypes.Int;
	}
	private boolean stringAble(Types type) {
		return type == Types.String || type == Types.Int;
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
			if((idx < 0) || (idx >= operands.size()) ) {
				return null;
			}
			return operands.get(idx);
		}
		
		// for debug 
		public String toString() {
			return new net.sf.json.JSONObject().element("name", name() ).element("type", type).element("operands", String.valueOf(operands) ).element("next", String.valueOf(next) ).toString();	
		}
	}
	
	// 各个类型
	enum Types {
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
		Method, String, Int, Null;
	}
	
}

