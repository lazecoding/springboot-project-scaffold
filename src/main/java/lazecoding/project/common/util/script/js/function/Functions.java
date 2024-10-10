package lazecoding.project.common.util.script.js.function;

import lazecoding.project.common.util.script.js.Languages;

/**
 * 定义函数

 * MD5
 *
 * @author lazecoding
 */

public enum Functions {

    // Math 数学函数
    ABS("ABS", "绝对值", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function ABS(number) {\n" +
                    "  return Math.abs(number);\n" +
                    "}",
            "返回数的绝对值"),
    AVERAGE("AVERAGE", "平均值", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function AVERAGE(...numbers) {\n" +
                    "  let sum = 0;\n" +
                    "  for (let number of numbers) {\n" +
                    "    sum += number;\n" +
                    "  }\n" +
                    "  return sum / numbers.length;\n" +
                    "}",
            "计算平均值"),
    FIXED("FIXED", "固定小数位数", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function FIXED(number, decimalPlaces) {\n" +
                    "  const factor = Math.pow(10, decimalPlaces);\n" +
                    "  return Math.floor(number * factor) / factor;\n" +
                    "}",
            "格式化数值为固定小数位数的文本"),
    INT("INT", "整数部分", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function INT(number) {\n" +
                    "  return Math.floor(number);\n" +
                    "}",
            "返回数的整数部分"),
    MAX("MAX", "最大值", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function MAX(...numbers) {\n" +
                    "  return Math.max(...numbers);\n" +
                    "}",
            "返回最大值"),
    MIN("MIN", "最小值", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function MIN(...numbers) {\n" +
                    "  return Math.min(...numbers);\n" +
                    "}",
            "返回最小值"),
    SUM("SUM", "求和", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function SUM(...numbers) {\n" +
                    "  return numbers.reduce((sum, number) => sum + number, 0);\n" +
                    "}",
            "计算数值的总和"),
    POWER("POWER", "乘方", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function POWER(number, power) {\n" +
                    "  return number ** power;\n" +
                    "}",
            "返回数的指定次方"),
    PRODUCT("PRODUCT", "乘积", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function PRODUCT(...numbers) {\n" +
                    "  return numbers.reduce((product, number) => product * number, 1);\n" +
                    "}",
            "返回所有数的乘积"),
    ROUND("ROUND", "四舍五入", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function ROUND(number, numDigits) {\n" +
                    "  const factor = Math.pow(10, numDigits);\n" +
                    "  return Math.round(number * factor) / factor;\n" +
                    "}",
            "将数四舍五入到指定的位数"),
    RAND("RAND", "随机数", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function RAND() {\n" +
                    "  return Math.random();\n" +
                    "}",
            "生成随机数"),
    PI("PI", "圆周率", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function PI() {\n" +
                    "  return Math.PI;\n" +
                    "}",
            "返回 π 的值"),
    MOD("MOD", "求余", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function MOD(number, divisor) {\n" +
                    "  return number % divisor;\n" +
                    "}",
            "返回除法余数"),
    // String 字符串函数
    CONTAIN("CONTAIN", "包含", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function CONTAIN(text1, text2) {\n" +
                    "  return text1.includes(text2);\n" +
                    "}",
            "检查一个字符串是否包含另一个字符串"),
    EXACT("EXACT", "完全匹配", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function EXACT(text1, text2) {\n" +
                    "  return text1 === text2;\n" +
                    "}",
            "检查两个字符串是否完全相同"),
    LEN("LEN", "长度", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function LEN(text) {\n" +
                    "  return text.length;\n" +
                    "}",
            "返回字符串的长度"),
    LOWER("LOWER", "小写", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function LOWER(text) {\n" +
                    "  return text.toLowerCase();\n" +
                    "}",
            "将字符串转换为小写"),
    UPPER("UPPER", "大写", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function LOWER(text) {\n" +
                    "  return text.toUpperCase();\n" +
                    "}",
            "将字符串转换为大写"),
    REPLACE("REPLACE", "替换文本", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function REPLACE(oldText, startNum, numChars, newText) {\n" +
                    "  if (startNum < 1 || startNum > oldText.length) {\n" +
                    "    return oldText; // 如果开始位置不合法，返回原字符串\n" +
                    "  }\n" +
                    "  const start = startNum - 1; // 将1-based索引转换为0-based索引\n" +
                    "  const end = start + numChars;\n" +
                    "  return oldText.substring(0, start) + newText + oldText.substring(end);\n" +
                    "}",
            "在字符串中替换文本"),
    TEXTREPLACE("REPLACE", "文本替换", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function TEXTREPLACE(text, search, replacement) {\n" +
                    "  return text.replace(new RegExp(search, 'g'), replacement);\n" +
                    "}",
            "在字符串中查找并替换文本"),
    SPLIT("SPLIT", "分割", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function SPLIT(text, textSeparator) {\n" +
                    "  return text.split(textSeparator);\n" +
                    "}",
            "根据指定的分隔符分割字符串"),
    STARTWITH("STARTWITH", "以开始", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function STARTWITH(text1, text2) {\n" +
                    "  return text1.startsWith(text2);\n" +
                    "}",
            "检查字符串是否以特定文本开始"),
    TRIM("TRIM", "去空格", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function TRIM(text) {\n" +
                    "  return text.trim();\n" +
                    "}",
            "移除字符串两端的空白字符"),
    JOIN("JOIN", "连接", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function JOIN(delimiter, ...args) {\n" +
                    " return args.join(delimiter);\n" +
                    "}",
            "将多个字符串元素联合成一个字符串"),
    NUMBERTOSTRING("NUMBERTOSTRING", "数值转字符串", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function NUMBERTOSTRING(number) {\n" +
                    "  return number.toString();\n" +
                    "}",
            "数值转字符串"),
    STRINGTONUMBER("STRINGTONUMBER", "字符串转数值", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function STRINGTONUMBER(string) {\n" +
                    "  return Number(string);\n" +
                    "}",
            "字符串转数值"),
    GETUUID("GETUUID", "获取UUID", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "getUuid",
            "获取UUID"),
    GETSIMPLEUUID("GETSIMPLEUUID", "获取简明UUID", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "getSimpleUuid",
            "获取简明UUID"),
    MD5("MD5", "MD5", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "md5",
            "MD5"),
    SM3("SM3", "SM3", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "sm3",
            "SM3"),
    // JSON
    GET("GET", "获取某个路径的值", FunctionGroups.JSON.getGroup(), Languages.JS,
            "function GET(jsonPath, jsonObj) {\n" +
                    "  // 分解JSON路径\n" +
                    "  const keys = jsonPath.split('.');\n" +
                    "  // 逐级访问JSON对象\n" +
                    "  let currentObject = jsonObj;\n" +
                    "  for (const key of keys) {\n" +
                    "    if (currentObject.hasOwnProperty(key)) {\n" +
                    "      currentObject = currentObject[key];\n" +
                    "    } else {\n" +
                    "      return undefined; // 如果路径中的某个键不存在，则返回undefined\n" +
                    "    }\n" +
                    "  }\n" +
                    "  return currentObject;\n" +
                    "}",
            "获取 JSON 中某个路径的值"),
    JSONTOSTRING("JSONTOSTRING", "JSON转字符串", FunctionGroups.JSON.getGroup(), Languages.JS,
            "function JSONTOSTRING(object) {\n" +
                    "  return JSON.stringify(object);\n" +
                    "}",
            "JSON 转字符串"),
    STRINGTOJSON("JSONTOSTRING", "字符串转JSON", FunctionGroups.JSON.getGroup(), Languages.JS,
            "function STRINGTOJSON(jsonString) {\n" +
                    "  try {\n" +
                    "    return JSON.parse(jsonString);\n" +
                    "  } catch (e) {\n" +
                    "    return null;\n" +
                    "  }\n" +
                    "}",
            "字符串转 JSON"),

    // 日期
    DATE("DATE", "DATE", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function DATE(value) {\n" +
                    "  if (typeof value === 'string') {\n" +
                    "    // 处理字符串格式的日期\n" +
                    "    return new Date(value);\n" +
                    "  } else if (typeof value === 'number') {\n" +
                    "    // 处理时间戳（毫秒）\n" +
                    "    return new Date(value);\n" +
                    "  } else {\n" +
                    "    throw new Error('Invalid input type. DATE function accepts either a string or a number.');\n" +
                    "  }\n" +
                    "}",
            "返回当前日期或将文本转换为日期"),
    DATEFORMAT("DATEFORMAT", "日期格式", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function DATEFORMAT(date, format) {\n" +
                    "  if (!(date instanceof Date)) {\n" +
                    "    throw new Error('The first argument must be a Date object.');\n" +
                    "  }\n" +
                    "\n" +
                    "  const year = date.getFullYear();\n" +
                    "  const month = (date.getMonth() + 1).toString().padStart(2, '0');\n" +
                    "  const day = date.getDate().toString().padStart(2, '0');\n" +
                    "  const hour = date.getHours().toString().padStart(2, '0');\n" +
                    "  const minute = date.getMinutes().toString().padStart(2, '0');\n" +
                    "  const second = date.getSeconds().toString().padStart(2, '0');\n" +
                    "\n" +
                    "  return format\n" +
                    "    .replace('yyyy', year)\n" +
                    "    .replace('MM', month)\n" +
                    "    .replace('dd', day)\n" +
                    "    .replace('HH', hour)\n" +
                    "    .replace('mm', minute)\n" +
                    "    .replace('ss', second);\n" +
                    "}",
            "DATEFORMAT(date,format) 将日期格式化为指定类型"),
    NOW("NOW", "当前时间", FunctionGroups.DATE.getGroup(), Languages.JS,
            "",
            ""),
    YEAR("YEAR", "获取年份", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function YEAR(date) {\n" +
                    "  // 如果date是一个日期对象，直接使用它\n" +
                    "  if (date instanceof Date) {\n" +
                    "    return date.getFullYear();\n" +
                    "  }\n" +
                    "  // 如果date是一个字符串或数字（时间戳），先转换成日期对象\n" +
                    "  else {\n" +
                    "    // 创建新的日期对象\n" +
                    "    const validDate = new Date(date);\n" +
                    "    // 检查日期是否有效\n" +
                    "    if (!isNaN(validDate.getTime())) {\n" +
                    "      return validDate.getFullYear();\n" +
                    "    } else {\n" +
                    "      throw new Error('Invalid date');\n" +
                    "    }\n" +
                    "  }\n" +
                    "}",
            "获取时间/时间字符/时间戳年份"),
    MONTH("MONTH", "获取月份", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function MONTH(date) {\n" +
                    "  // 如果date是一个日期对象，直接使用它\n" +
                    "  if (date instanceof Date) {\n" +
                    "    return validDate.getMonth() + 1;\n" +
                    "  }\n" +
                    "  // 如果date是一个字符串或数字（时间戳），先转换成日期对象\n" +
                    "  else {\n" +
                    "    // 创建新的日期对象\n" +
                    "    const validDate = new Date(date);\n" +
                    "    // 检查日期是否有效\n" +
                    "    if (!isNaN(validDate.getTime())) {\n" +
                    "      return validDate.getMonth() + 1;\n" +
                    "    } else {\n" +
                    "      throw new Error('Invalid date');\n" +
                    "    }\n" +
                    "  }\n" +
                    "}",
            "获取时间/时间字符/时间戳月份"),
    DAY("DAY", "获取日期", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function DAY(date) {\n" +
                    "  // 如果date是一个日期对象，直接使用它\n" +
                    "  if (date instanceof Date) {\n" +
                    "    return date.getDate();\n" +
                    "  }\n" +
                    "  // 如果date是一个字符串或数字（时间戳），先转换成日期对象\n" +
                    "  else {\n" +
                    "    // 创建新的日期对象\n" +
                    "    const validDate = new Date(date);\n" +
                    "    // 检查日期是否有效\n" +
                    "    if (!isNaN(validDate.getTime())) {\n" +
                    "      return date.getDate();\n" +
                    "    } else {\n" +
                    "      throw new Error('Invalid date');\n" +
                    "    }\n" +
                    "  }\n" +
                    "}",
            "获取时间/时间字符/时间戳日期"),
    HOUR("HOUR", "获取小时", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function HOUR(date) {\n" +
                    "  // 如果date是一个日期对象，直接使用它\n" +
                    "  if (date instanceof Date) {\n" +
                    "    return date.getHours();\n" +
                    "  }\n" +
                    "  // 如果date是一个字符串或数字（时间戳），先转换成日期对象\n" +
                    "  else {\n" +
                    "    // 创建新的日期对象\n" +
                    "    const validDate = new Date(date);\n" +
                    "    // 检查日期是否有效\n" +
                    "    if (!isNaN(validDate.getTime())) {\n" +
                    "      return validDate.getHours();\n" +
                    "    } else {\n" +
                    "      throw new Error('Invalid date');\n" +
                    "    }\n" +
                    "  }\n" +
                    "}",
            "获取时间/时间字符/时间戳小时"),
    MINUTE("MINUTE", "获取分钟", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function MINUTE(date) {\n" +
                    "  // 如果date是一个日期对象，直接使用它\n" +
                    "  if (date instanceof Date) {\n" +
                    "    return date.getMinutes();\n" +
                    "  }\n" +
                    "  // 如果date是一个字符串或数字（时间戳），先转换成日期对象\n" +
                    "  else {\n" +
                    "    // 创建新的日期对象\n" +
                    "    const validDate = new Date(date);\n" +
                    "    // 检查日期是否有效\n" +
                    "    if (!isNaN(validDate.getTime())) {\n" +
                    "      return validDate.getMinutes();\n" +
                    "    } else {\n" +
                    "      throw new Error('Invalid date');\n" +
                    "    }\n" +
                    "  }\n" +
                    "}",
            "获取时间/时间字符/时间戳分钟"),
    SECOND("SECOND", "获取秒", FunctionGroups.DATE.getGroup(), Languages.JS,
            "function SECOND(date) {\n" +
                    "  // 如果date是一个日期对象，直接使用它\n" +
                    "  if (date instanceof Date) {\n" +
                    "    return date.getSeconds();\n" +
                    "  }\n" +
                    "  // 如果date是一个字符串或数字（时间戳），先转换成日期对象\n" +
                    "  else {\n" +
                    "    // 创建新的日期对象\n" +
                    "    const validDate = new Date(date);\n" +
                    "    // 检查日期是否有效\n" +
                    "    if (!isNaN(validDate.getTime())) {\n" +
                    "      return validDate.getSeconds();\n" +
                    "    } else {\n" +
                    "      throw new Error('Invalid date');\n" +
                    "    }\n" +
                    "  }\n" +
                    "}",
            "获取时间/时间字符/时间戳秒"),


    ;

    /**
     * 函数名
     */
    private final String name;

    /**
     * 中文别名
     */
    private final String alias;

    /**
     * 分组
     */
    private final String group;

    /**
     * 语言类型
     */
    private final String language;

    /**
     * 脚本
     */
    private final String script;

    /**
     * 描述，形如：返回其参数列表中第一个非空值\n:param args: 任意数量的参数\n:return: 第一个非空值
     */
    private final String description;

    Functions(String name, String alias, String group, String language, String script, String description) {
        this.name = name;
        this.alias = alias;
        this.group = group;
        this.language = language;
        this.script = script;
        this.description = description;
    }

    Functions(String name, String alias, String group, String script, String description) {
        this.name = name;
        this.alias = alias;
        this.group = group;
        this.script = script;
        this.description = description;
        this.language = Languages.JS;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getGroup() {
        return group;
    }

    public String getLanguage() {
        return language;
    }

    public String getScript() {
        return script;
    }

    public String getDescription() {
        return description;
    }
}
