package lazecoding.project.common.util.script.function;

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
            "ABS(number) 返回数字的绝对值\n" +
                    "用法：ABS(-123.456)\n" +
                    "示例：ABS(-123.456) 的结果为123.456"),
    AVERAGE("AVERAGE", "平均值", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function AVERAGE(...numbers) {\n" +
                    "  let sum = 0;\n" +
                    "  for (let number of numbers) {\n" +
                    "    sum += number;\n" +
                    "  }\n" +
                    "  return sum / numbers.length;\n" +
                    "}",
            "AVERAGE(number1, number2, ...) 求数字的平均值\n" +
                    "用法：AVERAGE(1,2)\n" +
                    "示例：AVERAGE(1,2) 的结果为 1.5"),
    FIXED("FIXED", "固定小数位数", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function FIXED(number, decimalPlaces) {\n" +
                    "  const factor = Math.pow(10, decimalPlaces);\n" +
                    "  return Math.floor(number * factor) / factor;\n" +
                    "}",
            "FIXED(number) 将数字向下舍入到指定的小数位数。\n" +
                    "用法：FIXED(10.8963, 2)\n" +
                    "示例：FIXED(10.8963, 2) 返回的结果是10.89"),
    INT("INT", "整数部分", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function INT(number) {\n" +
                    "  return Math.floor(number);\n" +
                    "}",
            "INT(number)将数字向下舍入到最接近的整数。\n" +
                    "用法：INT(3.45)\n" +
                    "示例：INT(3.45) 返回3；INT(-3.45) 返回-4"),
    MAX("MAX", "最大值", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function MAX(...numbers) {\n" +
                    "  return Math.max(...numbers);\n" +
                    "}",
            "MAX(number1, number2, ...) 获取这组数字中的最大值。\n" +
                    "用法：MAX(1, 4, 6.7, 10, 2)\n" +
                    "示例：MAX(1, 4, 6.7, 10, 2) 返回10"),
    MIN("MIN", "最小值", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function MIN(...numbers) {\n" +
                    "  return Math.min(...numbers);\n" +
                    "}",
            "MIN(number1, number2, ...) 返回数组中的最小值。\n" +
                    "用法：MIN(1, 3, 5, 7, 2, 4)\n" +
                    "示例：MIN(1, 3, 5, 7, 2, 4) 返回1"),
    SUM("SUM", "求和", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function SUM(...numbers) {\n" +
                    "  return numbers.reduce((sum, number) => sum + number, 0);\n" +
                    "}",
            "SUM(number1, number2...) 函数将所有参数求和并返回。集合参数会被自动展开成多个参数\n" +
                    "用法：SUM(1.23, 1.45, 100)\n" +
                    "示例：SUM(1.23, 1.45, 100) 返回102.68; SUM(LIST(1,2,3,4,5)) 的结果是 15"),
    POWER("POWER", "乘方", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function POWER(number, power) {\n" +
                    "  return number ** power;\n" +
                    "}",
            "POWER(number, power) 返回数字乘幂的结果。\n" +
                    "用法：POWER(2, 2)\n" +
                    "示例：POWER(2, 2) 的结果是 4。"),
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
            "ROUND(number, numDigits) 将数字四舍五入到指定的位数。\n" +
                    "用法：ROUND(1.2345, 2)\n" +
                    "示例：ROUND(1.2345, 2) 返回1.23；ROUND(12345, 2) 返回12345"),
    RAND("RAND", "随机数", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function RAND() {\n" +
                    "  return Math.random();\n" +
                    "}",
            "RAND() 返回大于等于 0 且小于 1 的均匀分布随机实数。每一次触发计算都会变化。\n" +
                    "用法：RAND() 的结果是 0.601931207820683。\n" +
                    "示例：RAND() 的结果是 0.601931207820683。"),
    PI("PI", "圆周率", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function PI() {\n" +
                    "  return Math.PI;\n" +
                    "}",
            "PI() 返回圆周率3.14159265358979323846。\n" +
                    "用法：PI() * POWER(r, 2)\n" +
                    "示例：计算半径长为r的圆的面积 PI() * POWER(r, 2) 如果r=1，那么返回3.14159265358979323846"),
    MOD("MOD", "求余", FunctionGroups.MATH.getGroup(), Languages.JS,
            "function MOD(number, divisor) {\n" +
                    "  return number % divisor;\n" +
                    "}",
            "MOD(number, divisor) 返回两数相除的余数。\n" +
                    "用法：MOD(37, 6)\n" +
                    "示例：MOD(37, 6) 返回值为1"),
    // String 字符串函数
    CONTAIN("CONTAIN", "包含", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function CONTAIN(text1, text2) {\n" +
                    "  return text1.includes(text2);\n" +
                    "}",
            "CONTAIN(text1, text2) 判断 text1 是否包含 text2。\n" +
                    "用法：CONTAIN('text1', 'text')\n" +
                    "示例：CONTAIN('text1', 'text') 的结果为 true。"),
    EXACT("EXACT", "完全匹配", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function EXACT(text1, text2) {\n" +
                    "  return text1 === text2;\n" +
                    "}",
            "EXACT(text1, text2) 判断字符串是否完全相等，如果相等，则返回true，如果不相等，则返回false，区分大小写。\n" +
                    "用法：EXACT('abc', 'Abc')\n" +
                    "示例：EXACT('abc', 'Abc') 返回false。"),
    LEN("LEN", "长度", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function LEN(text) {\n" +
                    "  return text.length;\n" +
                    "}",
            "LEN(text) 返回字符串长度。\n" +
                    "用法：LEN('abc')\n" +
                    "示例：LEN('abc') 的结果为 3"),
    LOWER("LOWER", "小写", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function LOWER(text) {\n" +
                    "  return text.toLowerCase();\n" +
                    "}",
            "LOWER(text) 将参数中的所有字母转换成小写字母返回。\n" +
                    "用法：LOWER('AbCd')\n" +
                    "示例：LOWER('AbCd') 的结果是 'abcd'"),
    UPPER("UPPER", "大写", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function UPPER(text) {\n" +
                    "  return text.toUpperCase();\n" +
                    "}",
            "UPPER(text) 将文本字符串中的所有小写字母转换成大写字母。\n" +
                    "用法：UPPER('AbCd')\n" +
                    "示例：UPPER('AbCd') 返回结果是 'ABCD'。"),
    REPLACE("REPLACE", "替换文本", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function REPLACE(oldText, startNum, numChars, newText) {\n" +
                    "  if (startNum < 1 || startNum > oldText.length) {\n" +
                    "    return oldText; // 如果开始位置不合法，返回原字符串\n" +
                    "  }\n" +
                    "  const start = startNum - 1; // 将1-based索引转换为0-based索引\n" +
                    "  const end = start + numChars;\n" +
                    "  return oldText.substring(0, start) + newText + oldText.substring(end);\n" +
                    "}",
            "REPLACE(oldText, startNum, numChars, newText) 将oldText的从startNum开始的numChars个字符替换成newText,startNum从1开始。\n" +
                    "用法：REPLACE('12345678', 2, 3, 'ABCD')\n" +
                    "示例：REPLACE('12345678', 2, 3, 'ABCD') 结果是1ABCD5678"),
    TEXTREPLACE("REPLACE", "文本替换", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function TEXTREPLACE(text, search, replacement) {\n" +
                    "  return text.replace(new RegExp(search, 'g'), replacement);\n" +
                    "}",
            "TEXTREPLACE(string, string, string) 将参数1里与参数2相匹配的字符替换成参数3\n" +
                    "用法：TEXTREPLACE('ABBC','B','b')\n" +
                    "示例：TEXTREPLACE('ABBC','B','b') 返回的结果是 'AbbC'"),
    SPLIT("SPLIT", "分割", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "split",
            "SPLIT(text, textSeparator) 将字符串分割。\n" +
                    "用法：SPLIT('ABABAB', 'B')\n" +
                    "示例：SPLIT('ABABAB', 'B') 的结果为 LIST('A','A','A')。"),
    STARTWITH("STARTWITH", "以开始", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function STARTWITH(text1, text2) {\n" +
                    "  return text1.startsWith(text2);\n" +
                    "}",
            "STARTWITH(text1, text2) 判断文本字符串是否以特定字符串开始。\n" +
                    "用法：STARTWITH('ABCDEF', 'ABC')\n" +
                    "示例：STARTWITH('ABCDEF', 'ABC') 返回true。"),
    TRIM("TRIM", "去空格", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function TRIM(text) {\n" +
                    "  return text.trim();\n" +
                    "}",
            "TRIM(text) 删除字符串首尾的空格。\n" +
                    "用法：TRIM(' ABCD ')\n" +
                    "示例：TRIM(' ABCD ') 返回的结果是 'ABCD'。"),
    JOIN("JOIN", "连接", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function JOIN(delimiter, ...args) {\n" +
                    " return args.join(delimiter);\n" +
                    "}",
            "JOIN(delimiter, list) 将数组列表转换为由delimiter分割的字符串\n" +
                    "用法：GET(',', LIST(1,2,3))\n" +
                    "示例：GET(',', LIST(1,2,3)) 返回的结果是 '1,2,3'"),
    NUMBERTOSTRING("NUMBERTOSTRING", "数值转字符串", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function NUMBERTOSTRING(number) {\n" +
                    "  return number.toString();\n" +
                    "}",
            "数值转字符串\n" +
                    "用法：NUMBERTOSTRING(1)\n" +
                    "示例：NUMBERTOSTRING(1) 返回的结果是 '1'。"),
    STRINGTONUMBER("STRINGTONUMBER", "字符串转数值", FunctionGroups.STRING.getGroup(), Languages.JS,
            "function STRINGTONUMBER(string) {\n" +
                    "  return Number(string);\n" +
                    "}",
            "字符串转数值\n"  +
                    "用法：NUMBERTOSTRING('1')\n" +
                    "示例：NUMBERTOSTRING('1') 返回的结果是 1。"),
    GETUUID("GETUUID", "获取UUID", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "getUuid",
            "获取标准 UUID。\n" +
                    "用法：GETUUID()\n" +
                    "示例：GETUUID() 返回的结果是 '59ce2443-5dbd-4933-bf9d-93e2cde545a9'。"),
    GETSIMPLEUUID("GETSIMPLEUUID", "获取简明UUID", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "getSimpleUuid",
            "获取简明 UUID，即去除 '-'。\n" +
                    "用法：GETUUID()\n" +
                    "示例：GETUUID() 返回的结果是 '59ce24435dbd4933bf9d93e2cde545a9'。"),
    MD5("MD5", "MD5", FunctionGroups.STRING.getGroup(), Languages.JAVA,
            "md5",
            "获取 MD5 值。\n" +
                    "用法：MD5('111')\n" +
                    "示例：MD5('111') 返回的结果是 '698d51a19d8a121ce581499d7b701668'。"),
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
    NOW("NOW", "当前时间戳", FunctionGroups.DATE.getGroup(), Languages.JAVA,
            "currentTimeMillis",
            "获取当前时间戳"),
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

    // Collection
    IN("IN", "存在", FunctionGroups.COLLECTION.getGroup(), Languages.JS,
            "function IN(ele, collection) {\n" +
                    "    return collection.includes(ele);\n" +
                    "}\n",
            "IN(ele,collection) 判断元素是否位于集合中\n" +
                    "用法：IN('选项 1', LIST('选项 1','选项 2'))\n" +
                    "示例：IN('选项 1', LIST('选项 1','选项 2')) 的结果为 true"),
    INTER("INTER", "交集", FunctionGroups.COLLECTION.getGroup(), Languages.JAVA,
            "listInter",
            "INTER(collection1,collection2) 计算两个集合的交集\n" +
                    "用法：INTER(LIST(1,2,3),LIST(2,3,5,6))\n" +
                    "示例：INTER(LIST(1,2,3),LIST(2,3,5,6)) 的结果是 LIST(2,3)"),
    UNION("UNION", "并集", FunctionGroups.COLLECTION.getGroup(), Languages.JAVA,
            "listUnion",
            "UNION(collection1,collection2) 计算两个集合的并集\n" +
                    "用法：UNION(LIST(1,2),LIST(3,4))\n" +
                    "示例：UNION(LIST(1,2),LIST(3,4)) 的结果是 LIST(1,2,3,4)"),
    LIST("LIST", "创建列表", FunctionGroups.COLLECTION.getGroup(), Languages.JAVA,
            "initList",
            "LIST(ele1, ele2, ...) 生成一个由 ele1 ele2 ... 组成的集合\n" +
                    "用法：LIST('选项1','选项2')\n" +
                    "示例：LIST('选项1','选项2')"),
    INDEXVALUE("INDEXVALUE", "指定索引的值", FunctionGroups.COLLECTION.getGroup(), Languages.JAVA,
            "indexValue",
            "INDEXVALUE(list,index) 获取 list index 位置的元素,下标从 0 开始\n" +
                    "用法：INDEXVALUE(LIST(1,2,3),1)\n" +
                    "示例：INDEXVALUE(LIST(1,2,3),2) 的结果为 2"),
    LISTSIZE("LISTSIZE", "元素个数", FunctionGroups.COLLECTION.getGroup(), Languages.JAVA,
            "listSize",
            "LISTSIZE(list) 返回集合的大小\n" +
                    "用法：LISTSIZE(LIST(1,2,3))\n" +
                    "示例：LISTSIZE(LIST(1,2,3)) 的结果为 3"),

    // Logic
    IF("IF", "如果", FunctionGroups.LOGIC.getGroup(), Languages.JS,
            "function IF(logic, value1, value2) {\n" +
                    "    return logic ? value1 : value2;\n" +
                    "}",
            "IF(logic, value1, value2) 如果logic为true，则返回value1， 否则返回value2\n" +
                    "用法：IF(70>=60, '及格', '不及格')\n" +
                    "示例：IF(70>=60, '及格', '不及格') 的结果是 及格"
    ),
    EQ("EQ", "等于", FunctionGroups.LOGIC.getGroup(), Languages.JS,
            "function EQ(value1, value2) {\n" +
                    "    return value1 === value2;\n" +
                    "}",
            "EQ(value1, value2),如果value1和value2的值相等，则为true，否则为false\n" +
                    "用法：EQ('aa', 'aa')\n" +
                    "示例：EQ('aa', 'aa') 和 EQ(1,1) 的结果为 true"
    ),

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
