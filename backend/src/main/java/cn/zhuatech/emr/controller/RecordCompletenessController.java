/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.emr.controller;

import cn.zhuatech.emr.common.ApiResponse;
import cn.zhuatech.emr.service.RecordCompletenessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/record-completeness")
public class RecordCompletenessController {
    private final RecordCompletenessService service;
    public RecordCompletenessController(RecordCompletenessService service) { this.service = service; }
    @PostMapping
    ApiResponse<RecordCompletenessService.CompletenessResult> assess(
        @Valid @RequestBody RecordCompletenessService.CompletenessRequest request) {
        return ApiResponse.ok(service.assess(request));
    }
}
