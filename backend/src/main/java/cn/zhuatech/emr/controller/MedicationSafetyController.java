/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.emr.controller;
import cn.zhuatech.emr.common.ApiResponse; import cn.zhuatech.emr.service.MedicationSafetyService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/emr/insights/medication-safety") public class MedicationSafetyController {private final MedicationSafetyService service; public MedicationSafetyController(MedicationSafetyService service){this.service=service;} @PostMapping ApiResponse<MedicationSafetyService.Result> evaluate(@Valid @RequestBody MedicationSafetyService.Request request){return ApiResponse.ok(service.evaluate(request));}}
