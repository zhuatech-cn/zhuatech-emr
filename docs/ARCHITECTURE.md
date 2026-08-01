# 架构说明

```text
Vue 3 管理端 / 响应式 H5
          │ HTTP / JSON
Spring Security → Controller → Service → Spring Data JPA → MySQL 8
                                  │
                    病历完整性与归档门禁规则引擎
```

当前版本以单体分层架构保证易运行与易理解。`DomainCatalog` 管理病案质控样例，`RecordCompletenessService` 执行归档前完整性检查，`WorkItem` 承载缺陷整改事项。示例不处理真实患者数据；生产化必须按适用法律法规完成等保、最小权限、全链路审计、数据脱敏、电子签名和医疗接口标准适配。
