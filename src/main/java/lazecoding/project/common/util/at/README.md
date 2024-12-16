# @

- 使用示例

```java
public static void main(String[] args) {
    String content = "快点回复我 @_{\"id\":\"admin\",\"name\":\"管理员\"}_@";
    ParserResult parserResult = parser(content);
    if (parserResult.isSuccess()) {
        String escapeContent = parserResult.getEscapeContent();
        Set<String> ids = parserResult.getIds();
    }
    System.out.println(JsonUtil.GSON.toJson(parserResult));
}
```

- 结果

```json
{
  "success": true,
  "content": "快点回复我 @_{\"id\":\"admin\",\"name\":\"管理员\"}_@",
  "escapeContent": "快点回复我 @管理员",
  "ids": [
    "admin"
  ]
}
```
