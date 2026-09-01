# 企业级电子病历定稿治理

`POST /api/enterprise/emr/clinical-record-finalization` 检查必填内容、作者与上级签名、诊断操作、编码、知情同意、危急结果、隐私分类、更正审计和质控问题，返回 `FINALIZE / QUERY / BLOCKED`。

生产部署应使用可靠电子签名、时间戳和不可覆盖的版本审计，并依据医疗记录管理制度配置定稿期限与更正流程。
