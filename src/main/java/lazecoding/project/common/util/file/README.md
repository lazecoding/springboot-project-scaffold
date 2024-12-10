# 文件操作

- 获取文件操作对象

```java
// 获取文件操作对象
FileOperator fileOperator = FileOperatorHolder.getInstance();
```

- 本地文件操作类

```C
LocalFileOperator
```

- 文件操作配置

```yaml
# 文件操作类型
file:
  # local、minio
  operator: local
# 文件中心 minio
minio:
  # 地址
  endpoint: http://127.0.0.1:9000
  accessKey: BOqfNjG6113qExdyGuDo
  secretKey: H5XB4OXeuIySwmzHNYe2sANUSO0cpeV2S8KdETlf
  bucket: scaffold
```