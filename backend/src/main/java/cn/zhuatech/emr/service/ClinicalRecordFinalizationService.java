/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.emr.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClinicalRecordFinalizationService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.requiredFieldsComplete()) blockers.add("病历必填内容不完整");
        if (!request.authorSigned()) blockers.add("病历作者未电子签名");
        if (request.attendingCosignRequired() && !request.attendingCosigned()) blockers.add("上级医师未完成会签");
        if (!request.diagnosisAndProcedureConfirmed()) blockers.add("诊断与操作记录未确认");
        if (!request.requiredConsentsLinked()) blockers.add("必要知情同意文件未关联");
        if (!request.criticalResultsAcknowledged()) blockers.add("危急结果未确认处置");
        if (!request.privacyClassificationApplied()) blockers.add("病历隐私与访问分类未应用");
        if (request.openDocumentationQueries() > 0) blockers.add("仍有未关闭的病历质控问题");
        if (!blockers.isEmpty()) {
            actions.add("阻断病历定稿并完成临床与质控补录");
            return new Assessment(Decision.BLOCKED, blockers, actions);
        }
        if (!request.codingReviewed() || !request.amendmentAuditReady()) {
            if (!request.codingReviewed()) actions.add("完成诊断与手术编码复核");
            if (!request.amendmentAuditReady()) actions.add("启用定稿后更正与追加记录审计");
            return new Assessment(Decision.QUERY, blockers, actions);
        }
        actions.add("批准病历定稿并锁定签名、版本和更正审计链");
        return new Assessment(Decision.FINALIZE, blockers, actions);
    }

    public record Request(@NotBlank String recordId, boolean requiredFieldsComplete, boolean authorSigned,
                          boolean attendingCosignRequired, boolean attendingCosigned,
                          boolean diagnosisAndProcedureConfirmed, boolean codingReviewed,
                          boolean requiredConsentsLinked, boolean criticalResultsAcknowledged,
                          boolean privacyClassificationApplied, boolean amendmentAuditReady,
                          @Min(0) int openDocumentationQueries) {}
    public record Assessment(Decision decision, List<String> blockers, List<String> actions) {}
    public enum Decision { FINALIZE, QUERY, BLOCKED }
}
