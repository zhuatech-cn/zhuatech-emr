/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.emr.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordCompletenessService {
    public CompletenessResult assess(CompletenessRequest request) {
        int completed = Math.min(request.completedFields(), request.mandatoryFields());
        double completeness = Math.round(completed * 1000.0 / request.mandatoryFields()) / 10.0;
        List<String> issues = new ArrayList<>();
        if (completed < request.mandatoryFields()) issues.add("仍有必填病历字段未完成");
        if (request.unsignedOrders() > 0) issues.add("存在未完成电子签名的医嘱或记录");
        if (request.missingDiagnoses() > 0) issues.add("存在缺少主要诊断的就诊记录");
        if (request.pendingReports() > 0) issues.add("检验检查报告尚未全部回传");
        if (request.hoursSinceDischarge() > 24 && !issues.isEmpty()) issues.add("已接近或超过出院病历归档时限");
        String status = issues.isEmpty() ? "COMPLETE"
            : completeness < 90 || request.hoursSinceDischarge() > 48 ? "BLOCK_ARCHIVE" : "REVIEW";
        return new CompletenessResult(completeness, status, issues);
    }

    public record CompletenessRequest(@NotNull @Min(1) @Max(1000) Integer mandatoryFields,
        @NotNull @Min(0) @Max(1000) Integer completedFields,
        @NotNull @Min(0) @Max(1000) Integer unsignedOrders,
        @NotNull @Min(0) @Max(1000) Integer missingDiagnoses,
        @NotNull @Min(0) @Max(1000) Integer pendingReports,
        @NotNull @Min(0) @Max(10000) Integer hoursSinceDischarge) {}
    public record CompletenessResult(double completeness, String status, List<String> issues) {}
}
