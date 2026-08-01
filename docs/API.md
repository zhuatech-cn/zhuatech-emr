# API 概览

Base URL：`http://localhost:8080/api`。除公开信息外均使用 HTTP Basic 演示鉴权。

| 方法 | 路径 | 角色 | 说明 |
| --- | --- | --- | --- |
| GET | `/public/about` | 公开 | 项目公司与官网 |
| GET | `/admin/dashboard` | ADMIN | 管理端运营总览 |
| GET | `/workspace/tasks` | OPERATOR | 用户工作台数据 |
| POST | `/admin/risk-assessment` | ADMIN | 运营风险评估 |
| POST | `/admin/record-completeness` | ADMIN | 病历完整性与归档门禁评估 |

风险评估请求包含 `backlog`、`delayedItems`、`criticalItems`、`capacityUtilization`、`dataCompleteness`，均为非负整数；百分比字段范围为 0–100。

病历完整性请求使用必填项数、已完成项数、未签医嘱、诊断缺项、待回传报告和出院后小时数，返回 `COMPLETE`、`REVIEW` 或 `BLOCK_ARCHIVE`。接口仅用于软件演示，不构成医疗建议或正式病案质控结论。
